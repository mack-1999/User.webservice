package com.hotel.user.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotel.user.controller.UserController;
import com.hotel.user.dto.UserDto;
import com.hotel.user.entities.Hotel;
import com.hotel.user.entities.Rating;
import com.hotel.user.entities.User;
import com.hotel.user.exception.ResourceNotFoundException;
import com.hotel.user.external.services.HotelService;
import com.hotel.user.external.services.RatingService;
import com.hotel.user.repository.UserRepository;
import com.hotel.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepo; // Field Dependency Injection
	
	@Autowired
	ModelMapper modelMapper;
	
	private HotelService hotelService;
	
	private RatingService ratingService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	public UserServiceImpl(RatingService ratingService) {
		this.ratingService = ratingService; // Constructor Dependency Injection
	}
	
	@Autowired
	public void sethotelService(HotelService hotelService) {
		this.hotelService = hotelService; // Setter Dependency Injection
	}
	
	@Override
	@CacheEvict(value = "userCache", allEntries = true) // Clears entire "users" cache
	public UserDto saveUser(UserDto userDto) {
		// Generate & Set random hotel id
		String randomId = UUID.randomUUID().toString();
		userDto.setUserId(randomId);
				
		// Converting DTO to entity class
		User userData = modelMapper.map(userDto, User.class);
						
		// Save data into DB
		User savedData = this.userRepo.save(userData);
						
		// Return DTO
		return modelMapper.map(savedData, UserDto.class);
	}

	@Override
	@Cacheable(value = "userCache", key = "#userId")  // Fetch from cache if available
	public UserDto getUserById(String userId) {
		// Find User id in DB or else throw an user defined exception
		User user = this.userRepo.findById(userId)
						.orElseThrow(() -> new ResourceNotFoundException("User with id" + userId + "not found"));
		
		// Get Rating data from RATING-SERVICE (Service-to-Service Call)
		logger.info("Service to service: Rating-Service");
		List<Rating> ratingsOfUser = ratingService.getAllRatingByUserId(user.getUserId());
		
		logger.info("Service to service: Hotel-Service");
		List<Rating> ratingList = ratingsOfUser.stream().map(rating -> {
			// Get hotel data from HOTEL-SERVICE (Service-to-Service Call)
			// Feign automatically performs load balancing across available HOTEL-SERVICE instances.
			Hotel hotel = hotelService.getHotelDataById(rating.getHotelId());
			
			rating.setHotel(hotel); //Add hotel details within ratings
			return rating;	
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList); //Add Ratings for specific user
		
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	@Cacheable(value = "userCache", key = "'allUsers'")  // Cache all users list
	public List<UserDto> getAllUsers() {
		List<User> results = this.userRepo.findAll();
		
		List<UserDto> userDto = results.stream()
				.map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		
		return userDto;
	}

}
