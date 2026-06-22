package com.dadneedsreport.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {
	
	// It can be improved
	private static final String otherError = "SERVICE TEMPORARY UNVALIABLE";
	private static final String jsonbadRequestmsg = "{ \"msg\" : \"Please see the documentation\"}";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<String> invalidJson(HttpMessageNotReadableException e) {
		return new ResponseEntity(jsonbadRequestmsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<String> anyOtherError(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otherError);
	}
}
