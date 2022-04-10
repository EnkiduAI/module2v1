package com.epam.esm.security.jwt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;

public final class JwtUserFactory {

	public JwtUserFactory() {

	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getUserId(), user.getUserName(), user.getUserSurname(), user.getLogin(),
				user.getPassword(), mapToGrantedAuthorities(new HashSet<>(user.getRoles())));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

}
