package com.spgroup.friendsmanagement.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spgroup.friendsmanagement"})
@EntityScan("com.spgroup.friendsmanagement.model")
@EnableAutoConfiguration
@EnableJpaRepositories("com.spgroup.friendsmanagement.repository")
@PropertySource("classpath:application.properties")

public class FriendsmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsmanagementApplication.class, args);
	}
}
