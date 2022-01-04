package dao.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.TestProfile;
import com.epam.esm.model.dao.impl.JdbcTemplateCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestProfile.class)
@ActiveProfiles(profiles = "dev")
class CertificateDaoTest {

	@Autowired
	public JdbcTemplateCertificateDaoImpl certificate;

	@Test
	protected void findByIdTest() {
		GiftCertificate actual = certificate.findById(2);
		System.out.println(actual);
		assertNotNull(actual);
	}

	@Test
	protected void create() {
		assertTrue(certificate.create("sample", "test", 111, "23") > 0);
	}

	@Test
	protected void findAll() {
		assertNotNull(certificate.findAll());
	}
}
