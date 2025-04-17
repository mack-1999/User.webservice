package com.hotel.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaServer
@EnableCaching // Enables Spring's caching mechanism
public class UserServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);

		// Print all available beans from User Service App
		/*
		 * String[] beanNames = context.getBeanDefinitionNames();
		 * System.out.println("Beans available in ApplicationContext:"); for (String
		 * beanName : beanNames) { System.out.println(beanName); }
		 */
	}
}