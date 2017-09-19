/**
 *

 * Copyright (C) ISBAN
 * All Rights Reserved.
 *    www.isban.es
 *
 */

package com.anc.test.ryan.config;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import com.isb.phoenix.logger.ArqLocLoggerFactory;
import com.isb.phoenix.logger.Logger;
import com.isban.gli.util.commons.profile.ProfileApp;



public class AppCtxtInit implements ApplicationContextInitializer<ConfigurableApplicationContext>  {

	private Logger logger = ArqLocLoggerFactory.getLogger(AppCtxtInit.class);

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.ConfigurableApplicationContext)
	 */
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();

		environment.setActiveProfiles(ProfileApp.DEV);
//		LOG.info(new StringBuffer().append("Active profile: ").append(ProfileApp.DEV).toString());
		
		MutablePropertySources propertySources = environment.getPropertySources();
		String location = "file:///gli/ftp/properties/ws.properties";
		Resource resource = applicationContext.getResource(location);
		
		try{
			if(resource.exists()){
				ResourcePropertySource propertySource = new ResourcePropertySource(location);
				propertySources.addLast(propertySource);
				logger.info("Fichero de configuracion externo para WS: "+location+" encontrado");
			}else{
				throw new Exception("Fichero de configuracion externo para WS: "+location+" no encontrado");
			}
		}catch(Exception e){
			logger.error("Error al cargar el fichero de configuracion externo para WS: "+e,e);
		}

	}
}