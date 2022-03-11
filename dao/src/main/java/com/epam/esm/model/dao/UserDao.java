package com.epam.esm.model.dao;

import java.util.List;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

public interface UserDao {

	List<User> findAll(int page, int limit);
	User findById(int id);
	Tag findMostPopularTag();
}
