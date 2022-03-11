package com.epam.esm.model.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.model.entity.GiftCertificate;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

	@Override
	public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
		GiftCertificate certificate = new GiftCertificate();
		certificate.setId(rs.getInt("cert_id"));
		certificate.setName(rs.getString("name"));
		certificate.setDescription(rs.getString("description"));
		certificate.setPrice(rs.getInt("price"));
		certificate.setDuration(rs.getString("duration"));
		certificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
		certificate.setUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
		return certificate;
	}

}
