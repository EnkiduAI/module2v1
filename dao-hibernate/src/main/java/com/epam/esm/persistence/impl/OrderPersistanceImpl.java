package com.epam.esm.persistence.impl;


import static com.epam.esm.persistence.query.OrderQuery.FIND_ALL_USER_ORDERS;
import static com.epam.esm.persistence.query.OrderQuery.FIND_ORDER_BY_ID;
import static com.epam.esm.persistence.query.OrderQuery.FIND_USER_ORDER_BY_ID;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateError;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.EntityManagerHelper;
import com.epam.esm.persistence.OrderPersistance;

@Repository
public class OrderPersistanceImpl implements OrderPersistance {

	EntityManagerFactory factory = EntityManagerHelper.getFactory();

	@Override
	public int createOrder(User user, GiftCertificate certificate) {
		EntityManager em = factory.createEntityManager();
		int id;
		try {
			em.getTransaction().begin();
			Order order = new Order();
			order.setIdUser(user.getUserId());
			order.setIdCertificate(certificate.getId());
			order.setPrice(certificate.getPrice());
			order.setPurchaseDate(LocalDateTime.now());
			em.persist(order);
			id = order.getOrderId();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new HibernateError("Cannot create order");
		} finally {
			em.close();
		}
		return id;
	}

	@Override
	public List<Order> findAllUserOrders(int userId, int page, int limit) {
		EntityManager em = factory.createEntityManager();
		List<Order> orderList = new ArrayList<>();
		try {
			em.getTransaction().begin();
			TypedQuery<Order> query = em.createQuery(FIND_ALL_USER_ORDERS, Order.class).setParameter("id", userId);
				query.setFirstResult((page-1)*limit);
				query.setMaxResults(limit);
			orderList = query.getResultList();
			em.getTransaction().commit();
		} catch (NoResultException e) {
			em.getTransaction().rollback();
			return Collections.emptyList();
		} finally {
			em.close();
		}
		return orderList;
	}

	@Override
	public Order findUserOrderById(int userId, int orderId) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Order> query = em.createQuery(FIND_USER_ORDER_BY_ID, Order.class).setParameter("idUser", userId)
				.setParameter("idOrder", orderId);
		Order order = query.getSingleResult();
		em.close();
		return order;
	}

	@Override
	public Order findOrderById(int orderId) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Order> query= em.createQuery(FIND_ORDER_BY_ID, Order.class).setParameter("id", orderId);
		Order order = query.getSingleResult();
		em.close();
		return order;
	}

}
