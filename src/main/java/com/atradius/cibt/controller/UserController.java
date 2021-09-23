package com.atradius.cibt.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atradius.cibt.client.UserDetailsAPIService;
import com.atradius.cibt.exception.ResourceNotFoundException;
import com.atradius.cibt.model.Message;
import com.atradius.cibt.model.User;
import com.atradius.cibt.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;

/**
 * @author 002A4W744
 *
 */

@RestController
@RequestMapping("/api/users")
@Log4j2
public class UserController {
	
	private static final String USER_SERVICE = "userService";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsAPIService userServiceDetailsAPIService;

	/**
	 * 1. This method takes User object as input from JSON/XML using
	 * 
	 * @RequestBody and returns ResponseEntity<T>. call service.saveUser(obj)
	 */

	
	  @PostMapping("/save") public ResponseEntity<Message> saveUser(@RequestBody User user) {
			ResponseEntity<Message> resp = null;
			try {
				log.info("inside UserController.saveUser() method");
				Integer id = userService.saveUser(user);
				resp = new ResponseEntity<Message>(new Message("SUCCESS", id + "-saved"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("Unable to save user");
				resp = new ResponseEntity<Message>(new Message("FAIL", "Unable to Save"), HttpStatus.OK);
			}
			return resp;
		}
	 
	/***
	 * 2. This method reads data from DB using findAll() and returns List<User> if
	 * data exist or String (not exist) as ResponseEntity using annotation
	 * 
	 * @ResponseBody
	 */

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUser() {
		try {
			log.info("inside UserController.getAllUser() method");
			List<User> list = userService.getAllUser();
			if (list == null || list.isEmpty())
				throw new ResourceNotFoundException("No user record found");
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Unable to find user");
			throw new ResourceNotFoundException("Unable to found caused by:" + e.getMessage());
		}
	}
	 

	/**
	 * 5. Read Input as JSON/XML using
	 * 
	 * @RequestBody , check id exist or not if exist call service save method Return
	 *              ResponseeEntity
	 */
	@PutMapping("/update")
	public ResponseEntity<Message> updateUser(@RequestBody User user) {
		try {
			log.info("inside UserController.updateUser() method");
			boolean exist = userService.isExist(user.getId());
			if (!exist)
				throw new ResourceNotFoundException("User not found for id:" + user.getId());
			userService.saveUser(user);
			return new ResponseEntity<Message>(new Message("OK", user.getId() + "-Updated"), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Unable to update user");
			throw new ResourceNotFoundException("unable to update user caused by:" + e.getMessage());
		}
	}

	/**
	 * 4. Read pathVariable id check row exist or not if exist call service delete
	 * else return String error msg
	 */
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Message> deleteUser(@PathVariable Integer id) {
		try {
			log.info("inside UserController.deleteUser() method");
			boolean exist = userService.isExist(id);
			if (!exist)
				throw new ResourceNotFoundException("unable to found user to delete for userId:" + id);
			userService.deleteUser(id);
			return new ResponseEntity<Message>(new Message("SUCCESSS", id + "-removed"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Unable to delete user");
			throw new ResourceNotFoundException("unable to delete user cause by:" + e.getMessage());
		}

	}
	
	@GetMapping("/getmicro2")
	//@Retry(name = USERSERVICE, fallbackMethod = "getMicro2InstanceFallback")
	@CircuitBreaker(name=USER_SERVICE, fallbackMethod = "getMicro2InstanceFallback")
	public ResponseEntity<String> getMicro2Instance() {
		String response = userServiceDetailsAPIService.getMicro2Instance();
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	public ResponseEntity<String> getMicro2InstanceFallback(Exception e) {
		log.info("Inside fallback");
		return new ResponseEntity<String>("Microservice1 is down!!", HttpStatus.OK);
	}
	
	/*
	 * @PostMapping("/save")
	 * 
	 * @CircuitBreaker(name=USER_SERVICE, fallbackMethod =
	 * "getMicro2InstanceFallback") public ResponseEntity<Message>
	 * createUser(@RequestBody User user) {
	 * log.info("Inside Post request controller" +user); Integer id
	 * =userService.saveUser(user); return new ResponseEntity<Message>(new
	 * Message("SUCCESS", "User created"), HttpStatus.CREATED); }
	 */

	/*
	 * @Cacheable(value = "/user", key = "#id") public ResponseEntity
	 * getAllUsers(String contentType, String authorization) {
	 * log.info("Inside getAllUser controller"); List<User> users =
	 * userService.getAllUser(); return new ResponseEntity(users, HttpStatus.OK); }
	 */
	

}
