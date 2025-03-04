package com.hotel.user.service;

import java.util.List;

import com.hotel.user.dto.UserDto;

public interface UserService {
	//Create new user
	UserDto saveUser(UserDto userDto);
	//Get user by id
	UserDto getUserById(String userId);
	//Get all users
	List<UserDto> getAllUsers();
}
