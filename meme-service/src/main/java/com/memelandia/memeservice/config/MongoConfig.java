package com.memelandia.memeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongoClient() {
        // return MongoClients.create("mongodb://meme_service:meme_1234321@localhost:27022");
        return MongoClients.create("mongodb+srv://memelandia:memelandia@memelandia.pr6cutx.mongodb.net/meme-db?appName=memelandia");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), "meme-db");
    } 
}