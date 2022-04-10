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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.epam.esm.exception.entity.ExceptionResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Locale locale = request.getLocale();
		ExceptionResponseBody responseBody = new ExceptionResponseBody(HttpStatus.UNAUTHORIZED.value(),
				messageSource.getMessage("authenticationError.unauthorized", null, locale),
				LocalDateTime.now().toString());
		response.setStatus(401);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, responseBody);
		out.flush();
	}

}
