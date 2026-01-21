package com.memelandia.userservice.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "Nome", minLength = 1, maxLength = 50, nullable = false)
    private String name;

    @NotNull
    @Schema(description = "CPF", nullable = false)
    @Pattern(regexp = "\\d{11}", message = "CPF inválido")
    @Indexed(unique = true)
    private String cpf;

    @NotNull
    @Schema(description = "E-mail", nullable = false)
    @Pattern(regexp = ".+@.+\\..+", message = "E-mail inválido")
    private String email;

    @NotNull
    @Schema(description = "Data de registro", nullable = false)
    private Instant registration_date;
}
