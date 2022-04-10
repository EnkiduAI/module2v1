package com.epam.esm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.UserService;
import com.epam.esm.security.jwt.JwtUserFactory;

@Service
public class JwtUserDetailService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public JwtUserDetailService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		try {
			user = userService.findByUsername(username);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException("notFoundError.username");
		}
		return JwtUserFactory.create(user);
	}
}
