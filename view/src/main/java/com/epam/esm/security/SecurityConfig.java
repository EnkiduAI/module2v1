package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.context.request.RequestContextListener;

import com.epam.esm.security.exception.RestAccessDeniedHandler;
import com.epam.esm.security.exception.RestAthenticationEntryPoint;
import com.epam.esm.security.jwt.JwtConfigurer;
import com.epam.esm.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RestAccessDeniedHandler accessDeniedHandler;
	private final RestAthenticationEntryPoint entryPointHandler;

	@Autowired
	public SecurityConfig(JwtTokenProvider jwtTokenProvider, RestAccessDeniedHandler accessDeniedHandler,
			RestAthenticationEntryPoint entryPointHandler) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.accessDeniedHandler = accessDeniedHandler;
		this.entryPointHandler = entryPointHandler;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/view/api/admin/**").hasRole("ADMIN")
				.antMatchers("/view/api/order/**", "/view/api/orders", "/view/api/certificateWithTag", "/view/api/tags",
						"/view/api/tag/**")
				.authenticated()
				.antMatchers("/view/api/signup", "/view/api/login", "/view/api/certificates",
						"/view/api/certificate/**", "/login")
				.permitAll().and()
				.formLogin().permitAll()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(entryPointHandler).and().oauth2Login()
				.and()
				.apply(new JwtConfigurer(jwtTokenProvider));
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
