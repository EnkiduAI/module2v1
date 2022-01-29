package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

public interface UserService {

	User findById(int id)throws ServiceException;
	List<User>findAll(int page, int limit)throws ServiceException;
	Tag findMostPopularTag() throws ServiceException;
}
