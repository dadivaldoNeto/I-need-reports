package com.dadneedsreport.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.data.domain.Pageable;
import com.dadneedsreport.config.filter.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						(authorize) -> authorize.requestMatchers(HttpMethod.POST, "/api/v1/register", "/api/v1/login")
								.permitAll()
								.requestMatchers(
								"/swagger/**",
								"/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/swagger-resources/**").permitAll()
								.anyRequest().authenticated())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint((request, response, authException) -> {
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							response.setContentType("application/json");
							response.getWriter().write("""
									{
									  "error": "Unauthorized"
									}
									""");
						}))
				.formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable())
				.cors(withDefaults())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static Pageable getFindAllLimit() {
		return PageRequest.of(0, 60);
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOriginPatterns(Arrays.asList("*"));
		cors.setAllowedHeaders(Arrays.asList("*"));
		cors.setAllowedMethods(Arrays.asList(
			"POST",
			"GET",
			"DELETE",
			"PUT",
			"PATCH",
			"OPTIONS"
		));
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", cors);
		return configSource;
	}
}
