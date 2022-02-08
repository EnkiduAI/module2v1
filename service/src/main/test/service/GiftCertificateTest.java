package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.impl.CertificateServiceImpl;
import com.epam.esm.persistence.impl.GiftCetificatePersistanceImpl;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class GiftCertificateTest {
	
	@MockBean
	public GiftCetificatePersistanceImpl persistence;
	
	@InjectMocks public CertificateServiceImpl service;
		
	private List<GiftCertificate> certificates;
	private List<CertificateWithTag> certsWithTags;
	private GiftCertificate certificate;

	@BeforeEach
	protected void setUp() {
		certificate = new GiftCertificate(154, "testCert", "testCert", 111, "23", LocalDateTime.now(),
				LocalDateTime.now());
		certificates = new ArrayList<>();
		certsWithTags = new ArrayList<>();
	}

	@Test
	 void findCertificates() throws ServiceException {
		when(persistence.findAll(1, 5)).thenReturn(certificates);
		List<GiftCertificate> expected = service.findAllCertificates(1, 5);
		assertEquals(expected, certificates);
	}
	
	@Test
	protected void findCertficatesWithTags() throws ServiceException {
		when(persistence.findAllCertificatesWithTags(1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTags(1, 5);
		assertEquals(expected, certsWithTags);
	}

	
	@Test
	protected void findCertificateById()throws ServiceException{
		when(persistence.findById(154)).thenReturn(certificate);
		GiftCertificate expected = service.findCertificateById(154);
		assertEquals(expected, certificate);
	}
	
	@Test
	protected void findCertificateWithTagByTagname()throws ServiceException {
		when(persistence.findCertificateWithTagByTagname("learn", 1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTagsByTagname("learn", 1, 5);
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void findCertificateWithTagByTagnameSorted()throws ServiceException {
		when(persistence.findCertificateWithTagByTagname("learn", 1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTagsByTagnameSorted("learn", "ASC", 1, 5);
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void findCertificateWithTagByCertificate()throws ServiceException {
		when(persistence.findCertificateWithTagByCertificate("c", 1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTagsByCertificate("c", 1, 5);
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void findCertificateWithTagByCertificateSorted()throws ServiceException {
		when(persistence.findCertificateWithTagByCertificate("c", 1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTagsByCertificateSorted("c","ASC", 1, 5);
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void findCertificateWithTagByCertificateAndTagname()throws ServiceException {
		when(persistence.findCertificateWithTagByCertificateAndTagname("courses","c", 1, 5)).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTagsByCertificateAndTagname("courses","c", 1, 5);
		assertEquals(expected, certsWithTags);
	}
}
