package com.felipemdf.SpringQuickStart.core.handler.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
	// Generalized Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex){
        ApiError apiError = new ApiError(
        		HttpStatus.INTERNAL_SERVER_ERROR, 
        		"An unexpected error occurred. Please contact support for assistance."
        );

        return buildResponseEntity(apiError, ex);
    }
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError, Exception ex) {
		logger.error(apiError.getMessage(), ex);
		
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
