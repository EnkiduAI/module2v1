package com.epam.esm.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
	private static EntityManagerFactory factory;
	
	private EntityManagerHelper() {
		
	}

	static {
		try {
			factory = Persistence.createEntityManagerFactory("entity");
		} catch (ExceptionInInitializerError e) {
			throw new ExceptionInInitializerError("Failed to initialize EntityManagerFactory");
		}
	}
	
	public static EntityManagerFactory getFactory() {
		return factory;
	}
}
