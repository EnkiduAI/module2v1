package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

public interface UserService {
	UserDto createUser(User user) throws NotFoundException;
	User findById(int id)throws ServiceException, NotFoundException;
	List<User>findAll(int page, int limit)throws ServiceException;
	Tag findMostPopularTag() throws ServiceException;
	User findByUsername(String username) throws NotFoundException;
}
