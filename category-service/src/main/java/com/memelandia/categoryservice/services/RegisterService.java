package com.memelandia.categoryservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.categoryservice.client.IUserClient;
import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.exceptions.DomainEntityNotFound;
import com.memelandia.categoryservice.exceptions.ServiceException;
import com.memelandia.categoryservice.repository.ICategoryRepository;

import feign.FeignException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Serviço de registro", description = "Serviços de registro de categorias")
@Service
public class RegisterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);
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
            LOGGER.info(
                "| usuário inválido | ID: {}",
                userID
            );
            throw new ServiceException("user-service", exception);
        }
    }

    public Category registerCategory(@Valid Category category) {
        validateUser(category.getUserID());
        Category registered = categoryRepository.insert(category);
        LOGGER.info(
            "| categoria registrada | nome: {}, ID: {}",
            registered.getName(),
            registered.getId() 
        );
        return registered;
    }

    public Category updateCategory(@Valid Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new DomainEntityNotFound(category.getClass(),"ID" , category.getId());
        }
        validateUser(category.getUserID());
        Category updated = categoryRepository.save(category);
        LOGGER.info(
            "| categoria atualizada | ID: {}",
            updated.getId()
        );
        return updated;
    }

    public void deleteCategory(String categoryID) {
        Optional<Category> category = categoryRepository.findById(categoryID);
        if (category.isEmpty()) {
            throw new DomainEntityNotFound(Category.class,"ID" , categoryID);
        }
        categoryRepository.delete(category.get());
        LOGGER.info(
            "| categoria deletada | ID: {}",
            categoryID
        );
    }
}
