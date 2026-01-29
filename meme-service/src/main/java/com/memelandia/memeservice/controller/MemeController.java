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
        LOGGER.info(
            "| GET | buscar todos | tamanho e número da página: {}x{}",
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        Page<Meme> memes = searchService.searchAll(pageable);
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Registrar um meme")
    @PostMapping
    public ResponseEntity<Meme> registerMeme(@RequestBody @Valid Meme meme) {
        LOGGER.info(
            "| POST | nome: {}",
            meme.getName()
        );
        Meme registered = registerService.registerMeme(meme);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @Operation(summary = "Buscar meme por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Meme> searchById(@PathVariable(value = "id", required = true) String memeID) {
        LOGGER.info(
            "| GET | buscar por ID | ID: {}",
            memeID
        );
        Meme meme = searchService.searchById(memeID);
        return ResponseEntity.ok(meme);
    }

    @Operation(summary = "Modificar um meme")
    @PutMapping
    public ResponseEntity<Meme> updateMeme(@RequestBody @Valid Meme meme) {
        LOGGER.info(
            "| PUT | nome: {}, id: {}",
            meme.getName(),
            meme.getId()
        );
        Meme updated  = registerService.updateMeme(meme);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deletar um meme")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeMeme(@PathVariable(value = "id", required = true) String memeID) {
        LOGGER.info(
            "| DELETE | id: {}",
            memeID
        );
        registerService.deleteMeme(memeID);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar meme pelo nome")
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Meme>> searchByName(@PathVariable String name, @ParameterObject Pageable pageable) {
        LOGGER.info(
            "| GET | buscar por nome | nome: {} | tamanho e número da página: {}x{}",
            name,
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        Page<Meme> memes = searchService.searchByName(name, pageable);
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Buscar memes por categoria")
    @GetMapping(value = "/category/{categoryID}")
    public ResponseEntity<Page<Meme>> searchByCategory(@PathVariable String categoryID, @ParameterObject Pageable pageable) {
        LOGGER.info(
            "| GET | buscar por categoria | categoryID: {} | tamanho e número da página: {}x{}",
            categoryID,
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        Page<Meme> memes = searchService.searchByCategory(categoryID, pageable);
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Buscar memes por usuário")
    @GetMapping(value = "/user/{userID}")
    public ResponseEntity<Page<Meme>> searchByUser(@PathVariable String userID, @ParameterObject Pageable pageable) {
        LOGGER.info(
            "| GET | buscar por usuário | userID: {} | tamanho e número da página: {}x{}",
            userID,
            pageable.getPageSize(),
            pageable.getPageNumber()
        );
        Page<Meme> memes = searchService.searchByUser(userID, pageable);
        return ResponseEntity.ok(memes);
    }

    @Operation(summary = "Meme aleatório")
    @GetMapping(value = "/random")
    public ResponseEntity<Meme> randomMeme() {
        LOGGER.info("| Meme do dia! |");
        Meme memeDoDia = searchService.randomMeme();
        return ResponseEntity.ok(memeDoDia);
    }
}
