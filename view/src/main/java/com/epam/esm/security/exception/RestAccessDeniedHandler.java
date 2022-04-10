package com.epam.esm.security.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.epam.esm.exception.entity.ExceptionResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler{
	
	@Autowired
	private ResourceBundleMessageSource messageSource;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Locale locale = request.getLocale();
		ExceptionResponseBody responseBody = new ExceptionResponseBody(HttpStatus.FORBIDDEN.value(),
				messageSource.getMessage("authenticationError.forbidden", null, locale),
				LocalDateTime.now().toString());
		response.setStatus(403);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, responseBody);
		out.flush();
	}

}
