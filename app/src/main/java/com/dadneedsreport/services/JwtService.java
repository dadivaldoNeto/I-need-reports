package com.dadneedsreport.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dadneedsreport.models.User;


@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRET;

	@Value("${jwt.expiration}")
	private long expirationH;

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		return createToken(claims, user.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		Instant currentTime = Instant.now();
		Date Expiration = Date.from(currentTime.plus(expirationH, ChronoUnit.HOURS));
		return Jwts.builder()
				.header()
				.add("typ", "JWT")
				.and()
				.claims(claims)
				.subject(username)
				.issuedAt(Date.from(currentTime))
				.expiration(Expiration)
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	// Validate Token
	public Claims getTokenData(String token) {

		try {
			Claims claims = Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();

			if (isTokenExpired(claims.getExpiration()))
				throw new RuntimeException("JWT Token expired");
			return (claims);
		} catch (JwtException ex) {
			throw new RuntimeException("Invalid JWT Token");
		}
	}

	private boolean isTokenExpired(Date expDate) {
		return Instant.now().isAfter(expDate.toInstant());
	}
}
