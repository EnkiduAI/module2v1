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
	List<GiftCertificate> findAll();	
	List<CertificateWithTag>findAllCertificatesWithTags();
	CertificateWithTag findCertificateWithTag(int tagId, int certificateId);
	List<CertificateWithTag> findCertificateWithTagByTagname(String tagName);
	List<CertificateWithTag> findCertificateWithTagByCertificate(String partName);
	List<CertificateWithTag> findCertificateWithTagByCertificateAndTagname(String tagName, String certificateName);
}
