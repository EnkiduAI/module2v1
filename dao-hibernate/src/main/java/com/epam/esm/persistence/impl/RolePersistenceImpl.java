package com.epam.esm.persistence.impl;

import static com.epam.esm.persistence.query.RoleQuery.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.epam.esm.model.entity.Role;
import com.epam.esm.persistence.EntityManagerHelper;
import com.epam.esm.persistence.RolePersistence;

@Repository
public class RolePersistenceImpl implements RolePersistence {

	private EntityManagerFactory factory = EntityManagerHelper.getFactory();

	@Override
	public Role findByName(String name) {
		EntityManager em = factory.createEntityManager();
		Role role = new Role();
		try {
			TypedQuery<Role> query = em.createQuery(FIND_ROLE_BY_NAME, Role.class).setParameter("roleName", name);
			role = query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		} finally {
			em.close();
		}
		return role;
	}

}
