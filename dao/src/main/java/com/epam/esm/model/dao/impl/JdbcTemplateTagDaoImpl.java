package com.epam.esm.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.mapper.TagMapper;

@Repository
public class JdbcTemplateTagDaoImpl implements TagDao {

	private static final String CREATE_TAG = """
			insert into certificates.tag values(null, ?)
			""";

	private static final String DELETE_TAG = """
			delete from certificates.tag where id=?
			""";

	private static final String FIND_BY_ID = """
			select tag.id, tag.tag_name from certificates.tag where id=?
			""";

	private static final String FIND_ALL_TAGS = """
			select tag.id, tag.tag_name from certificates.tag
			""";
	private static final String UNBIND_TAG = """
			delete from certificates.gift_certificate_has_tag as gt
			where gt.tag_id = ?
			""";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateTagDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int create(String name) {
		KeyHolder key = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(CREATE_TAG, java.sql.Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				return ps;
			}
		}, key);

		return key.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		return jdbcTemplate.update(DELETE_TAG, id);
	}

	@Override
	public Tag findById(int id) {
		return jdbcTemplate.queryForObject(FIND_BY_ID, new TagMapper(), id);
	}

	@Override
	public List<Tag> findAll() {
		return jdbcTemplate.query(FIND_ALL_TAGS, new TagMapper());
	}

	@Override
	public int unbindTag(int id) {
		return jdbcTemplate.update(UNBIND_TAG, id);
	}

}
