package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.OrderService;
import com.epam.esm.persistence.GiftCertificatePersistance;
import com.epam.esm.persistence.impl.OrderPersistanceImpl;
import com.epam.esm.persistence.impl.UserPersistenceImpl;

@Service
public class OrderServiceImpl implements OrderService {

	private UserPersistenceImpl userDao;
	private GiftCertificatePersistance certificateDao;
	private OrderPersistanceImpl orderDao;

	@Autowired
	public OrderServiceImpl(GiftCertificatePersistance certificateDao, UserPersistenceImpl userDao,
			OrderPersistanceImpl orderDao) {
		this.certificateDao = certificateDao;
		this.userDao = userDao;
		this.orderDao = orderDao;
	}

	@Transactional
	@Override
	public Order createOrder(int userId, int certificateId) throws ServiceException {
		User user = userDao.findById(userId);
		GiftCertificate certificate = certificateDao.findById(certificateId);
		if (user != null) {
			if (certificate != null) {
				int orderId = orderDao.createOrder(user, certificate);
				return orderDao.findOrderById(orderId);
			} else {
				throw new ServiceException("Cannot find certificate");
			}
		} else {
			throw new ServiceException("Cannot find user");
		}
	}

	@Transactional
	@Override
	public Order findUserOrderById(int userId, int orderId) throws ServiceException{
		User user = userDao.findById(userId);
		if(user != null) {
			return orderDao.findUserOrderById(userId, orderId);
		}else {
			throw new ServiceException("Cannot find user");
		}
	}

	@Transactional
	@Override
	public List<Order> findAllUserOrders(int userId, int page, int limit) throws ServiceException {
		User user = userDao.findById(userId);
		if(user != null) {
			return orderDao.findAllUserOrders(userId, page, limit);
		}else {
			throw new ServiceException("Cannot find user");
		}
	}

}
