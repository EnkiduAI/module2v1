package com.epam.esm.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dao.impl.JdbcTemplateCertificateDaoImpl;
import com.epam.esm.model.dao.impl.JdbcTemplateOrderDaoImpl;
import com.epam.esm.model.dao.impl.JdbcTemplateUserDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private JdbcTemplateUserDaoImpl userDao;
	private JdbcTemplateCertificateDaoImpl certificateDao;
	private JdbcTemplateOrderDaoImpl orderDao;

	@Autowired
	public OrderServiceImpl(JdbcTemplateCertificateDaoImpl certificateDao, JdbcTemplateUserDaoImpl userDao,
			JdbcTemplateOrderDaoImpl orderDao) {
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

	@Override
	public Order findUserOrderById(int userId, int orderId) throws ServiceException{
		User user = userDao.findById(userId);
		if(user != null) {
			return orderDao.findUserOrderById(userId, orderId);
		}else {
			throw new ServiceException("Cannot find user");
		}
	}

	@Override
	public List<Order> findAllUserOrders(int userId) throws ServiceException {
		User user = userDao.findById(userId);
		if(user != null) {
			return orderDao.findAllUserOrders(userId);
		}else {
			throw new ServiceException("Cannot find user");
		}
	}

}
