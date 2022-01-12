package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.UserServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/user")
public class UserController {

	private DtoConverter converter = DtoConverter.getInstance();

	@Autowired
	private UserServiceImpl service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<UserDto>> findAllUsers() throws ServiceException {
		List<User> userList = new ArrayList<>();
		try {
			userList = service.findAll();
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertUserList(userList), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") int id) {
		User user = new User();
		try {
			user = service.findById(id);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertUser(user), HttpStatus.OK);
	}
}
