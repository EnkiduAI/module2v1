package com.epam.esm.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.mapper.CertificateWithTagMapper;
import com.epam.esm.model.entity.mapper.GiftCertificateMapper;

@Repository
public class JdbcTemplateCertificateDaoImpl implements CertificateDao {

	private static final String FIND_BY_ID = "select gift_certificate.id, gift_certificate.name, "
			+ "gift_certificate.description, gift_certificate.price, "
			+ "gift_certificate.duration, gift_certificate.create_date, "
			+ "gift_certificate.last_update_date from gift_certificate " + "where gift_certificate.id =?";

	private static final String FIND_ALL = """
			select gift_certificate.id, gift_certificate.name,
			gift_certificate.description, gift_certificate.price,
			gift_certificate.duration, gift_certificate.create_date,
			gift_certificate.last_update_date from gift_certificate
			""";

	private static final String INSERT_CERTIFICATE = """
			insert into gift_certificate values(null, ?, ?, ?, ?, ?, ?)
			""";

	private static final String UPDATE_SERTIFICATE = """
			update certificates.gift_certificate set name=?, description=?,
			price=?, duration=?, last_update_date=? where id=?
			""";

	private static final String DELETE_SERTIFICATE = """
			delete from certificates.gift_certificate where id=?
			""";

	private static final String BIND_TAG = """
			insert into certificates.gift_certificate_has_tag values(?, ?);
			""";

	private static final String FIND_ALL_CERTIFICATES_WITH_TAGS = """
			SELECT t.id, t.tag_name, gc.id, gc.name, gc.description,
			gc.price, gc.duration, gc.create_date,
			gc.last_update_date
			FROM certificates.gift_certificate_has_tag as gt
			join gift_certificate as gc on gc.id = gt.gift_certificate_id
			join tag as t on t.id = gt.tag_id
			""";

	private static final String FIND_ALL_CERTIFICATES_WITH_TAGS_BY_TAGNAME = """
			SELECT t.id, t.tag_name, gc.id, gc.name, gc.description,
			gc.price, gc.duration, gc.create_date,
			gc.last_update_date
			FROM certificates.gift_certificate_has_tag as gt
			join gift_certificate as gc on gc.id = gt.gift_certificate_id
			join tag as t on t.id = gt.tag_id
			where t.tag_name = ?
			""";

	private static final String FIND_ALL_CERTIFICATES_WITH_TAGS_BY_CERTIFICATE = """
			SELECT t.id, t.tag_name, gc.id, gc.name, gc.description,
			gc.price, gc.duration, gc.create_date,
			gc.last_update_date
			FROM certificates.gift_certificate_has_tag as gt
			join gift_certificate as gc on gc.id = gt.gift_certificate_id
			join tag as t on t.id = gt.tag_id
			where gc.name like ?
			""";

	private static final String CERTIFICATES_WITH_TAGS_BY_CERTIFICATE_AND_TAGNAME = """
			SELECT t.id, t.tag_name, gc.id, gc.name, gc.description,
			gc.price, gc.duration, gc.create_date,
			gc.last_update_date
			FROM certificates.gift_certificate_has_tag as gt
			join gift_certificate as gc on gc.id = gt.gift_certificate_id
			join tag as t on t.id = gt.tag_id
			where t.tag_name = ? and gc.name like ?
			""";

	private static final String FIND_CERTIFICATE_WITH_TAG = """
			SELECT t.id, t.tag_name, gc.id, gc.name, gc.description,
			gc.price, gc.duration, gc.create_date,
			gc.last_update_date
			FROM certificates.gift_certificate_has_tag as gt
			join gift_certificate as gc on gc.id = gt.gift_certificate_id
			join tag as t on t.id = gt.tag_id
			where t.id = ? and gc.id = ?
			""";

	private static final String UNBIND_CERTIFICATE = """
			delete from certificates.gift_certificate_has_tag as gt
			where gt.gift_certificate_id = ?
			""";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTemplateCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Creates gift certificate
	 *
	 * @param name        the name
	 * @param description the description
	 * @param price       the price
	 * @param duration    the duration
	 * @return the int
	 */
	@Override
	public int create(String name, String description, int price, String duration) {
		KeyHolder key = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(java.sql.Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(INSERT_CERTIFICATE,
						java.sql.Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setInt(3, price);
				ps.setString(4, duration + " days");
				ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
				ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
				return ps;
			}
		}, key);
		return key.getKey().intValue();
	}

	/**
	 * Updates gift certificate
	 *
	 * @param certificate the certificate
	 * @param id          the id
	 * @return the int
	 */
	@Override
	public int update(GiftCertificate certificate, int id) {
		return jdbcTemplate.update(UPDATE_SERTIFICATE, certificate.getName(), certificate.getDescription(),
				certificate.getPrice(), certificate.getDuration(), LocalDateTime.now(), id);
	}

	/**
	 * Delete gift certificate
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int delete(int id) {
		return jdbcTemplate.update(DELETE_SERTIFICATE, id);
	}

	/**
	 * Find certificate by id
	 *
	 * @param id the id
	 * @return the gift certificate
	 */
	@Override
	public GiftCertificate findById(int id) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_ID, new GiftCertificateMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * Find all certificates.
	 *
	 * @return the list
	 */
	@Override
	public List<GiftCertificate> findAll() {
		return jdbcTemplate.query(FIND_ALL, new GiftCertificateMapper());
	}

	/**
	 * Bind tag to certificate.
	 *
	 * @param certificateId the certificate id
	 * @param tagId         the tag id
	 * @return the int
	 */
	@Override
	public int bindTag(int certificateId, int tagId) {
		return jdbcTemplate.update(BIND_TAG, certificateId, tagId);
	}

	/**
	 * Find all certificates with tags.
	 *
	 * @return the list
	 */
	@Override
	public List<CertificateWithTag> findAllCertificatesWithTags() {
		return jdbcTemplate.query(FIND_ALL_CERTIFICATES_WITH_TAGS, new CertificateWithTagMapper());
	}

	/**
	 * Find certificates with tags.
	 *
	 * @param tagId         the tag id
	 * @param certificateId the certificate id
	 * @return the certificate with tag
	 */
	@Override
	public CertificateWithTag findCertificateWithTag(int tagId, int certificateId) {
		return jdbcTemplate.queryForObject(FIND_CERTIFICATE_WITH_TAG, new CertificateWithTagMapper(), tagId,
				certificateId);
	}

	/**
	 * Unbind certificate.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int unbindCertificate(int id) {
		return jdbcTemplate.update(UNBIND_CERTIFICATE, id);
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByTagname(String tagName) {
		return jdbcTemplate.query(FIND_ALL_CERTIFICATES_WITH_TAGS_BY_TAGNAME, new CertificateWithTagMapper(), tagName);
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByCertificate(String partName) {
		return jdbcTemplate.query(FIND_ALL_CERTIFICATES_WITH_TAGS_BY_CERTIFICATE, new CertificateWithTagMapper(),
				"%" + partName + "%");
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByCertificateAndTagname(String tagName,
			String certificateName) {
		return jdbcTemplate.query(CERTIFICATES_WITH_TAGS_BY_CERTIFICATE_AND_TAGNAME, new CertificateWithTagMapper(),
				tagName, "%" + certificateName + "%");
	}
}
