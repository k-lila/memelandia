package com.memelandia.userservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.userservice.domain.User;
import com.memelandia.userservice.exceptions.DomainEntityNotFound;
import com.memelandia.userservice.repository.IUserRepository;

@Service
public class SearchService {

    private IUserRepository userRepository;

    @Autowired
    public SearchService(IUserRepository iUserRepository) {
        this.userRepository = iUserRepository;
    }

    public Page<User> searchAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User searchById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"ID" , id);
        }
        return user.get();
    }

    public User searchByCPF(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"CPF" , cpf);
        }
        return user.get();    
    }

}
