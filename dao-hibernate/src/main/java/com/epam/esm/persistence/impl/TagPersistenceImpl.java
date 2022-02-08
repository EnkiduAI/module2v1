package com.epam.esm.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.EntityManagerHelper;
import com.epam.esm.persistence.TagPersistence;

@Repository
public class TagPersistenceImpl implements TagPersistence {

	private EntityManagerFactory factory = EntityManagerHelper.getFactory();

	@Transactional
	@Override
	public int create(String name) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Tag tag = new Tag();
		tag.setName(name);
		em.persist(tag);
		int id = tag.getId();
		em.getTransaction().commit();
		em.close();
		return id;
	}

	@Transactional
	@Override
	public int delete(int id) {
		EntityManager em = factory.createEntityManager();
		Tag tag = new Tag();
		try {
			em.getTransaction().begin();
			tag = em.find(Tag.class, id);
			em.remove(tag);
			em.getTransaction().commit();
		} catch (HibernateException e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return tag.getId();
	}

	@Override
	public int unbindTag(int id) {
		EntityManager em = factory.createEntityManager();
		int result = 0;
		em.getTransaction().begin();
		try {
			Tag tag = em.find(Tag.class, id);
			for (GiftCertificate certificate : tag.getGiftCertificates()) {
				tag.removeCertificate(certificate);
			}
			em.getTransaction().commit();
			result = 1;
		}catch (HibernateException e) {
			em.getTransaction().rollback();
			throw new HibernateException("Cannot unbind tag");
		}finally {
			em.close();
		}
		return result;
	}

	@Transactional
	@Override
	public Tag findByName(String name) {
		EntityManager em = factory.createEntityManager();
		Tag tag = new Tag();
		try {
			TypedQuery<Tag> tq = em.createQuery("select t from Tag t where t.name = :name", Tag.class);
			tq.setParameter("name", name);
			tag = tq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		return tag;
	}

	@Transactional
	@Override
	public Tag findById(int id) {
		EntityManager em = factory.createEntityManager();
		Tag tag = em.find(Tag.class, id);
		em.close();
		return tag;
	}

	@Transactional
	@Override
	public List<Tag> findAll(int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Tag> list = em.createQuery("from Tag", Tag.class);
		list.setFirstResult((page - 1) * limit);
		list.setMaxResults(limit);
		List<Tag> tags = list.getResultList();
		em.close();
		return tags;
	}

}
