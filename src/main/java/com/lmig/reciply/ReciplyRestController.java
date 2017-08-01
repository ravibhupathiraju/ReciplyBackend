package com.lmig.reciply;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lmig.reciply.Utilities;

import io.swagger.annotations.ApiOperation;

@RestController
public class ReciplyRestController  {

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private MealPlanRepository mealPlanRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	Utilities utils = new Utilities();
	
	// Post method to add user
	@RequestMapping(value = "/api/User", method = RequestMethod.POST)
	public HttpStatus addUser(@Validated(AppUser.New.class) @RequestBody AppUser user) {
		if (user == null) {
			return HttpStatus.BAD_REQUEST;
		}
		userRepository.save(user);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/api/User", method = RequestMethod.PUT)
	public AppUser updateUser(@Validated(AppUser.Existing.class) @RequestBody AppUser user) {
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
	public AppUser register(@Validated(AppUser.New.class) @RequestBody AppUser user) {
		AppUser userFound = userRepository.findByUserId(user.userId);
		if (userFound == null) {  //user doesn't already exist in database
			String pwd = user.password;
			user.password = utils.hashPassword(pwd);			
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
		String pwd = utils.hashPassword(user.password);
		return userRepository.findByUserIdAndPassword(user.userId,
				pwd);
	}

	// Post method to add mealplan
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.POST)
	public MealPlan addMealPlan (@RequestBody MealPlan mealPlan) {
		// if (mealPlan == null) {
		// return new ResponseEntity<MealPlan>(HttpStatus.BAD_REQUEST);
		// }
		Collections.sort(mealPlan.getRecipes(),new Utilities());
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
	public ResponseEntity<List<MealPlan>> getMealPlan(@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String dateString) {
		if (userId.equals(null) || userId.equals("")) {
			return new ResponseEntity<List<MealPlan>>(HttpStatus.BAD_REQUEST);
		}
		LocalDate weekBeginDate = null;
		List<MealPlan> mealPlanList;
		if (dateString.equals("")) {
			mealPlanList = mealPlanRepository.search(userId);
			
			if (mealPlanList.isEmpty()) {
				return new ResponseEntity<List<MealPlan>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<MealPlan>>(mealPlanList, HttpStatus.OK);
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			weekBeginDate = LocalDate.parse(dateString, formatter);
			mealPlanList = mealPlanRepository.searchWithDate(userId, weekBeginDate);
			if (mealPlanList.isEmpty()) {
				return new ResponseEntity<List<MealPlan>>(mealPlanList, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<MealPlan>>(mealPlanList, HttpStatus.OK);
		}

	}
	

	@RequestMapping(path = "/api/mealPlan/{id}", method = RequestMethod.DELETE)
	public void deleteMealPlan(@PathVariable(name = "id", required = true) int id) {
		mealPlanRepository.delete(id);
	}

	// POST method to update Ingredients entity related to shopping list
	@RequestMapping(value = "/api/ShoppingList", method = RequestMethod.PUT)
	public List<Ingredient> postShopList(@RequestBody List<Ingredient> ingredient) {
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
	public List<Ingredient> getShoppingList(@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String dateString) {
		LocalDate weekBeginDate = null;
		if (dateString.equals("")) {
			return null;
		} else {
			ArrayList<Ingredient> missingIngredients = new ArrayList<Ingredient>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			weekBeginDate = LocalDate.parse(dateString, formatter);
			List<MealPlan> mealPlan = mealPlanRepository.searchWithDate(userId, weekBeginDate);
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
