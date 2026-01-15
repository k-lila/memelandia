package com.memelandia.userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.memelandia.userservice.domain.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    Optional<User> findByCpf(String cpf);
}
