package com.epam.esm.model.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.model.entity.CertificateWithTag;

public class CertificateWithTagMapper implements RowMapper<CertificateWithTag>{

	@Override
	public CertificateWithTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		CertificateWithTag certTag = new CertificateWithTag();
		certTag.setTagId(rs.getInt("t.id_tag"));
		certTag.setTagName(rs.getString("t.tag_name"));
		certTag.setCertificateId(rs.getInt("gc.cert_id"));
		certTag.setCertificateName(rs.getString("gc.name"));
		certTag.setDescription(rs.getString("gc.description"));
		certTag.setPrice(rs.getInt("gc.price"));
		certTag.setDuration(rs.getString("gc.duration")+" days");
		certTag.setCreateDate(rs.getTimestamp("gc.create_date").toLocalDateTime());
		certTag.setLastUpdateDate(rs.getTimestamp("gc.last_update_date").toLocalDateTime());
		return certTag;
	}

}
