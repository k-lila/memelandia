package com.memelandia.userservice.controller;

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

import com.memelandia.userservice.domain.User;
import com.memelandia.userservice.services.RegisterService;
import com.memelandia.userservice.services.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@Tag(
    name = "Users",
    description = "Endpoints do serviço de usuários"
)
@RestController
@RequestMapping(value = "users")
public class UserController {

    private RegisterService registerService;
    private SearchService searchService;

    @Autowired
    public UserController(RegisterService registerService, SearchService searchService) {
        this.registerService = registerService;
        this.searchService = searchService;
    }

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<Page<User>> searchAll(Pageable pageable) {
        Page<User> users = searchService.searchAll(pageable);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
        User registered = registerService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> searchById(@PathVariable(value = "id", required = true) String userID) {
        User user = searchService.searchById(userID);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Buscar usuário por CPF")
    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<User> searchByCPF(@PathVariable(value = "cpf", required = true) String cpf) {
        User user = searchService.searchByCPF(cpf);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Modificar um usuário")
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        User updated  = registerService.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar um usuário")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable(value = "id", required = true) String userID) {
        registerService.deleteUser(userID);
        return ResponseEntity.noContent().build();
    }

}
