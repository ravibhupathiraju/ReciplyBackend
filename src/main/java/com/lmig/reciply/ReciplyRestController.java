package com.lmig.reciply;

import java.lang.annotation.Repeatable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.lmig.reciply.AppUserRepository;
import com.lmig.reciply.MealPlanRepository;
//import com.google.gson.Gson;

@RestController
public class ReciplyRestController {

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private MealPlanRepository mealPlanRepository;

	// Post method to add mealplan
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.POST)
	// public HttpStatus addMealPlan(@RequestBody MealPlan mealPlan) {
	public MealPlan addMealPlan(@RequestBody MealPlan mealPlan) {
//		if (mealPlan == null) {
//			return HttpStatus.BAD_REQUEST;
//		}
		mealPlanRepository.save(mealPlan);
		System.out.println(mealPlan.toString());
		// return HttpStatus.OK;
		return mealPlan;
	}	
	
	// Post method to add user
	@RequestMapping(value = "/api/User", method = RequestMethod.POST)
	public HttpStatus addUser(@RequestBody AppUser user) {
		if (user == null) {
			return HttpStatus.BAD_REQUEST;
		}
		userRepository.save(user);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/api/User", method = RequestMethod.PUT)
	public AppUser updateUser(@RequestBody AppUser user) {
//		if (user == null) {
//			 return HttpStatus.BAD_REQUEST;
//		}
		System.out.println("In Put. UserID passed in equals=> "+user.getId());
		AppUser existing = userRepository.findOne(user.getId());
		existing.merge(user);
		userRepository.save(existing);
		return existing;
	}
	@RequestMapping(path = "/api/User/{id}", method = RequestMethod.GET)
//	public AppUser getUser(Model model, HttpSession session,
	public AppUser getUser(
			@PathVariable(name = "id", required = true) int id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(path = "/api/login", method = RequestMethod.POST)
	public AppUser login(@RequestBody AppUser user) {
		System.out.println("userID len= "+user.userId+" val- "+user.userId);
		System.out.println("password len= "+user.password.length()+" val- "+user.password);
		return userRepository.findByUserIdAndPassword(user.userId, user.password);
	}
	
	// Put method to add mealplan
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.PUT)
	// public HttpStatus addMealPlan(@RequestBody MealPlan mealPlan) {
	public MealPlan updateMealPlan(@RequestBody MealPlan mealPlan) {
//		if (mealPlan == null) {
//			return HttpStatus.BAD_REQUEST;
//		}
//		MealPlan existing = mealPlanRepository.findById(mealPlan.getPlanId());
//		existing.merge(mealPlan);
		System.out.println("ravi" + mealPlanRepository.findByplanId(mealPlan.getPlanId()));
//		mealPlanRepository.delete(mealPlanRepository.findByplanId(mealPlan.getPlanId()));
//		mealPlanRepository.de
		mealPlanRepository.save(mealPlan);
		System.out.println(mealPlan.toString());
		// return HttpStatus.OK;
		return mealPlan;
	}

	// Search method to get weekly plan, recipe name and ingredients information
	// Input will be user id, week beginning date
	@RequestMapping(value = "/api/mealPlan", method = RequestMethod.GET)
	public List<MealPlan> getMealPlan(@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") LocalDate date) {
		List<MealPlan> result = new ArrayList<>();
		result = mealPlanRepository.search(userId);
		return mealPlanRepository.search(userId);
	}

	@RequestMapping(path = "/api/mealPlan2/{id}", method = RequestMethod.GET)
	public List<MealPlan> getUser(@PathVariable(name = "id", required = true) String temp) {
		System.out.println("temp " + temp);
		return mealPlanRepository.search(temp);
	}

}
