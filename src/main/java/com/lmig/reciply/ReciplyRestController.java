package com.lmig.reciply;

import java.lang.annotation.Repeatable;
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

//import com.google.gson.Gson;

@RestController
public class ReciplyRestController {
	
	@Autowired
	private AppUserRepository userRepository;

	@RequestMapping(value = "/api/User", method = RequestMethod.POST)
	public HttpStatus addUser(@RequestBody AppUser user) {
		if (user == null) {
			 return HttpStatus.BAD_REQUEST;
		}
		userRepository.save(user);
		return HttpStatus.OK;
	}

	@RequestMapping(path = "/api/User/{id}", method = RequestMethod.GET)
//	public AppUser getUser(Model model, HttpSession session,
	public AppUser getUser(
			@PathVariable(name = "id", required = true) int id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(path = "/api/login/{userId}/{password}", method = RequestMethod.GET)
	public AppUser login(
			@PathVariable(name = "userId", required = true) String userId, 
			@PathVariable(name = "password", required = true) String password) {
		System.out.println("userID len= "+userId.length()+" val- "+userId);
		System.out.println("password len= "+password.length()+" val- "+password);
		return userRepository.findByUserIdAndPassword(userId, password);
	}
	
}
