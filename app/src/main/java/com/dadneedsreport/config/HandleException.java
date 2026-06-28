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

import com.dadneedsreport.dto.StringResponse;

@RestControllerAdvice
public class HandleException {

	// It can be improved
	private static final String otherError = """
			{
				error : SERVICE TEMPORARY UNVALIABLE
			}
			""";

	private static final String badRequest = """
			{
				error : SEE THE DOCUMENTATION
			}
			""";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<String> invalidJson(HttpMessageNotReadableException e) {
		return new ResponseEntity<String>(badRequest, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	ResponseEntity<String> invalidRequest(HttpMediaTypeNotSupportedException e) {
		return new ResponseEntity<String>(badRequest, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<?> argumentErros(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		
		
		ex.getBindingResult().getFieldErrors().forEach( 
			(e) -> errors.put(e.getField(), e.getDefaultMessage())
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<String> anyOtherError(Exception ex) {
		System.out.println(ex.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otherError);
	}

	public static ResponseEntity<StringResponse> error(HttpStatus status, String message) {
		if (status == null)
				status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(new StringResponse(message));
	}
}
