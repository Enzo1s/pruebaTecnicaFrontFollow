package com.api.vital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.vital.models.entity.User;
import com.api.vital.service.jwt.JWTService;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTtokenFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		try {
		if(token != null && !token.equals("Invalid credentials")) {
			boolean verified = jwtService.verifyToken(token);
			if(verified) {
				User user = jwtService.getUser(token);
				if(user != null) {
					List<GrantedAuthority> authorities = new ArrayList<>();
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null,
							authorities);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		filterChain.doFilter(request, response);
		}catch (TokenExpiredException e) {
			
			ObjectMapper mapper = new ObjectMapper();

	        HttpServletResponse responseError = (HttpServletResponse) response;
	        Map<String, Object> responseDetails = new HashMap<>();
	        responseDetails.put("error", "TokenExpiredException");
	        responseDetails.put("status", HttpStatus.UNAUTHORIZED);

	        responseError.setStatus(HttpStatus.UNAUTHORIZED.value());
	        responseError.setContentType(MediaType.APPLICATION_JSON_VALUE);

	        mapper.writeValue(responseError.getWriter(), responseDetails);
		}
	}
	
	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(header != null && !header.equals("null"))
			return header;
		return null;
	}

}
