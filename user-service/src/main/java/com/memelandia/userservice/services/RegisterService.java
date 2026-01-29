package com.memelandia.userservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.userservice.domain.User;
import com.memelandia.userservice.exceptions.DomainEntityNotFound;
import com.memelandia.userservice.repository.IUserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Serviço de registro", description = "Serviços de registro de usuários")
@Service
public class RegisterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);
    private IUserRepository userRepository;

    @Autowired
    public RegisterService(IUserRepository iUserRepository) {
        this.userRepository = iUserRepository;
    }

    public User registerUser(@Valid User user) {
        if (userRepository.findByCpf(user.getCpf()).isPresent()) {
            LOGGER.info(
                "| cpf já cadastrado",
                user.getCpf()
            );
            throw new RuntimeException("CPF já cadastrado");
        }
        User registered = userRepository.insert(user);
        LOGGER.info(
            "| usuário registrado | nome: {}, ID: {}",
            registered.getName(),
            registered.getId() 
        );
        return registered;
    }

    public User updateUser(@Valid User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new DomainEntityNotFound(user.getClass(),"ID" , user.getId());
        }
        User updated = userRepository.save(user);
        LOGGER.info(
            "| usuário atualizado | ID: {}",
            updated.getName(),
            updated.getId()
        );
        return updated;
    }

    public void deleteUser(String userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"ID" , userID);
        }
        userRepository.delete(user.get());
        LOGGER.info(
            "| usuário deletado | ID: {}",
            userID
        );
    }
}
