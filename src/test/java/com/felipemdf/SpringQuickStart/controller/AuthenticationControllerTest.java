package com.felipemdf.SpringQuickStart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipemdf.SpringQuickStart.dto.request.LoginRequest;
import com.felipemdf.SpringQuickStart.dto.request.SignUpRequest;
import com.felipemdf.SpringQuickStart.dto.response.JwtResponse;
import com.felipemdf.SpringQuickStart.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {
	
		@Autowired
		private MockMvc mockMvc;
		
		@MockBean
		private UserService userService;
	
		@Test
		void authenticate () throws Exception {
			LoginRequest loginRequest = new LoginRequest();
	        loginRequest.setUsername("admin");
	        loginRequest.setPassword("admin");
	        
	        JwtResponse jwtResponse = new JwtResponse("fakeJwtToken", Arrays.asList("ROLE_ADMIN"));
	        
	        Mockito.when(userService.authenticate(Mockito.any(LoginRequest.class))).thenReturn(jwtResponse);
	        
	        
	        this.mockMvc.perform(post("/api/auth/signin")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(new ObjectMapper().writeValueAsString(loginRequest)))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value(jwtResponse.getAccessToken()))
		            .andExpect(MockMvcResultMatchers.jsonPath("$.roles").exists())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0]").value("ROLE_ADMIN"));
	       
	        
	        Mockito.verify(userService, Mockito.times(1)).authenticate(Mockito.eq(loginRequest));
		}
		
		@Test
		void registerUser() throws Exception {
			SignUpRequest signUpRequest = new SignUpRequest();
	        signUpRequest.setUsername("admin");
	        signUpRequest.setEmail("admin@gmail.com");
	        signUpRequest.setPassword("admin123");
	        
	        
	        this.mockMvc.perform(post("/api/auth/signup")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(new ObjectMapper().writeValueAsString(signUpRequest)))
	        		.andExpect(MockMvcResultMatchers.status().isCreated());
	        
	        
	        Mockito.verify(userService, Mockito.times(1)).register(Mockito.eq(signUpRequest));
		}
}
