package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.persistence.GiftCertificatePersistence;
import com.epam.esm.persistence.impl.OrderPersistanceImpl;
import com.epam.esm.persistence.impl.UserPersistenceImpl;
import com.epam.esm.validator.Validator;

@Service
public class OrderServiceImpl implements OrderService {

	private UserPersistenceImpl userDao;
	private GiftCertificatePersistence certificateDao;
	private OrderPersistanceImpl orderDao;

	private Validator validator = new Validator();;

	@Autowired
	public OrderServiceImpl(GiftCertificatePersistence certificateDao, UserPersistenceImpl userDao,
			OrderPersistanceImpl orderDao) {
		this.certificateDao = certificateDao;
		this.userDao = userDao;
		this.orderDao = orderDao;
	}

	@Transactional
	@Override
	public Order createOrder(int userId, int certificateId) throws NotFoundException {
		User user = userDao.findById(userId);
		GiftCertificate certificate = certificateDao.findById(certificateId);
		if (user == null) {
			throw new NotFoundException("notFoundError.user");
		}
		if (certificate == null) {
			throw new NotFoundException("notFoundError.certificate");
		}
		int orderId = orderDao.createOrder(user, certificate);
		return orderDao.findOrderById(orderId);
	}

	@Transactional
	@Override
	public Order findUserOrderById(int userId, int orderId) throws NotFoundException {
		User user = userDao.findById(userId);
		Order order = orderDao.findOrderById(orderId);
		if (order == null) {
			throw new NotFoundException("notFoundError.order");
		}
		if (user != null) {
			return orderDao.findUserOrderById(userId, orderId);
		} else {
			throw new NotFoundException("notFoundError.user");
		}
	}

	@Transactional
	@Override
	public List<Order> findAllUserOrders(int userId, int page, int limit) throws NotFoundException, ServiceException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("serviceException.pageSize");
		}
		User user = userDao.findById(userId);
		if (user != null) {
			return orderDao.findAllUserOrders(userId, page, limit);
		} else {
			throw new NotFoundException("notFoundError.user");
		}
	}

}
