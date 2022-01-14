package com.epam.esm.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.mapper.OrderMapper;

public class JdbcTemplateOrderDaoImpl implements OrderDao{
	
	private static final String CREATE_ORDER = """
			insert into order values(null,?,?,?,?)
			""";
	private static final String FIND_ALL_USER_ORDERS = """
			select order_id, c.name, c.description,
			price, purchase_date
			from order
			join gift_certificate as c on c.id = order.id_certificate
			where id_user = ? 
			""";
	private static final String FIND_USER_ORDER_BY_ID = """
			select order_id, c.name, c.description,
			price, purchase_date
			from order
			join gift_certificate as c on c.id = order.id_certificate
			where id_user = ? and order_id = ?
			""";
	private static final String FIND_ORDER_BY_ID = """
			select order_id, c.name, c.description,
			price, purchase_date
			from order
			join gift_certificate as c on c.id = order.id_certificate
			where order_id = ?
			""";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcTemplateOrderDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int createOrder(User user, GiftCertificate certificate) {
		KeyHolder key = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, user.getUserId());
				ps.setInt(2, certificate.getId());
				ps.setInt(3, certificate.getPrice());
				ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
				return ps;
			}
		}, key);
		return key.getKey().intValue();
	}

	@Override
	public List<Order> findAllUserOrders(int userId) {
		return jdbcTemplate.query(FIND_ALL_USER_ORDERS, new OrderMapper(), userId);
	}

	@Override
	public Order findUserOrderById(int userId, int orderId) {
		return jdbcTemplate.queryForObject(FIND_USER_ORDER_BY_ID, new OrderMapper(), userId, orderId);
	}

	@Override
	public Order findOrderById(int orderId) {
		return jdbcTemplate.queryForObject(FIND_ORDER_BY_ID, new OrderMapper(), orderId);
	}

}
