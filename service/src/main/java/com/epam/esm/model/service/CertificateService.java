package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;

public interface CertificateService {

	GiftCertificate createCertificate(String name, String description, int price, String duration)
			throws ServiceException;// check

	CertificateWithTag createCertificate(String tagName, String certificateName, String description, int price,
			String duration) throws ServiceException;// check
	
	GiftCertificate findCertificateById(int id) throws ServiceException;// check
	
	List<GiftCertificate> findAllCertificates(int page, int limit) throws ServiceException;// check
	
	GiftCertificate update(GiftCertificate certificate, int id) throws ServiceException;// check

	CertificateWithTag update(String tagName, GiftCertificate certificate, int certificateId) throws ServiceException;
	
	List<CertificateWithTag> getCertificatesWithTags(int page, int limit)
			throws ServiceException;// check

	List<CertificateWithTag> getCertificatesWithTagsByTagname(String tagName, int page, int limit) throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTagsByTagnameSorted(String tagName, String sortType, int page, int limit)
			throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificate(String certificateName, int page, int limit) throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateSorted(String certificateName, String sortType, int page, int limit)
			throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagname(String tagName, String certificateName, int page, int limit)
			throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTagsByCertificateAndTagnameSorted(String tagName,
			String certificateName, String sortType, int page, int limit) throws ServiceException;

	GiftCertificate deleteCertificate(int certId) throws ServiceException;// check
}
