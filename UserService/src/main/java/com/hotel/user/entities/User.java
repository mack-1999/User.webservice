package com.hotel.user.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	private String userId;
	private String name;
	private String email;
	
	@Transient
	private List<Rating> ratings = new ArrayList<>();
}
