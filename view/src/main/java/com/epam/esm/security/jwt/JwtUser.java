package com.epam.esm.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id;
	private final String userName;
	private final String userSurname;
	private final String login;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public JwtUser(int id, String userName, String userSurname, String login, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.userSurname = userSurname;
		this.login = login;
		this.password = password;
		this.authorities = authorities;
	}

	public int getId() {
		return id;
	}

	public String getUserFirstName() {
		return userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	
	@JsonIgnore
	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
