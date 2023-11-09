package com.felipemdf.SpringQuickStart.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String accessToken ;
    private List<String> roles;
    
	public JwtResponse(String accessToken, List<String> roles) {
		super();
		this.accessToken = accessToken;
		this.roles = roles;
	}
    
    
}
