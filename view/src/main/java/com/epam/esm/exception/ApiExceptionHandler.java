package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.esm.exception.entity.ExceptionResponseBody;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ExceptionResponseBody> handleBadRequest(ServiceException e) {
		ExceptionResponseBody exception = new ExceptionResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponseBody> handleNotFound(NotFoundException e) {
		ExceptionResponseBody exception = new ExceptionResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}
}