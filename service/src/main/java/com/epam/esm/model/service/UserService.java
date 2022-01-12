package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.User;

public interface UserService {

	User findById(int id)throws ServiceException;
	List<User>findAll()throws ServiceException;
}
