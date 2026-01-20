package com.memelandia.memeservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.memelandia.memeservice.domain.Meme;

@Repository
public interface IMemeRepository extends MongoRepository<Meme, String> {
    Page<Meme> findByName(String name, Pageable pageable);
    Page<Meme> findByCategoryID(String categoryID, Pageable pageable);
    Page<Meme> findByUserID(String userID, Pageable pageable);
}
