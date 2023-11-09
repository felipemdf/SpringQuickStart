package com.felipemdf.SpringQuickStart.core.handler.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {
	
	 private HttpStatus status;
	 private String message;
	 
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	 private LocalDateTime dateTime = LocalDateTime.now();
	 
	public ApiError(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	} 
}