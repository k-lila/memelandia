package com.memelandia.categoryservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.exceptions.DomainEntityNotFound;
import com.memelandia.categoryservice.repository.ICategoryRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Serviço de busca", description = "Serviços de busca de categorias")
@Service
public class SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    private ICategoryRepository categoryRepository;

    @Autowired
    public SearchService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> searchAll(Pageable pageable) {
        Page<Category> page = categoryRepository.findAll(pageable); 
        LOGGER.info(
            "| página encontrada | {}x{} | total (categorias): {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            categoryRepository.count()
        );
        return page;
    }

    public Category searchById(String categoryID) {
        Optional<Category> Category = categoryRepository.findById(categoryID);
        if (Category.isEmpty()) {
            throw new DomainEntityNotFound(Category.class,"ID" , categoryID);
        }
        Category found = Category.get();
        LOGGER.info(
            "| categoria encontrada | ID: {}",
            found.getId()
        );
        return found;
    }

    public Page<Category> searchByUser(String userID, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByUserID(userID, pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total (categorias): {}, páginas: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            categories.getTotalElements(),
            categories.getTotalPages()
        );
        return categories;
    }

    public Page<Category> searchByName(String name, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByName(name, pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total (categorias): {}, páginas: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            categories.getTotalElements(),
            categories.getTotalPages()
        );
        return categories;
    }
}
