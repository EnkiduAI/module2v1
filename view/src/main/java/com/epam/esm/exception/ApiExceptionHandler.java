package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.epam.esm.exception.entity.ExceptionResponseBody;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponseBody> handle(Throwable e) {
		ExceptionResponseBody exception = new ExceptionResponseBody(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}

}
