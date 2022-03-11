package com.epam.esm.exception;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.esm.exception.entity.ExceptionResponseBody;

@RestControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ExceptionResponseBody> handleBadRequest(ServiceException e) {
		Locale locale = LocaleContextHolder.getLocale();
		ExceptionResponseBody exception = new ExceptionResponseBody(HttpStatus.BAD_REQUEST.value(),
				messageSource.getMessage(e.getMessage(), null, locale), LocalDateTime.now().toString());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponseBody> handleNotFound(NotFoundException e) {
		Locale locale = LocaleContextHolder.getLocale();
		ExceptionResponseBody exception = new ExceptionResponseBody(HttpStatus.NOT_FOUND.value(),
				messageSource.getMessage(e.getMessage(), null, locale), LocalDateTime.now().toString());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}
}