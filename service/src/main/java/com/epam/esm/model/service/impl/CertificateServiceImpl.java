package com.epam.esm.model.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.CertificateService;
import com.epam.esm.parser.GiftCertificateParser;
import com.epam.esm.persistence.impl.GiftCetificatePersistanceImpl;
import com.epam.esm.persistence.impl.TagPersistenceImpl;
import com.epam.esm.validator.Validator;

@Service
public class CertificateServiceImpl implements CertificateService {

	/** Certificate dao. */
	private GiftCetificatePersistanceImpl certificateDao;

	private TagPersistenceImpl tagDao;

	private Validator validator = new Validator();

	public CertificateServiceImpl() {

	}

	@Autowired
	public CertificateServiceImpl(GiftCetificatePersistanceImpl certificateDao, TagPersistenceImpl tagDao) {
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
	public GiftCertificate createCertificate(Map<String, Object> certificate) throws ServiceException {
		GiftCertificateParser.parseToGiftCertificate(certificate);
		String name = certificate.get("name").toString();
		String description = certificate.get("description").toString();
		int price = Integer.parseInt(certificate.get("price").toString());
		String duration = certificate.get("duration").toString();
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
		int tagId;
		Tag expectedTag = tagDao.findByName(tagName);
		if (expectedTag != null) {
			tagId = expectedTag.getId();
		} else {
			tagId = tagDao.create(tagName);
		}
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
	 * @throws ServiceException  the service exception
	 * @throws NotFoundException
	 */
	@Override
	public GiftCertificate findCertificateById(int id) throws ServiceException, NotFoundException {
		if (id <= 0) {
			throw new ServiceException("id cannot be 0 or less");
		}
		if (certificateDao.findById(id) == null) {
			throw new NotFoundException("Entry with id = " + id + " not exsist");
		}
		return certificateDao.findById(id);
	}

	/**
	 * Find all certificates.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 * @throws NotFoundException 
	 */
	@Override
	public List<GiftCertificate> findAllCertificates(int page, int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<GiftCertificate> certificates = certificateDao.findAll(page, limit);
		if (certificates == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return certificateDao.findAll(page, limit);
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
	public GiftCertificate update(Map<String, Object> request, int id) throws ServiceException {
		GiftCertificateParser.parseToGiftCertificate(request);
		GiftCertificate certificateToUpdate = certificateDao.findById(id);
		if (request.containsKey("name")) {
			certificateToUpdate.setName(request.get("name").toString());
		}
		if (request.containsKey("description")) {
			certificateToUpdate.setDescription(request.get("description").toString());
		}
		if (request.containsKey("price")) {
			certificateToUpdate.setPrice(Integer.parseInt(request.get("price").toString()));
		}
		if (request.containsKey("duration")) {
			certificateToUpdate.setDuration(request.get("duration").toString());
		}
		certificateDao.update(certificateToUpdate, id);
		return certificateDao.findById(id);
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
	public CertificateWithTag updateWithTag(Map<String, Object> fields, int certificateId)
			throws ServiceException {
		GiftCertificateParser.parseToGiftCertificate(fields);
		int tagId;
		GiftCertificate certificateToUpdate = certificateDao.findById(certificateId);
		if (fields.get("name") != null) {
			certificateToUpdate.setName(fields.get("name").toString());
		}
		if (fields.get("description") != null) {
			certificateToUpdate.setDescription(fields.get("description").toString());
		}
		if (fields.get("price") != null) {
			certificateToUpdate.setPrice((int) fields.get("price"));
		}
		if (fields.get("duration") != null) {
			certificateToUpdate.setDuration(fields.get("duration").toString());
		}	
		String tagName = fields.get("tagName").toString();
		certificateDao.update(certificateToUpdate, certificateId);
		int certId = certificateToUpdate.getId();
		Tag expectedTag = tagDao.findByName(tagName);
		if (expectedTag != null) {
			tagId = expectedTag.getId();
		} else {
			tagId = tagDao.create(tagName);
		}
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
	public List<CertificateWithTag> getCertificatesWithTags(int page, int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> cwt = certificateDao.findAllCertificatesWithTags(page, limit);
		if(cwt == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return cwt;
	}

	// list =
	// list.stream().sorted(Comparator.comparing(CertificateWithTag::getCertificateName).reversed())
	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByTagname(String tagName, int page, int limit)
			throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> cwt = certificateDao.findCertificateWithTagByTagname(tagName, page, limit);
		if(cwt == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return cwt;
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByTagnameSorted(String tagName, String sortType, int page,
			int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByTagname(tagName, page, limit);
		if(list == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return sort(list, sortType);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificate(String certificateName, int page, int limit)
			throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> cwt = certificateDao.findCertificateWithTagByCertificate(certificateName, page, limit);
		if(cwt == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return cwt;
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateSorted(String certificateName, String sortType,
			int page, int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByCertificate(certificateName, page,
				limit);
		if(list == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return sort(list, sortType);
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagname(String tagName,
			String certificateName, int page, int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> cwt = certificateDao.findCertificateWithTagByCertificateAndTagname(tagName, certificateName, page, limit);
		if(cwt == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
		return cwt;
	}

	@Override
	public List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagnameSorted(String tagName,
			String certificateName, String sortType, int page, int limit) throws ServiceException, NotFoundException {
		if (!validator.isPageble(page, limit)) {
			throw new ServiceException("Page & Size are incorrect");
		}
		List<CertificateWithTag> list = certificateDao.findCertificateWithTagByCertificateAndTagname(tagName,
				certificateName, page, limit);
		if(list == null) {
			throw new NotFoundException("Cannot find certificates on given criteria");
		}
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
			throw new ServiceException("Method deleteCertificate at CertificateServiceImpl was interrupted with error");
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
