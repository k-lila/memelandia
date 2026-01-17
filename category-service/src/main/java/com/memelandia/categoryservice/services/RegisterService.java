package com.memelandia.categoryservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.exceptions.DomainEntityNotFound;
import com.memelandia.categoryservice.repository.ICategoryRepository;

import jakarta.validation.Valid;

@Service

public class RegisterService {

    private ICategoryRepository categoryRepository;

    @Autowired
    public RegisterService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category registerCategory(@Valid Category category) {
        return this.categoryRepository.insert(category);
    }

    public Category updateCategory(@Valid Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new DomainEntityNotFound(category.getClass(),"ID" , category.getId());
        }
        return this.categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new DomainEntityNotFound(Category.class,"ID" , id);
        }
        categoryRepository.delete(category.get());
    }
}
