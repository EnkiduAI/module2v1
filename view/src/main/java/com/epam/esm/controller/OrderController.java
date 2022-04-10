package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.impl.OrderServiceImpl;
import com.epam.esm.model.service.impl.UserServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api")
public class OrderController {

	@Autowired
	private OrderServiceImpl service;

	@Autowired
	private UserServiceImpl userService;

	private DtoConverter converter = DtoConverter.getInstance();

	@PostMapping("/order")
	@ResponseBody
	public ResponseEntity<OrderDto> createOrder(@RequestBody int certificateId) throws NotFoundException {
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Order order = service.createOrder(user.getUserId(), certificateId);
		return new ResponseEntity<>(converter.convertOrder(order), HttpStatus.OK);
	}

	@GetMapping("/order/{orderId}")
	@ResponseBody
	public ResponseEntity<OrderDto> findOrderById(@PathVariable("orderId") int orderId)
			throws ServiceException, NotFoundException {
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Order order = service.findUserOrderById(user.getUserId(), orderId);
		OrderDto orderDto = converter.convertOrder(order);
		orderDto.add(linkTo(methodOn(UserController.class).findUserById(user.getUserId())).withRel("get user"));
		orderDto.add(linkTo(methodOn(CertificateController.class).getCertificateById(orderDto.getCertificateId()))
				.withRel("get certificate"));
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}

	@GetMapping("/admin/orders/{userId}")
	@ResponseBody
	public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable("userId") int userId,
			@QueryParam("page") int page, @QueryParam("limit") int limit) throws ServiceException, NotFoundException {
		List<Order> orderList = service.findAllUserOrders(userId, page, limit);
		List<OrderDto> orderDto = converter.convertOrderList(orderList);
		for (OrderDto order : orderDto) {
			order.add(linkTo(methodOn(UserController.class).findUserById(userId)).withRel("get user"));
			order.add(linkTo(methodOn(CertificateController.class).getCertificateById(order.getCertificateId()))
					.withRel("get certificate"));
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}

	@GetMapping("/orders")
	@ResponseBody
	public ResponseEntity<List<OrderDto>> getOrders(@QueryParam("page") int page, @QueryParam("limit") int limit)
			throws ServiceException, NotFoundException {
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Order> orderList = service.findAllUserOrders(user.getUserId(), page, limit);
		List<OrderDto> orderDto = converter.convertOrderList(orderList);
		for (OrderDto order : orderDto) {
			order.add(linkTo(methodOn(UserController.class).findUserById(user.getUserId())).withRel("get user"));
			order.add(linkTo(methodOn(CertificateController.class).getCertificateById(order.getCertificateId()))
					.withRel("get certificate"));
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}
}
