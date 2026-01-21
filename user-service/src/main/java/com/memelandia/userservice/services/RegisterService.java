package com.memelandia.userservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.userservice.domain.User;
import com.memelandia.userservice.exceptions.DomainEntityNotFound;
import com.memelandia.userservice.repository.IUserRepository;

import jakarta.validation.Valid;

@Service
public class RegisterService {
    private IUserRepository userRepository;

    @Autowired
    public RegisterService(IUserRepository iUserRepository) {
        this.userRepository = iUserRepository;
    }

    public User registerUser(@Valid User user) {
        return this.userRepository.insert(user);
    }

    public User updateUser(@Valid User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new DomainEntityNotFound(user.getClass(),"ID" , user.getId());
        }
        return this.userRepository.save(user);
    }

    public void deleteUser(String userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) {
            throw new DomainEntityNotFound(User.class,"ID" , userID);
        }
        userRepository.delete(user.get());
    }
}
