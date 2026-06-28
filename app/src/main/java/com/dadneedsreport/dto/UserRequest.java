package com.dadneedsreport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest( 
	@NotBlank 
	@Pattern(regexp = "^[a-zA-Z]{3,8}$", message = "Username must be 3-8 alphabetic characters")
	String username, 
	@NotBlank String password
	) {}
