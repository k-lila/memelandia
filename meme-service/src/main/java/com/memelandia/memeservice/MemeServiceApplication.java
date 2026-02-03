package com.memelandia.memeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.mongodb.autoconfigure.health.MongoHealthContributorAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
	exclude = MongoHealthContributorAutoConfiguration.class
)
@EnableFeignClients
public class MemeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeServiceApplication.class, args);
	}

}
