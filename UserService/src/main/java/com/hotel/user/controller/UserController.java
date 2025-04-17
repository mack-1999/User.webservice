package com.hotel.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.user.dto.UserDto;
import com.hotel.user.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	int retryCount = 1;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/create")
	public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<>(this.userService.saveUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserDto>> getAllUsersData(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	@Retry(name = "userServiceRetry", fallbackMethod = "userFallback")
	//@CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "userFallback")
	//@RateLimiter(name = "userServiceRateLimiter", fallbackMethod = "userFallbackRateLimiter")
	public ResponseEntity<UserDto> getUserDataById(@PathVariable String userId){
		logger.info("Getting Single User Data: Inside getUserDataById()");
		//logger.info("Retry Count:{}",retryCount);
		//retryCount++;
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	// Fallback method when circuit breaker is OPEN
    public ResponseEntity<String> userFallback(String userId, Throwable ex) {
        logger.error("Fallback method executed due to: {}", ex.getMessage());
        return ResponseEntity.ok("Downstream Service is Down");
    }
    
    // Fallback method when circuit breaker is OPEN
    public BodyBuilder userFallbackRateLimiter(String userId, Throwable ex) {
        logger.error("Fallback method executed due to: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS);
    }
	
	//Update Data for User
	//Delete a User
}
