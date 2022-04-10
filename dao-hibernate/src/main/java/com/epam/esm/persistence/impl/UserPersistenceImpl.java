package com.epam.esm.persistence.impl;

import static com.epam.esm.persistence.query.UserQuery.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.Role;
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
		User user = new User();
		try {
			TypedQuery<User> query = em.createQuery("select u from User u where userId = :id", User.class)
					.setParameter("id", id);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		return user;
	}

	@Override
	public Tag findMostPopularTag() {
		EntityManager em = factory.createEntityManager();
		Tag tag = new Tag();
		try {
			Query query = em.createNativeQuery(FIND_MOST_POPULAR_TAG, Tag.class);
			tag = (Tag) query.getSingleResult();
		} finally {
			em.close();
		}
		return tag;
	}

	@Override
	public User findByUsername(String username) {
		User user = new User();
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<User> query = em.createQuery(FIND_USER_BY_LOGIN, User.class).setParameter("login", username);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		return user;
	}

	@Override
	public int createUser(User user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		int id = user.getUserId();
		em.getTransaction().commit();
		em.close();
		return id;
	}

	@Override
	public void updateUserRoles(int id, Role role) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		user.getRoles().add(role);
		em.getTransaction().commit();
		em.close();
	}

}
