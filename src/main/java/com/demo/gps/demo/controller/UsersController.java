package com.demo.gps.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.gps.dao.UserRepository;
import com.demo.gps.demo.model.User;

@RestController
@RequestMapping("/users")
public class UsersController {

	@RequestMapping("/get")
	public String index() {

		return "Greetings from User API";
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/find/{name}", method=RequestMethod.GET)
	public String get(@PathVariable String name) {
		User user = userRepository.findByUser(name);
		return user != null ? user.getUserName() : null;
	}
	
	@RequestMapping(value="/latestposition/{name}", method=RequestMethod.GET)
	public String getLatestPosition(@PathVariable String name) {
		User user = userRepository.findByUser(name);
		return user != null ? user.getLatestLatitude() + " : " + user.getLatestLongitude() : null;
	}
}
