package com.dadneedsreport.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadneedsreport.config.HandleException;
import com.dadneedsreport.dto.StringResponse;
import com.dadneedsreport.dto.UserRequest;
import com.dadneedsreport.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest dto) {
		try {
			userService.registerUser(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (Exception ex) {
			return HandleException.error(HttpStatus.BAD_REQUEST, "User already exists");
		}
	}

	@PostMapping(value = "login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody UserRequest dto) {
		try {
			return ResponseEntity.ok(userService.loginUser(dto));
		} catch (RuntimeException ex) {
			return HandleException.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

}
