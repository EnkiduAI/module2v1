package com.epam.esm.model.dao;

import java.util.List;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;

public interface OrderDao {
	int createOrder(User user, GiftCertificate certificate);
	List<Order> findAllUserOrders(int userId, int page, int limit);
	Order findUserOrderById(int userId, int orderId);
	Order findOrderById(int orderId);
}
