package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Order;

public interface OrderService {
	Order createOrder(int userId, int certificateId) throws NotFoundException;

	List<Order> findAllUserOrders(int userId, int page, int limit) throws NotFoundException, ServiceException;

	Order findUserOrderById(int userId, int orderId) throws NotFoundException;
}
