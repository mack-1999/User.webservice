package com.hotel.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hotel.user.entities.Rating;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto implements Serializable {

    private String userId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    private List<Rating> ratings = new ArrayList<>();
}

