package com.memelandia.categoryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memelandia.categoryservice.domain.Category;
import com.memelandia.categoryservice.services.RegisterService;
import com.memelandia.categoryservice.services.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
    name = "categories",
    description = "Endpoints do serviço de usuários"
)
@RestController
@RequestMapping(value = "categories")
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private RegisterService registerService;
    private SearchService searchService;

    @Autowired
    public CategoryController(RegisterService registerService, SearchService searchService) {
        this.registerService = registerService;
        this.searchService = searchService;
    }

    @Operation(summary = "Lista todas as categorias")
    @GetMapping
    public ResponseEntity<Page<Category>> searchAll(Pageable pageable) {
        Page<Category> categories = searchService.searchAll(pageable);
        LOGGER.info(
            "Requisição para listar categorias | Tamanho da página: {}, número da página: {}",
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Registrar uma categoria")
    @PostMapping
    public ResponseEntity<Category> registerCategory(@RequestBody @Valid Category category) {
        Category registered = registerService.registerCategory(category);
        LOGGER.info(
            "Categoria registrada | nome: {}, id: {}",
            registered.getName(),
            registered.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @Operation(summary = "Buscar categoria por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> searchById(@PathVariable(value = "id", required = true) String categoryID) {
        Category category = searchService.searchById(categoryID);
        LOGGER.info(
            "Busca por id | id: {}",
            categoryID
        );
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Modificar uma categoria")
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category) {
        Category updated  = registerService.updateCategory(category);
        LOGGER.info(
            "Categoria modificada | nome: {}, id: {}",
            category.getName(),
            category.getId()
        );
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar uma categoria")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable(value = "id", required = true) String categoryID) {
        registerService.deleteCategory(categoryID);
        LOGGER.info(
            "Categoria deletada | id: {}",
            categoryID
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar categorias por usuário")
    @GetMapping(value = "/user/{userID}")
    public ResponseEntity<Page<Category>> searchByUser(@PathVariable(required = true) String userID, @ParameterObject Pageable pageable) {
        Page<Category> categories = searchService.searchByUser(userID, pageable);
        LOGGER.info(
            "Busca de categorias por usuário | usuário: {}",
            userID
        );
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Buscar categorias pelo nome")
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Category>> searchByName(@PathVariable(required = true) String name, @ParameterObject Pageable pageable) {
        Page<Category> categories = searchService.searchByName(name, pageable);
        LOGGER.info(
            "Busca de categorias pelo nome | nome: {}",
            name
        );
        return ResponseEntity.ok(categories);
    }
}
