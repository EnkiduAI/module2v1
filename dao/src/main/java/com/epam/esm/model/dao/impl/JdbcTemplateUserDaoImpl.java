package com.epam.esm.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;

@Repository
public class JdbcTemplateUserDaoImpl implements UserDao {

	private static final String FIND_ALL = """
			select user_id, user_name, user_surname
			from users
			""";
	private static final String FIND_BY_ID = """
			select user_id, user_name, user_surname
			from users
			where user_id = ?
			""";

	JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateUserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public User findById(int id) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
