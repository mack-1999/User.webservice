package com.hotel.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.user.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
