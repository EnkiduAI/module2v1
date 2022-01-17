package com.epam.esm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.service.impl.OrderServiceImpl;

@RestController
@ComponentScan(basePackages = { "com.epam.esm" })
@RequestMapping("/view/api/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl service;

	private DtoConverter converter = DtoConverter.getInstance();

	@PostMapping("/user/{userId}/certificate/{certificateId}")
	@ResponseBody
	public ResponseEntity<OrderDto> createOrder(@PathVariable("userId") int userId,
			@PathVariable("certificateId") int certificateId) throws ServiceException {
		Order order = new Order();
		try {
			order = service.createOrder(userId, certificateId);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertOrder(order), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/order/{orderId}")
	@ResponseBody
	public ResponseEntity<OrderDto> findOrderById(@PathVariable("userId") int userId,
			@PathVariable("orderId") int orderId) throws ServiceException{
		Order order = new Order();
		try {
			order = service.findUserOrderById(userId, orderId);
		}catch(ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertOrder(order), HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	@ResponseBody
	public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable("userId") int userId) throws ServiceException{
		List<Order> orderList = new ArrayList<>();
		try {
			orderList = service.findAllUserOrders(userId);
		}catch(ServiceException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(converter.convertOrderList(orderList), HttpStatus.OK);
	}
}
