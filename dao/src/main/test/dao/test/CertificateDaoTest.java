package dao.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.model.dao.impl.JdbcTemplateCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
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
}
