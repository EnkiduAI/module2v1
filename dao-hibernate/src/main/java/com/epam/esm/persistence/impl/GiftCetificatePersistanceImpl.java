package com.epam.esm.persistence.impl;

import static com.epam.esm.persistence.query.GiftCertificateQuery.FIND_CERTIFICATES_WITH_TAGS;
import static com.epam.esm.persistence.query.GiftCertificateQuery.FIND_CERTIFICATE_WITH_TAG;
import static com.epam.esm.persistence.query.GiftCertificateQuery.FIND_CERTIFICATE_WITH_TAG_BY_PARTNAME;
import static com.epam.esm.persistence.query.GiftCertificateQuery.FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME;
import static com.epam.esm.persistence.query.GiftCertificateQuery.FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME_PARTNAME;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.EntityManagerHelper;
import com.epam.esm.persistence.GiftCertificatePersistance;

@Repository
public class GiftCetificatePersistanceImpl implements GiftCertificatePersistance {

	EntityManagerFactory factory = EntityManagerHelper.getFactory();

	@Override
	public int create(String name, String description, int price, String duration) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		GiftCertificate certificate = new GiftCertificate();
		if (name != null) {
			certificate.setName(name);
		}
		if (description != null) {
			certificate.setDescription(description);
		}
		if (price != 0) {
			certificate.setPrice(price);
		}
		if (duration != null) {
			certificate.setDuration(duration);
		}
		certificate.setCreateDate(LocalDateTime.now());
		certificate.setLastUpdateDate(LocalDateTime.now());
		em.persist(certificate);
		int id = certificate.getId();
		em.getTransaction().commit();
		em.close();
		return id;
	}

	@Override
	public int update(GiftCertificate certificate, int id) {
		EntityManager em = factory.createEntityManager();
		int result = 0;
		em.getTransaction().begin();
		try {
			em.find(GiftCertificate.class, id);
			em.merge(certificate);
			em.getTransaction().commit();
			result = 1;
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			throw new HibernateException("GiftCertificate update failed");
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public int delete(int id) {
		EntityManager em = factory.createEntityManager();
		int result = 0;
		em.getTransaction().begin();
		try {
			GiftCertificate certificate = em.find(GiftCertificate.class, id);
			em.remove(certificate);
			em.getTransaction().commit();
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			result = 1;
			throw new HibernateException("Gift certificate deletion failed");
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public int bindTag(int certificateId, int tagId) {
		EntityManager em = factory.createEntityManager();
		int result = 0;
		try {
			em.getTransaction().begin();
			Tag tag = em.find(Tag.class, tagId);
			GiftCertificate certificate = em.find(GiftCertificate.class, certificateId);
			certificate.addTag(tag);
			tag.addCertificate(certificate);
			em.getTransaction().commit();
			result = 1;
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			throw new HibernateException("Cannot bind tags");
		}
		em.close();
		return result;
	}

	@Override
	public int unbindCertificate(int id) {
		EntityManager em = factory.createEntityManager();
		int result = 0;
		em.getTransaction().begin();
		try {
			GiftCertificate certificate = em.find(GiftCertificate.class, id);
			for (Tag tag : certificate.getTags()) {
				certificate.removeTag(tag);
			}
			em.getTransaction().commit();
			result = 1;
		} catch (HibernateException e) {
			em.getTransaction().rollback();
			throw new HibernateException("Cannot unbind certificate");
		}
		return result;
	}

	@Override
	public GiftCertificate findById(int id) {
		EntityManager em = factory.createEntityManager();
		GiftCertificate certificate = em.find(GiftCertificate.class, id);
		em.close();
		return certificate;
	}

	@Override
	public List<GiftCertificate> findAll(int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<GiftCertificate> query = em.createQuery("from GiftCertificate", GiftCertificate.class);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		List<GiftCertificate> certificates = query.getResultList();
		em.close();
		return certificates;
	}

	@Override
	public List<CertificateWithTag> findAllCertificatesWithTags(int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<CertificateWithTag> query = em.createQuery(FIND_CERTIFICATES_WITH_TAGS, CertificateWithTag.class);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		List<CertificateWithTag> cwt = query.getResultList();
		em.close();
		return cwt;
	}

	@Override
	public CertificateWithTag findCertificateWithTag(int tagId, int certificateId) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<CertificateWithTag> query = em.createQuery(FIND_CERTIFICATE_WITH_TAG, CertificateWithTag.class);
		query.setParameter("tag_id", tagId);
		query.setParameter("certificate_id", certificateId);
		CertificateWithTag cwt = query.getSingleResult();
		em.close();
		return cwt;
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByTagname(String tagName, int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<CertificateWithTag> query = em.createQuery(FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME,
				CertificateWithTag.class);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		query.setParameter("tag_name", tagName);
		List<CertificateWithTag> cwt = query.getResultList();
		em.close();
		return cwt;
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByCertificate(String partName, int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<CertificateWithTag> query = em.createQuery(FIND_CERTIFICATE_WITH_TAG_BY_PARTNAME,
				CertificateWithTag.class);
		query.setParameter("name", partName);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		List<CertificateWithTag> cwt = query.getResultList();
		em.close();
		return cwt;
	}

	@Override
	public List<CertificateWithTag> findCertificateWithTagByCertificateAndTagname(String tagName,
			String certificateName, int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<CertificateWithTag> query = em.createQuery(FIND_CERTIFICATE_WITH_TAG_BY_TAGNAME_PARTNAME,
				CertificateWithTag.class);
		query.setParameter("name", "%"+certificateName+"%");
		query.setParameter("tag_name", tagName);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		List<CertificateWithTag> cwt = query.getResultList();
		em.close();
		return cwt;
	}

}
