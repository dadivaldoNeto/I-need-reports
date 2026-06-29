package com.dadneedsreport.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dadneedsreport.dto.StringResponse;

@RestControllerAdvice
public class HandleException {

	// It can be improved
	private static final String otherError = "SERVICE TEMPORARY UNVALIABLE";

	private static final String invalidMedia = "Invalid Media Type";	
	private static final String invaliArg = "Invalid Request";
	private static final String badRequest = "EndPoint NotFound, Please see the documentation";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<?> invalidJson(HttpMessageNotReadableException e) {
		return error(HttpStatus.BAD_REQUEST, invalidMedia);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	ResponseEntity<?> invalidRequest(HttpMediaTypeNotSupportedException e) {
		return error(HttpStatus.BAD_REQUEST, invaliArg);
	}

	//End point not found
	@ExceptionHandler(NoResourceFoundException.class)
	public static ResponseEntity<StringResponse> resourceNotFound() {
		return error(HttpStatus.BAD_REQUEST, badRequest);
	}

	//
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<?> argumentErros(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach( 
			(e) -> errors.put(e.getField(), e.getDefaultMessage())
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<?> anyOtherError(Exception ex) {
		System.out.println(ex.toString());
		return error(HttpStatus.INTERNAL_SERVER_ERROR, otherError);
	}

	public static ResponseEntity<StringResponse> error(HttpStatus status, String message) {
		if (status == null)
				status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(new StringResponse(message));
	}
}
