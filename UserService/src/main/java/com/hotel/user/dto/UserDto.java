package com.hotel.user.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	private String userId;
	private String name;
	private String email;
}
