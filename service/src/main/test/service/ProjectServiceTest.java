package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.impl.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

	@Mock
	private ProjectServiceImpl service;
	private List<GiftCertificate> certificates;
	private List<Tag> tags;
	private List<CertificateWithTag> certsWithTags;
	private GiftCertificate certificate;
	private Tag tag;
	private CertificateWithTag cwt;

	@BeforeEach
	protected void setUp() {
		certificate = new GiftCertificate(154, "testCert", "testCert", 111, "23", LocalDateTime.now(),
				LocalDateTime.now());
		tag = new Tag(66, "TEST");
		cwt = new CertificateWithTag(44, "TESTTAG", 234, "CErtName", "SimpleDesc", 111, "23", LocalDateTime.now(),
				LocalDateTime.now());
		certificates = new ArrayList<>();
		tags = new ArrayList<>();
		certsWithTags = new ArrayList<>();
	}

	@Test
	protected void findCertificates() throws ServiceException {
		when(service.findAllCertificates()).thenReturn(certificates);
		List<GiftCertificate> expected = service.findAllCertificates();
		assertEquals(expected, certificates);
	}
	
	@Test
	protected void findTags() throws ServiceException{
		when(service.findAllTags()).thenReturn(tags);
		List<Tag> expected = service.findAllTags();
		assertEquals(expected, tags);
	}
	
	@Test
	protected void findCertficatesWithTags() throws ServiceException {
		when(service.getCertificatesWithTags(anyString(), anyString(), anyString())).thenReturn(certsWithTags);
		List<CertificateWithTag> expected = service.getCertificatesWithTags("TkjGG","c", "ASC");
		assertEquals(expected, certsWithTags);
	}
	
	@Test
	protected void createCertificate() throws ServiceException{
		when(service.createCertificate("testCert", "testCert", 111, "23")).thenReturn(certificate);
		GiftCertificate expected = service.createCertificate("testCert", "testCert", 111, "23");
		assertEquals(expected, certificate);
	}
	
	@Test
	protected void createTag() throws ServiceException{
		when(service.createTag("TEST")).thenReturn(tag);
		Tag expected = service.createTag("TEST");
		assertEquals(expected, tag);
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
	
	@Test
	protected void findTagById() throws ServiceException{
		when(service.findTagById(66)).thenReturn(tag);
		Tag expected = service.findTagById(66);
		assertEquals(expected, tag);
	}
}
