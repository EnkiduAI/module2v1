package com.epam.esm.model.dao;

import java.util.List;

import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;

public interface CertificateDao {
	int create(String name, String description, int price, String duration);
	int update(GiftCertificate certificate, int id);
	int delete(int id);
	int bindTag(int certificateId, int tagId);
	int unbindCertificate(int id);
	GiftCertificate findById(int id);
	List<GiftCertificate> findAll(int page, int limit);	
	List<CertificateWithTag>findAllCertificatesWithTags(int page, int limit);
	CertificateWithTag findCertificateWithTag(int tagId, int certificateId);
	List<CertificateWithTag> findCertificateWithTagByTagname(String tagName, int page, int limit);
	List<CertificateWithTag> findCertificateWithTagByCertificate(String partName, int page, int limit);
	List<CertificateWithTag> findCertificateWithTagByCertificateAndTagname(String tagName, String certificateName, int page, int limit);
}
