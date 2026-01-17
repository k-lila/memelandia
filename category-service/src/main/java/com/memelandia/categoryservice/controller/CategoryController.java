package com.memelandia.categoryservice.controller;

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
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> registerCategory(@RequestBody @Valid Category category) {
        Category registered = registerService.registerCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @Operation(summary = "Buscar categoria por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> searchById(@PathVariable(value = "id", required = true) String id) {
        Category category = searchService.searchById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Modificar uma categoria")
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category) {
        Category updated  = registerService.updateCategory(category);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar uma categoria")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable(value = "id", required = true) String id) {
        registerService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user/{user}")
    public ResponseEntity<Page<Category>> searchByUser(@PathVariable String user, @ParameterObject Pageable pageable) {
        Page<Category> categories = searchService.searchByUser(user, pageable);
        return ResponseEntity.ok(categories);
    }

}
