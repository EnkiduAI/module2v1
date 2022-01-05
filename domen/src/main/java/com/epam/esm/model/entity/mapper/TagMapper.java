package com.epam.esm.model.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.model.entity.Tag;

public class TagMapper implements RowMapper<Tag>{

	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = new Tag();
		tag.setId(rs.getInt("id"));
		tag.setName(rs.getString("tag_name"));
		return tag;
	}
}
