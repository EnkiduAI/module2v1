package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class GiftCertificatesApplication {
	public static void main(String[] args) {
		SpringApplication.run(GiftCertificatesApplication.class, args);
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasename("message/messages");
		ms.setUseCodeAsDefaultMessage(true);
		return ms;
	}
}
