package com.epam.esm.persistence;

import java.util.List;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;


public interface UserPersistence {
	int createUser(User user);
	List<User> findAll(int page, int limit);
	User findById(int id);
	Tag findMostPopularTag();
	User findByUsername(String username);
	void updateUserRoles(int id, Role role);
}
