package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.impl.CertificateServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

	@Mock
	private CertificateServiceImpl service;
	private List<GiftCertificate> certificates;
	private List<CertificateWithTag> certsWithTags;
	private GiftCertificate certificate;
	private CertificateWithTag cwt;

	@BeforeEach
	protected void setUp() {
		certificate = new GiftCertificate(154, "testCert", "testCert", 111, "23", LocalDateTime.now(),
				LocalDateTime.now());
		cwt = new CertificateWithTag(44, "TESTTAG", 234, "CErtName", "SimpleDesc", 111, "23", LocalDateTime.now(),
				LocalDateTime.now());
		certificates = new ArrayList<>();
		certsWithTags = new ArrayList<>();
	}

	@Test
	protected void findCertificates() throws ServiceException {
		when(service.findAllCertificates(1, 5)).thenReturn(certificates);
		List<GiftCertificate> expected = service.findAllCertificates(1, 5);
		assertEquals(expected, certificates);
	}
	
	@Test
	protected void findCertficatesWithTags() throws ServiceException {
		when(service.getCertificatesWithTags(1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTags(1, 5);
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void createCertificate() throws ServiceException{
		when(service.createCertificate("testCert", "testCert", 111, "23")).thenReturn(certificate);
		GiftCertificate expected = service.createCertificate("testCert", "testCert", 111, "23");
		assertEquals(expected, certificate);
	}
	
	@Test
	protected void createCertificateWithTag() throws ServiceException{
		when(service.createCertificate("TESTTAG", "CErtName", "SimpleDesc", 111, "23")).thenReturn(cwt);
		CertificateWithTag expected = service.createCertificate("TESTTAG", "CErtName", "SimpleDesc", 111, "23");
		assertEquals(expected, cwt);
	}
	
	@Test
	protected void findCertificateById()throws ServiceException{
		when(service.findCertificateById(154)).thenReturn(certificate);
		GiftCertificate expected = service.findCertificateById(154);
		assertEquals(expected, certificate);
	}
}
