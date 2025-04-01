package com.hotel.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	// Configure LoadBalanced RestTemplate (Optional for RestTemplate Users)
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
	
	/*
	 * @Bean public CacheManager cacheManager() { return new EhcacheManager( new
	 * XmlConfiguration(getClass().getResource("src/main/resources/ehcache.xml")) );
	 * }
	 */
}
