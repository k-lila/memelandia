package com.memelandia.memeservice.controller;

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

import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.services.RegisterService;
import com.memelandia.memeservice.services.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
    name = "Memes",
    description = "Endpoints do serviço de memes"
)
@RestController
@RequestMapping(value = "memes")
public class MemeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemeController.class);
    private RegisterService registerService;
    private SearchService searchService;

    @Autowired
    public MemeController(RegisterService registerService, SearchService searchService) {
        this.registerService = registerService;
        this.searchService = searchService;
    }

    @Operation(summary = "Lista todos os memes")
    @GetMapping
    public ResponseEntity<Page<Meme>> searchAll(Pageable pageable) {
        Page<Meme> memes = searchService.searchAll(pageable);
        LOGGER.info(
            "Requisição para listar memes | Tamanho da página: {}, número da página: {}",
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Registrar um meme")
    @PostMapping
    public ResponseEntity<Meme> registerMeme(@RequestBody @Valid Meme meme) {
        Meme registered = registerService.registerMeme(meme);
        LOGGER.info(
            "Meme registrado | nome: {}, id: {}",
            registered.getName(),
            registered.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @Operation(summary = "Buscar meme por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Meme> searchById(@PathVariable(value = "id", required = true) String memeID) {
        Meme meme = searchService.searchById(memeID);
        LOGGER.info(
            "Busca por id | id: {}",
            memeID
        );
        return ResponseEntity.ok(meme);
    }

    @Operation(summary = "Buscar meme pelo nome")
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Meme>> searchByName(@PathVariable String name, @ParameterObject Pageable pageable) {
        Page<Meme> memes = searchService.searchByName(name, pageable);
        LOGGER.info(
            "Busca por nome | nome: {}",
            name
        );
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Modificar um meme")
    @PutMapping
    public ResponseEntity<Meme> updateMeme(@RequestBody @Valid Meme meme) {
        Meme updated  = registerService.updateMeme(meme);
        LOGGER.info(
            "Meme modificado | nome: {}, id: {}",
            meme.getName(),
            meme.getId()
        );
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar um meme")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeMeme(@PathVariable(value = "id", required = true) String memeID) {
        registerService.deleteMeme(memeID);
        LOGGER.info(
            "Meme deletado | id: {}",
            memeID
        );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar memes por categoria")
    @GetMapping(value = "/category/{categoryID}")
    public ResponseEntity<Page<Meme>> searchByCategory(@PathVariable String categoryID, @ParameterObject Pageable pageable) {
        Page<Meme> memes = searchService.searchByCategory(categoryID, pageable);
        LOGGER.info(
            "Busca de memes por categoria | categoria: {}",
            categoryID
        );
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Buscar memes por usuário")
    @GetMapping(value = "/user/{userID}")
    public ResponseEntity<Page<Meme>> searchByUser(@PathVariable String userID, @ParameterObject Pageable pageable) {
        Page<Meme> memes = searchService.searchByUser(userID, pageable);
        LOGGER.info(
            "Busca de memes por usuário | usuário: {}",
            userID
        );
        return ResponseEntity.ok(memes);
    }
}
