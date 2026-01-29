package com.memelandia.userservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.userservice.domain.User;
import com.memelandia.userservice.exceptions.DomainEntityNotFound;
import com.memelandia.userservice.repository.IUserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Serviço de busca", description = "Serviços de busca de usuários")
@Service
public class SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    private IUserRepository userRepository;

    @Autowired
    public SearchService(IUserRepository iUserRepository) {
        this.userRepository = iUserRepository;
    }

    public Page<User> searchAll(Pageable pageable) {
        Page<User> found = userRepository.findAll(pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            userRepository.count()
        );
        return found;
    }

    public User searchById(String userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"ID" , userID);
        }
        User found = user.get();
        LOGGER.info(
            "| usuário encontrado | ID: {}",
            found.getId()
        );
        return found;
    }

    public User searchByCPF(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"CPF" , cpf);
        }
        User found = user.get();
        LOGGER.info(
            "| usuário encontrado | ID: {}",
            found.getId()
        );
        return found;    
    }
}
