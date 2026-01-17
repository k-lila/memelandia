package com.memelandia.categoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.memelandia.categoryservice.domain.Category;


@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {
}
