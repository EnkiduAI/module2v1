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
import org.springframework.stereotype.Repository;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.mapper.OrderMapper;
@Repository
public class JdbcTemplateOrderDaoImpl implements OrderDao{
	
	private static final String CREATE_ORDER = """
			insert into certificates.order values(null,?,?,?,?)
			""";
	private static final String FIND_ALL_USER_ORDERS = """
			select id_order, id_user, u.user_name, u.user_surname, c.name, c.description,
			o.price, purchase_date
			from certificates.order as o
			join gift_certificate as c on c.id = o.id_certificate
			join users as u on u.user_id = id_user
			where id_user = ? 
			""";
	private static final String FIND_USER_ORDER_BY_ID = """
			select id_order, id_user, user_name, user_surname, c.name, c.description,
			o.price, purchase_date
			from certificates.order as o
			join gift_certificate as c on c.id = o.id_certificate
			join users on users.user_id = o.id_user
			where id_user = ? and id_order = ?
			""";
	private static final String FIND_ORDER_BY_ID = """
			select id_order, id_user, u.user_name, u.user_surname, c.name, c.description,
			o.price, purchase_date
			from certificates.order as o
			join gift_certificate as c on c.id = o.id_certificate
			join users as u on u.user_id = o.id_user
			where id_order = ?
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
