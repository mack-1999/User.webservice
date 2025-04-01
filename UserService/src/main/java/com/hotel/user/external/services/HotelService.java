package com.hotel.user.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.user.entities.Hotel;

@FeignClient(name = "HOTEL-SERVICE") // Client-Side Load Balancing
public interface HotelService {
	
	@GetMapping("/hotels/{hotelId}")
	public Hotel getHotelDataById(@PathVariable String hotelId);
	
}

/*
 * If you want all Feign calls to go through API Gateway, you must specify the
 * API Gateway URL explicitly url = "http://localhost:8084"
 */