package com.epam.esm.model.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.bcrypt.Bcrypt;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.UserService;
import com.epam.esm.persistence.impl.RolePersistenceImpl;
import com.epam.esm.persistence.impl.UserPersistenceImpl;
import com.epam.esm.validator.Validator;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	private UserPersistenceImpl userDao;
	private RolePersistenceImpl roleDao;
	private Validator validator = new Validator();
	private DtoConverter converter = DtoConverter.getInstance();

	public UserServiceImpl() {
		
	}
	
	@Autowired
	public UserServiceImpl(UserPersistenceImpl userDao, RolePersistenceImpl roleDao) {
		this.userDao = userDao;
		this.roleDao = roleDao;
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

	@Override
	public UserDto createUser(User user) throws NotFoundException{
		user.setPassword(Bcrypt.hashPassword(user.getPassword()));
		int id = userDao.createUser(user);
		userDao.updateUserRoles(id, roleDao.findByName("USER"));
		User newUser = userDao.findById(id);
		return converter.convertUser(newUser);
	}

	@Override
	public User findByUsername(String username) throws NotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null) {
			throw new NotFoundException("notFoundError.username");
		}
		return user;
	}
}
