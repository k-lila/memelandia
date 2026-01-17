package com.memelandia.memeservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.memelandia.memeservice.domain.Meme;

@Repository
public interface IMemeRepository extends MongoRepository<Meme, String> {
    Optional<Meme> findByName(String name);
    Page<Meme> findByCategory(String categoryID, Pageable pageable);
    Page<Meme> findByUser(String userID, Pageable pageable);
}
