package dmacc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dmacc.beans.User;

@Configuration
public class BeanConfiguration {

	@Bean
	public User user() {
		User bean = new User("email@site.com", "1245678");
		//bean.setName("Dr. Seuss");
		//bean.setPhone("555-555-5555");
		//bean.setRelationship("friend");
		return bean;
	}
}
