package com.epam.esm.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.mapper.TagMapper;

@Repository
public class JdbcTemplateUserDaoImpl implements UserDao {

	private static final String FIND_ALL = """
			select user_id, user_name, user_surname
			from users limit ?,?
			""";
	private static final String FIND_BY_ID = """
			select user_id, user_name, user_surname
			from users
			where user_id = ?
			""";
	private static final String FIND_MOST_POPULAR_TAG = """
						select tag.id, tag_name
						from certificates.order
						join gift_certificate_has_tag on gift_certificate_has_tag.gift_certificate_id = id_certificate
						join tag on tag.id = gift_certificate_has_tag.tag_id
						where id_user = (select id_user
						from certificates.order
						group by id_user
						order by sum(certificates.order.price) desc
						limit 1)
						group by tag_name
						order by count(tag_id) desc
						limit 1
						""";

	JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateUserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> findAll(int page, int limit) {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(User.class), (page - 1)* limit, limit);
	}

	@Override
	public User findById(int id) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Tag findMostPopularTag() {
		return jdbcTemplate.queryForObject(FIND_MOST_POPULAR_TAG, new TagMapper());
	}
}
