package com.epam.esm.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	/** Tag create query */
	private static final String CREATE_TAG = """
			insert into certificates.tag values(null, ?)
			""";

	/** Delete query */
	private static final String DELETE_TAG = """
			delete from certificates.tag where id=?
			""";

	/** Search by id query */
	private static final String FIND_BY_ID = """
			select tag.id, tag.tag_name from certificates.tag where id=?
			""";
	
	private static final String FIND_BY_NAME = """
			select id, tag_name from tag where tag_name = ?
			""";

	/** Find all tags query */
	private static final String FIND_ALL_TAGS = """
			select tag.id, tag.tag_name from certificates.tag
			""";
	
	
	/** Unbind tag query */
	private static final String UNBIND_TAG = """
			delete from certificates.gift_certificate_has_tag as gt
			where gt.tag_id = ?
			""";

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/**
	 * Instantiates a new jdbc template tag dao impl.
	 *
	 * @param jdbcTemplate the jdbc template
	 */
	@Autowired
	public JdbcTemplateTagDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Creates tag.
	 *
	 * @param name the name
	 * @return the int
	 */
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

	/**
	 * Delete tag.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int delete(int id) {
		return jdbcTemplate.update(DELETE_TAG, id);
	}

	/**
	 * Find tag by id.
	 *
	 * @param id the id
	 * @return the tag
	 */
	@Override
	public Tag findById(int id) {
		return jdbcTemplate.queryForObject(FIND_BY_ID, new TagMapper(), id);
	}

	/**
	 * Find all tags.
	 *
	 * @return the list
	 */
	@Override
	public List<Tag> findAll() {
		return jdbcTemplate.query(FIND_ALL_TAGS, new TagMapper());
	}

	/**
	 * Unbind tag.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int unbindTag(int id) {
		return jdbcTemplate.update(UNBIND_TAG, id);
	}

	@Override
	public Tag findByName(String name) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_NAME, new TagMapper(), name);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

}
