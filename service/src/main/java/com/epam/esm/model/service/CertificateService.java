package com.epam.esm.model.service;

import java.util.List;
import java.util.Map;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;

public interface CertificateService {

	GiftCertificate createCertificate(Map<String, Object> certificate)
			throws ServiceException;// check

	CertificateWithTag createCertificate(String tagName, String certificateName, String description, int price,
			String duration) throws ServiceException;// check

	GiftCertificate findCertificateById(int id) throws ServiceException, NotFoundException;// check

	List<GiftCertificate> findAllCertificates(int page, int limit) throws ServiceException, NotFoundException;// check

	GiftCertificate update(Map<String, Object> certificate, int id) throws ServiceException;// check

	CertificateWithTag updateWithTag(Map<String, Object> fields, int certificateId)
			throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTags(int page, int limit) throws ServiceException, NotFoundException;// check

	List<CertificateWithTag> getCertificatesWithTagsByTagname(String tagName, int page, int limit)
			throws ServiceException, NotFoundException;

	List<CertificateWithTag> getCertificatesWithTagsByTagnameSorted(String tagName, String sortType, int page,
			int limit) throws ServiceException, NotFoundException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificate(String certificateName, int page, int limit)
			throws ServiceException, NotFoundException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateSorted(String certificateName, String sortType,
			int page, int limit) throws ServiceException, NotFoundException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagname(String tagName, String certificateName,
			int page, int limit) throws ServiceException, NotFoundException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagnameSorted(String tagName,
			String certificateName, String sortType, int page, int limit) throws ServiceException, NotFoundException;

	GiftCertificate deleteCertificate(int certId) throws ServiceException;// check
}
