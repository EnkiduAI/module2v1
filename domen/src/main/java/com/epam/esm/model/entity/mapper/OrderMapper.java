package com.epam.esm.model.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.model.entity.Order;

public class OrderMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("id_order"));
		order.setUserId(rs.getInt("id_user"));
		order.setUserName(rs.getString("user_name"));
		order.setUserSurname(rs.getString("user_surname"));
		order.setCertificateName(rs.getString("name"));
		order.setCertificateDescription(rs.getString("description"));
		order.setPrice(rs.getInt("price"));
		order.setPurchaseDate(rs.getTimestamp("purchase_date").toLocalDateTime());
		return order;
	}

}
