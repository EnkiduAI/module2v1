package com.epam.esm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// TODO: Auto-generated Javadoc
/**
 * Class WebConfig.
 */
public class WebConfig implements WebApplicationInitializer {

	/**
	 * On startup. Create dispatcher servlet
	 * 
	 * @param servletContext the servlet context
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(AppConfig.class);

		servletContext.setInitParameter("spring.profiles.active", "prod");

		DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);

		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);

		registration.setLoadOnStartup(1);

		registration.addMapping("/api/*");

	}

}
