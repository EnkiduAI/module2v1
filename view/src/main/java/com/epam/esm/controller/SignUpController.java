package com.epam.esm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.UserServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/signup")
public class SignUpController {

	@Autowired
	private UserServiceImpl service;
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<UserDto> signUp(@RequestBody User user) throws NotFoundException{
		UserDto createdUser = service.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.OK);
	}
}
