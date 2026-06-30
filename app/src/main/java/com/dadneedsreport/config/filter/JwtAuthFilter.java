package com.dadneedsreport.config.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dadneedsreport.services.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String authHeader = request.getHeader("Authorization");
			Claims claims = null;

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				claims = jwtService.getTokenData(token);
			}

			if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				SecurityContext ctx = SecurityContextHolder.createEmptyContext();
				Map<String, Object> payload = new HashMap<>();

				payload.put("username", claims.get("username"));
				payload.put("id", claims.get("id"));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(payload, null,
						null);

				ctx.setAuthentication(authToken);
				SecurityContextHolder.setContext(ctx);
			}
			filterChain.doFilter(request, response);
		} catch (RuntimeException ex) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		}
	}

	private void sendError(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write("""
				{
				  "error": "%s"
				}
				""".formatted(message));
	}
}

/*
 * 
 * package com.ey.springboot3security.filter;
 * 
 * import com.ey.springboot3security.service.UserInfoDetails;
 * import com.ey.springboot3security.service.JwtService;
 * 
 * import jakarta.servlet.FilterChain;
 * import jakarta.servlet.ServletException;
 * import jakarta.servlet.http.HttpServletRequest;
 * import jakarta.servlet.http.HttpServletResponse;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken;
 * import org.springframework.security.core.context.SecurityContextHolder;
 * import org.springframework.security.core.userdetails.UserDetails;
 * import org.springframework.security.web.authentication.
 * WebAuthenticationDetailsSource;
 * import org.springframework.stereotype.Component;
 * import org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import java.io.IOException;
 * 
 * @Component
 * public class JwtAuthFilter extends OncePerRequestFilter {
 * 
 * private final UserDetailsService userDetailsService;
 * private final JwtService jwtService;
 * 
 * @Autowired
 * public JwtAuthFilter(UserDetailsService userDetailsService, JwtService
 * jwtService) {
 * this.userDetailsService = userDetailsService;
 * this.jwtService = jwtService;
 * }
 * 
 * @Override
 * protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * String authHeader = request.getHeader("Authorization");
 * String token = null;
 * String username = null;
 * 
 * if (authHeader != null && authHeader.startsWith("Bearer ")) {
 * token = authHeader.substring(7);
 * username = jwtService.extractUsername(token);
 * }
 * 
 * if (username != null &&
 * SecurityContextHolder.getContext().getAuthentication() == null) {
 * UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 * if (jwtService.validateToken(token, userDetails)) {
 * UsernamePasswordAuthenticationToken authToken = new
 * UsernamePasswordAuthenticationToken(
 * userDetails,
 * null,
 * userDetails.getAuthorities());
 * authToken.setDetails(new
 * WebAuthenticationDetailsSource().buildDetails(request));
 * SecurityContextHolder.getContext().setAuthentication(authToken);
 * }
 * }
 * filterChain.doFilter(request, response);
 * }
 * }
 * 
 */