package com.felipemdf.SpringQuickStart.dto.request;

import java.util.Set;

import com.felipemdf.SpringQuickStart.model.enums.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
	
	@NotNull
	@Size(max = 45)
	private String username;
	
	@NotNull
	@Size(max = 45)
	@Email
	private String email;
	
	@NotNull
	@Size(min= 6, max = 45)
	private String password;
	
	private Set<RoleEnum> roles;
}
