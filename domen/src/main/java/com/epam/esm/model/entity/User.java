package com.epam.esm.model.entity;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class User {
	
	private int userId;
	private String userName;
	private String userSurname;
	
	public User() {
		
	}
	
	public User(int userId, String userName, String userSurname) {
		this.userId = userId;
		this.userName = userName;
		this.userSurname = userSurname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, userName, userSurname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return userId == other.userId && Objects.equals(userName, other.userName)
				&& Objects.equals(userSurname, other.userSurname);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userSurname=");
		builder.append(userSurname);
		builder.append("]");
		return builder.toString();
	}
}
