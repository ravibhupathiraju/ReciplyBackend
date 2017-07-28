package com.lmig.reciply;

import java.lang.annotation.Repeatable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmig.reciply.AppUser.Existing;
import com.lmig.reciply.AppUser.New;

import io.swagger.annotations.ApiOperation;

import com.lmig.reciply.AppUserRepository;
import com.lmig.reciply.MealPlanRepository;
import com.lmig.reciply.IngredientRepository;

@RestController
public class ReciplyRestController {

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private MealPlanRepository mealPlanRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	// Post method to add user
	@RequestMapping(value = "/api/User", method = RequestMethod.POST)
	public HttpStatus addUser(
			@Validated(AppUser.New.class) @RequestBody AppUser user) {
		if (user == null) {
			return HttpStatus.BAD_REQUEST;
		}
		userRepository.save(user);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/api/User", method = RequestMethod.PUT)
	public AppUser updateUser(
			@Validated(AppUser.Existing.class) @RequestBody AppUser user) {
		AppUser existing = userRepository.findOne(user.getId());
		existing.merge(user);
		userRepository.save(existing);
		return existing;
	}

	@RequestMapping(path = "/api/User/{id}", method = RequestMethod.GET)
	public AppUser getUser(@PathVariable(name = "id", required = true) int id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(path = "/api/User/", method = RequestMethod.GET)
	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(path = "/api/User/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable(name = "id", required = true) int id) {
		userRepository.delete(id);
	}

	@RequestMapping(path = "/api/register", method = RequestMethod.POST)
	@ApiOperation(value = "Registers a new user", notes = "Will add a new user to the recip-ly database.")
	public AppUser register(
			@Validated(AppUser.New.class) @RequestBody AppUser user) {
		AppUser userFound = userRepository.findByUserId(user.userId);
		if (userFound == null) {
			userRepository.save(user);
			return user;
		} else {
			// user found in database so don't add them and return a empty user
			AppUser returnEmptyUser = new AppUser();
			return returnEmptyUser;
		}

	}

	@RequestMapping(path = "/api/login", method = RequestMethod.POST)
	public AppUser login(@RequestBody AppUser user) {
		return userRepository.findByUserIdAndPassword(user.userId,
				user.password);
	}

	// Post method to add mealplan
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.POST)
	public MealPlan addMealPlan(@RequestBody MealPlan mealPlan) {
		// if (mealPlan == null) {
		// return new ResponseEntity<MealPlan>(HttpStatus.BAD_REQUEST);
		// }
		mealPlanRepository.save(mealPlan);
		return mealPlan;
	}

	// Put method to add mealplan
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.PUT)
	public MealPlan updateMealPlan(@RequestBody MealPlan mealPlan) {
		// if (mealPlan == null) {
		// return HttpStatus.BAD_REQUEST;
		mealPlanRepository.save(mealPlan);
		return mealPlan;
	}

	// Search method to get weekly plan, recipe name and ingredients information
	// Input will be user id, week beginning date
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.GET)
	public List<MealPlan> getMealPlan(
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String dateString) {
		LocalDate weekBeginDate = null;
		if (dateString.equals("")) {
			return mealPlanRepository.search(userId);
		} else {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			weekBeginDate = LocalDate.parse(dateString, formatter);
			return mealPlanRepository.searchWithDate(userId, weekBeginDate);
		}

	}

	@RequestMapping(path = "/api/mealPlan/{id}", method = RequestMethod.DELETE)
	public void deleteMealPlan(
			@PathVariable(name = "id", required = true) int id) {
		mealPlanRepository.delete(id);
	}

	// POST method to update Ingredients entity related to shopping list
	@RequestMapping(value = "/api/ShoppingList", method = RequestMethod.PUT)
	public List<Ingredient> postShopList(
			@RequestBody List<Ingredient> ingredient) {
		// if (mealPlan == null) {
		// return new ResponseEntity<MealPlan>(HttpStatus.BAD_REQUEST);
		// }
		ingredientRepository.save(ingredient);
		System.out.println(ingredient.toString());
		return ingredient;
	}

	// Search method to get weekly plan, recipe name and ingredients information
	// Input will be user id, week beginning date
	@RequestMapping(value = "/api/shoppinglist", method = RequestMethod.GET)
	public List<Ingredient> getShoppingList(
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String dateString) {
		LocalDate weekBeginDate = null;
		if (dateString.equals("")) {
			return null;
		} else {
			ArrayList<Ingredient> missingIngredients = new ArrayList<Ingredient>();
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd");
			weekBeginDate = LocalDate.parse(dateString, formatter);
			List<MealPlan> mealPlan = mealPlanRepository.searchWithDate(userId,
					weekBeginDate);
			for (MealPlan mealPlan2 : mealPlan) {
				List<Recipe> recipes = mealPlan2.getRecipes();
				for (Recipe recipe : recipes) {
					List<Ingredient> ingredients = recipe.getIngredients();
					for (Ingredient ingredient : ingredients) {
						if (ingredient.getIngredientMissing().equals("Y")) {
							missingIngredients.add(ingredient);
						}
					}
				}
			}

			return missingIngredients;
		}

	}

}
