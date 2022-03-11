package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistence.impl.UserPersistenceImpl;
import com.epam.esm.validator.Validator;

@Service
public class UserServiceImpl implements UserService {

	private UserPersistenceImpl userDao;
	private Validator validator = new Validator();

	@Autowired
	public UserServiceImpl(UserPersistenceImpl userDao) {
		this.userDao = userDao;
	}

	@Override
	public User findById(int id) throws ServiceException, NotFoundException {
		User user = userDao.findById(id);
		if(user == null) {
			throw new NotFoundException("notFoundError.user");
		}
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll(int page, int limit) throws ServiceException {
		if(!validator.isPageble(page, limit)) {
			throw new ServiceException("serviceException.pageSize");
		}
		return userDao.findAll(page, limit);
	}

	@Override
	public Tag findMostPopularTag() throws ServiceException {
		return userDao.findMostPopularTag();
	}
}
