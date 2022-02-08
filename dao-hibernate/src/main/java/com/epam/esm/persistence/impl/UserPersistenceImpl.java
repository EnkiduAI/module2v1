package com.epam.esm.persistence.impl;

import static com.epam.esm.persistence.query.UserQuery.FIND_MOST_POPULAR_TAG;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.EntityManagerHelper;
import com.epam.esm.persistence.UserPersistence;

@Repository
public class UserPersistenceImpl implements UserPersistence {

	EntityManagerFactory factory = EntityManagerHelper.getFactory();

	@Override
	public List<User> findAll(int page, int limit) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<User> query = em.createQuery("from User", User.class);
		query.setFirstResult((page - 1) * limit);
		query.setMaxResults(limit);
		List<User> userList = query.getResultList();
		em.close();
		return userList;
	}

	@Override
	public User findById(int id) {
		EntityManager em = factory.createEntityManager();
		TypedQuery<User> query = em.createQuery("select u from User u where userId = :id", User.class)
				.setParameter("id", id);
		User user = query.getSingleResult();
		em.close();
		return user;
	}

	@Override
	public Tag findMostPopularTag() {
		EntityManager em = factory.createEntityManager();
		Tag tag = new Tag();
		try {			
			Query query = em.createNativeQuery(FIND_MOST_POPULAR_TAG, Tag.class);
			tag = (Tag)query.getSingleResult();
		} finally {
			em.close();
		}
		return tag;
	}

}
