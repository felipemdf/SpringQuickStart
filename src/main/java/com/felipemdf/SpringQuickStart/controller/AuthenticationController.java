package com.felipemdf.SpringQuickStart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipemdf.SpringQuickStart.dto.request.LoginRequest;
import com.felipemdf.SpringQuickStart.dto.request.SignUpRequest;
import com.felipemdf.SpringQuickStart.dto.response.JwtResponse;
import com.felipemdf.SpringQuickStart.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/signup")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
		userService.register(signUpRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) { 
		JwtResponse jwtResponse = userService.authenticate(loginRequest);
		
		return ResponseEntity.ok(jwtResponse);
	}
}
