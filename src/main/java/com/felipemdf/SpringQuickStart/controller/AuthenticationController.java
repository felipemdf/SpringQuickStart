package com.felipemdf.SpringQuickStart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipemdf.SpringQuickStart.dto.request.LoginRequest;
import com.felipemdf.SpringQuickStart.dto.request.SignUpRequest;
import com.felipemdf.SpringQuickStart.dto.response.JwtResponse;
import com.felipemdf.SpringQuickStart.model.User;
import com.felipemdf.SpringQuickStart.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
		throw new MalformedJwtException("teste");
//		User user = userService.register(signUpRequest);
//	    
//	    return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) { 
		JwtResponse jwtResponse = userService.authenticate(loginRequest);
		
		return ResponseEntity.ok(jwtResponse);
	}
}
