package com.hotel.user.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.user.entities.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
	
	// Get all ratings by userId
	@GetMapping("/ratings/getall/user/{userId}")
	public List<Rating> getAllRatingByUserId(@PathVariable String userId);
}
