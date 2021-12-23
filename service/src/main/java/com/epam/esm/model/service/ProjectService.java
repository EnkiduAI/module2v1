package com.epam.esm.model.service;

import java.util.List;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

public interface ProjectService {
	GiftCertificate createCertificate(String name, String description, int price, String duration)
			throws ServiceException;// check

	CertificateWithTag createCertificate(String tagName, String certificateName, String description, int price,
			String duration) throws ServiceException;//check

	Tag createTag(String name) throws ServiceException;// check

	GiftCertificate findCertificateById(int id) throws ServiceException;// check

	Tag findTagById(int id) throws ServiceException;// check

	List<GiftCertificate> findAllCertificates() throws ServiceException;// check

	List<Tag> findAllTags() throws ServiceException;// check

	GiftCertificate update(GiftCertificate certificate, int id) throws ServiceException;//check

	CertificateWithTag update(String tagName, GiftCertificate certificate, int certificateId) throws ServiceException;

	List<CertificateWithTag> getCertificatesWithTags(String tagName, String certificateName, String sortType)
			throws ServiceException;//check

	GiftCertificate deleteCertificate(int certId) throws ServiceException;// check

	Tag deleteTag(int tagId) throws ServiceException;// check
}
