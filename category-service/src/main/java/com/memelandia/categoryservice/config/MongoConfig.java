package com.memelandia.categoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    
    @Bean
    public MongoClient mongoClient() {
        // return MongoClients.create("mongodb+srv://memelandia:memelandia@memelandia.pr6cutx.mongodb.net/category-db?appName=memelandia");
        return MongoClients.create("mongodb://category_service:category_1234321@localhost:27021");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), "category-db");
    } 
}