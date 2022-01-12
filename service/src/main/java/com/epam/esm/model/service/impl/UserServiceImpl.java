package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dao.impl.JdbcTemplateUserDao;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private JdbcTemplateUserDao userDao;

	@Autowired
	public UserServiceImpl(JdbcTemplateUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findById(int id) throws ServiceException {
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() throws ServiceException {
		return userDao.findAll();
	}
}