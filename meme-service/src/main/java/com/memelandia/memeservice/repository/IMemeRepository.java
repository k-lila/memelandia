package com.memelandia.memeservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.memelandia.memeservice.domain.Meme;

@Repository
public interface IMemeRepository extends MongoRepository<Meme, String> {
    Optional<Meme> findByName(String name);
}
