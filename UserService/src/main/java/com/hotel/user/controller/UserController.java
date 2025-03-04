package com.hotel.user.controller;

import java.util.List;

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

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto){
		return new ResponseEntity<>(this.userService.saveUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserDto>> getAllUsersData(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserDataById(@PathVariable String userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	//Update Data for User
	//Delete a user
}
