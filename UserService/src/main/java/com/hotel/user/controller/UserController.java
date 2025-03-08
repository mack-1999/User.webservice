package com.hotel.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.user.dto.UserDto;
import com.hotel.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/create")
	public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto){
		return new ResponseEntity<>(this.userService.saveUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserDto>> getAllUsersData(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
	public ResponseEntity<UserDto> getUserDataById(@PathVariable String userId){
		logger.info("Getting Single User Data: Inside getUserDataById()");
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	// Fallback method when circuit breaker is OPEN
    public ResponseEntity<String> userFallback(String userId, Throwable ex) {
        logger.error("Fallback method executed due to: {}", ex.getMessage());
        return ResponseEntity.ok("Downstream Service is Down");
    }
	
	//Update Data for User
	//Delete a User
}
