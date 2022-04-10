package com.epam.esm.security.jwt;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.epam.esm.model.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Value("${jwt.token.secret}")
	private String secret;
	@Value("${jwt.token.expired}")
	private long validInMilliseconds;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}

	public String createToken(String userName, Set<Role> role) {
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put("roles", getRoleNames(role));
		Date now = new Date();
		Date valid = new Date(now.getTime() + validInMilliseconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(valid)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails,"" ,userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearer = req.getHeader("Authorization");
		if(bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7, bearer.length());
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return !(claims.getBody().getExpiration().before(new Date()));
		}catch(JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private List<String> getRoleNames(Set<Role> roles) {
		List<String> result = new ArrayList<>();
		roles.forEach(role -> result.add(role.getRoleName()));
		return result;
	}
}
