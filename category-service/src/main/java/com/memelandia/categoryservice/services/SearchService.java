package com.memelandia.categoryservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.exceptions.DomainEntityNotFound;
import com.memelandia.categoryservice.repository.ICategoryRepository;

@Service
public class SearchService {

    private ICategoryRepository categoryRepository;

    @Autowired
    public SearchService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> searchAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category searchById(String id) {
        Optional<Category> Category = categoryRepository.findById(id);
        if (Category.isEmpty()) {
            throw new DomainEntityNotFound(Category.class,"ID" , id);
        }
        return Category.get();
    }

}
