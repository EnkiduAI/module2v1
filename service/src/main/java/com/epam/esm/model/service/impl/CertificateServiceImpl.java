package com.epam.esm.model.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dao.impl.JdbcTemplateCertificateDaoImpl;
import com.epam.esm.model.dao.impl.JdbcTemplateTagDaoImpl;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.CertificateService;

@Service
public class CertificateServiceImpl implements CertificateService {

	/** Certificate dao. */
	private JdbcTemplateCertificateDaoImpl certificateDao;

	private JdbcTemplateTagDaoImpl tagDao;

	@Autowired
	public CertificateServiceImpl(JdbcTemplateCertificateDaoImpl certificateDao, JdbcTemplateTagDaoImpl tagDao) {
		this.certificateDao = certificateDao;
		this.tagDao = tagDao;
	}

	/**
	 * Creates the certificate.
	 *
	 * @param name        the name
	 * @param description the description
	 * @param price       the price
	 * @param duration    the duration
	 * @return the gift certificate
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public GiftCertificate createCertificate(String name, String description, int price, String duration)
			throws ServiceException {
		int id = certificateDao.create(name, description, price, duration);
		return certificateDao.findById(id);
	}

	/**
	 * Creates the certificate.
	 *
	 * @param tagName         the tag name
	 * @param certificateName the certificate name
	 * @param description     the description
	 * @param price           the price
	 * @param duration        the duration
	 * @return the certificate with tag
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public CertificateWithTag createCertificate(String tagName, String certificateName, String description, int price,
			String duration) throws ServiceException {
		int tagId = tagDao.create(tagName);
		int certificateId = certificateDao.create(certificateName, description, price, duration);
		if (certificateDao.bindTag(certificateId, tagId) > 0) {
			return certificateDao.findCertificateWithTag(tagId, certificateId);
		} else {
			throw new ServiceException("Error caused by bindind tag with certificate");
		}
	}

	/**
	 * Find certificate by id.
	 *
	 * @param id the id
	 * @return the gift certificate
	 * @throws ServiceException the service exception
	 */
	@Override
	public GiftCertificate findCertificateById(int id) throws ServiceException {
		return certificateDao.findById(id);
	}

	/**
	 * Find all certificates.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	@Override
	public List<GiftCertificate> findAllCertificates() throws ServiceException {
		return certificateDao.findAll();
	}

	/**
	 * Update certificate.
	 *
	 * @param certificate the certificate
	 * @param id          the id
	 * @return the gift certificate
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public GiftCertificate update(GiftCertificate certificate, int id) throws ServiceException {
		int certificateId = certificate.getId();
		certificateDao.update(certificate, id);
		return certificateDao.findById(certificateId);
	}

	/**
	 * Update certificate with tag.
	 *
	 * @param tagName       the tag name
	 * @param certificate   the certificate
	 * @param certificateId the certificate id
	 * @return the certificate with tag
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public CertificateWithTag update(String tagName, GiftCertificate certificate, int certificateId)
			throws ServiceException {
		certificateDao.update(certificate, certificateId);
		int certId = certificate.getId();
		int tagId = tagDao.create(tagName);
		if (certificateDao.bindTag(certId, tagId) > 0) {
			return certificateDao.findCertificateWithTag(tagId, certId);
		} else {
			throw new ServiceException("Error caused by bindind tag with certificate");
		}
	}

	/**
	 * Gets the certificates with tags.
	 *
	 * @param tagName         the tag name
	 * @param certificateName the certificate name
	 * @param sortType        the sort type
	 * @return the certificates with tags
	 * @throws ServiceException the service exception
	 */
	@Override
	public List<CertificateWithTag> getCertificatesWithTags() throws ServiceException {		
		return certificateDao.findAllCertificatesWithTags();
	}

	// list =
	// list.stream().sorted(Comparator.comparing(CertificateWithTag::getCertificateName).reversed())
	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByTagname(String tagName) throws ServiceException {
		return certificateDao.findCertificateWithTagByTagname(tagName);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByTagnameSorted(String tagName, String sortType)
			throws ServiceException {
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByTagname(tagName);		
		return sort(list, sortType);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificate(String certificateName)
			throws ServiceException {
		return certificateDao.findCertificateWithTagByCertificate(certificateName);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateSorted(String certificateName, String sortType)
			throws ServiceException {
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByCertificate(certificateName);		
		return sort(list, sortType);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagname(String tagName,
			String certificateName) throws ServiceException {
		return certificateDao.findCertificateWithTagByCertificateAndTagname(tagName, certificateName);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagnameSorted(String tagName,
			String certificateName, String sortType) throws ServiceException {
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByCertificateAndTagname(tagName,
				certificateName);		
		return sort(list, sortType);
	}

	/**
	 * Delete certificate.
	 *
	 * @param certId the cert id
	 * @return the gift certificate
	 * @throws ServiceException the service exception
	 */
	@Transactional
	@Override
	public GiftCertificate deleteCertificate(int certId) throws ServiceException {
		certificateDao.unbindCertificate(certId);
		GiftCertificate deleted = certificateDao.findById(certId);
		if (certificateDao.delete(certId) > 0) {
			return deleted;
		} else {
			throw new ServiceException("Method deleteCertificate at ProjectServiceImpl was interrupted with error");
		}
	}
	
	public List<CertificateWithTag> sort(List<CertificateWithTag> list, String sortType) {
		switch (sortType) {
		case ("ASC"):
			list = list.stream().sorted(Comparator.comparing(CertificateWithTag::getCertificateName)).toList();
			break;
		case ("DESC"):
			list = list.stream().sorted(Comparator.comparing(CertificateWithTag::getCertificateName).reversed())
					.toList();
			break;
		default:
			break;
		}
		return list;
	}
}