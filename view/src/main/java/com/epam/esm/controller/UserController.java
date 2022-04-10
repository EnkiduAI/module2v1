package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

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
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.UserServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api")
public class UserController {

	private DtoConverter converter = DtoConverter.getInstance();

	@Autowired
	private UserServiceImpl service;

	@GetMapping("/admin/users")
	@ResponseBody
	public ResponseEntity<List<UserDto>> findAllUsers(@QueryParam("page") int page, @QueryParam("limit") int limit)
			throws ServiceException, NotFoundException {
		List<User> userList = service.findAll(page, limit);
		List<UserDto> userDto = converter.convertUserList(userList);
		for (UserDto user : userDto) {
			user.add(linkTo(methodOn(UserController.class).findUserById(user.getUserId())).withSelfRel());
		}
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@GetMapping("admin/user/{id}")
	@ResponseBody
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") int id) throws ServiceException, NotFoundException {
		User user = service.findById(id);
		UserDto userDto = converter.convertUser(user);
		userDto.add(linkTo(methodOn(UserController.class).findAllUsers(1, 5)).withRel("find all users"));
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@GetMapping("/popular")
	@ResponseBody
	public ResponseEntity<TagDto> getMostPopularTag() throws ServiceException {
		Tag tag = service.findMostPopularTag();
		return new ResponseEntity<>(converter.convertTag(tag), HttpStatus.OK);
	}
}
