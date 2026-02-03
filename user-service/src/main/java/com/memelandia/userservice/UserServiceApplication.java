package com.memelandia.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.mongodb.autoconfigure.health.MongoHealthContributorAutoConfiguration;

@SpringBootApplication(
	exclude = MongoHealthContributorAutoConfiguration.class
)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
