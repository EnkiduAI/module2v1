package dao.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.SpringConfig;
import com.epam.esm.model.dao.impl.JdbcTemplateCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class)
class CertificateDaoTest {
	
	public CertificateDaoTest() {
	}
	

	@Autowired
	JdbcTemplateCertificateDaoImpl certificate;

	@Test
	void findByIdTest() {
		GiftCertificate actual = certificate.findById(2);
		assertNotNull(actual);
	}

	@Test
	void create() {
		assertTrue(certificate.create("sample", "test", 111, "23") > 0);
	}


	@Test
	void findAll() {
		assertNotNull(certificate.findAll());
	}
}
