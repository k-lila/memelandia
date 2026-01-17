package com.memelandia.memeservice.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "memes")
@Getter
@Setter
public class Meme {

    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "nome", minLength = 1, maxLength = 50, nullable = false)
    private String name;

    @NotNull
    @Schema(description = "descrição", minContains = 0, maxContains = 500, nullable = false)
    private String description;

    @NotNull
    @Schema(description = "data de registro", nullable = false)
    private Instant registration_date;

    @Indexed
    @Schema(description = "ID da categoria")
    private String category;

    @Indexed
    @Schema(description = "ID do usuário")
    private String user;

}
