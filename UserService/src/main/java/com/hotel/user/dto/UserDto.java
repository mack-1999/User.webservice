package com.hotel.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hotel.user.entities.Rating;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	private String userId;
	private String name;
	private String email;
	private List<Rating> ratings = new ArrayList<>();
}
