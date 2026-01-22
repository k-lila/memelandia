package com.memelandia.categoryservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.categoryservice.client.IUserClient;
import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.exceptions.DomainEntityNotFound;
import com.memelandia.categoryservice.exceptions.ServiceException;
import com.memelandia.categoryservice.repository.ICategoryRepository;

import feign.FeignException;
import jakarta.validation.Valid;

@Service
public class RegisterService {
    private ICategoryRepository categoryRepository;
    private IUserClient userClient;

    @Autowired
    public RegisterService(ICategoryRepository categoryRepository, IUserClient userClient) {
        this.categoryRepository = categoryRepository;
        this.userClient = userClient;
    }

    private void validateUser(String userID) {
        try {
            userClient.getUsertDTOByID(userID);
        } catch (FeignException exception) {
            throw new ServiceException("user-service", exception);
        }
    }

    public Category registerCategory(@Valid Category category) {
        validateUser(category.getUserID());
        return this.categoryRepository.insert(category);
    }

    public Category updateCategory(@Valid Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new DomainEntityNotFound(category.getClass(),"ID" , category.getId());
        }
        validateUser(category.getUserID());
        return this.categoryRepository.save(category);
    }

    public void deleteCategory(String categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isEmpty()) {
            throw new DomainEntityNotFound(Category.class,"ID" , categoryID);
        }
        categoryRepository.delete(category.get());
    }
}
