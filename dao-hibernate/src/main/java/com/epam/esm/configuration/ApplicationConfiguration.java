package com.epam.esm.configuration;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.epam.esm.model.entity.AuditListner;
import com.epam.esm.persistence.EntityManagerHelper;

@Configuration
@ComponentScan("com.epam.esm")
@Profile("prod")
public class ApplicationConfiguration {

	@Bean
	public AuditListner getAuditListner() {
		return new AuditListner();
	}
	
	@Bean
	public EntityManager getEntityManager() {
		return EntityManagerHelper.getFactory().createEntityManager();
	}
}
