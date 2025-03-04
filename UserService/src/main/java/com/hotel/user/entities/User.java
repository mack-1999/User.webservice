package com.hotel.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	private String userId;
	private String name;
	private String email;
}
