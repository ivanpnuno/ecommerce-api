package com.ecommerce.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.ecomerce.controller.PriceController;

@Component
@ApplicationPath("/api")
@Configuration
@EnableCaching
public class AppConfig extends ResourceConfig {

	public AppConfig() {
		packages("com.ecommerce");
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		this.register(PriceController.class);
	}
}
