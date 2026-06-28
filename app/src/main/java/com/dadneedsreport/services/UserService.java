package com.dadneedsreport.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dadneedsreport.dto.UserRequest;
import com.dadneedsreport.models.User;
import com.dadneedsreport.repositories.UserRepository;

@Service
public class UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void registerUser(UserRequest dto) throws Exception {
		User user = new User();
		String pw = passwordEncoder.encode(dto.password());
		user.setPassword(pw);
		user.setUsername(dto.username());
		userRepository.save(user);
	}
}
