package com.epam.esm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.UserService;
import com.epam.esm.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/view/api/login")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService service;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService service) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequestDto request) throws NotFoundException{
		try {
			String userName = request.getLogin();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, request.getPassword()));
			User expectedUser = service.findByUsername(userName);
			String token = jwtTokenProvider.createToken(userName, expectedUser.getRoles());
			Map<String, Object> response = new HashMap<>();
			response.put("username", userName);
			response.put("token", token);
			return ResponseEntity.ok(response);
		}catch(AuthenticationException e) {
			throw new BadCredentialsException("badCredentialsError.auth");
		}
	}
}
