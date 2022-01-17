package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.dto.pagination.DtoPagination;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.UserServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/user")
public class UserController {

	private DtoPagination<UserDto> pagination = new DtoPagination<>();

	private DtoConverter converter = DtoConverter.getInstance();

	@Autowired
	private UserServiceImpl service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<UserDto>> findAllUsers(@QueryParam("page") Optional<Integer> page,
			@QueryParam("limit") Optional<Integer> limit) throws ServiceException {
		List<User> userList = new ArrayList<>();
		try {
			userList = service.findAll();
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<UserDto> userDto = converter.convertUserList(userList);
		if(page.isPresent() && limit.isPresent()) {
			return new ResponseEntity<>(pagination.getPage(userDto, page.get(), limit.get()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(userDto, HttpStatus.OK);
		}
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

	@GetMapping("/popular/tag")
	@ResponseBody
	public ResponseEntity<TagDto> getMostPopularTag() throws ServiceException {
		Tag tag = new Tag();
		try {
			tag = service.findMostPopularTag();
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}
}
