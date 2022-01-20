package com.epam.esm.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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
		Tag tag = em.find(Tag.class, id);
		em.remove(tag);
		em.close();
		return tag.getId();
	}

	@Override
	public int unbindTag(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public Tag findByName(String name) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Tag> tq = em.createQuery("select t from Tag t where t.name = :name", Tag.class);
		Tag tag = tq.getSingleResult();
		em.close();
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
	public List<Tag> findAll() {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Tag> list = em.createQuery("from Tag", Tag.class);
		List<Tag> tags = list.getResultList();
		em.close();
		return tags;
	}

}
