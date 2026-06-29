package com.dadneedsreport.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dadneedsreport.dto.UserRequest;
import com.dadneedsreport.models.User;
import com.dadneedsreport.repositories.UserRepository;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public void registerUser(UserRequest dto) throws Exception {
		User user = new User();
		user.setPassword(passwordEncoder.encode(dto.password()));
		user.setUsername(dto.username());
		userRepository.save(user);
	}

	public Map<String, String> loginUser(UserRequest dto) throws UsernameNotFoundException, RuntimeException {
		User result = userRepository.findByUsername(dto.username())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		if (!passwordEncoder.matches(dto.password(), result.getPassword()))
			throw new RuntimeException("Invalid Password");

		Map<String, String> response = new HashMap<>();
		response.put("token", jwtService.generateToken(result));
	
		return response;
	}
}
