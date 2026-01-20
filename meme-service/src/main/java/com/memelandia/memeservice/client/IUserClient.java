package com.memelandia.memeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.memelandia.memeservice.dtos.UserDTO;

@FeignClient(name = "user-service")
public interface IUserClient {
    @GetMapping(value = "/users/{id}")
    UserDTO getUserDTOByID(@PathVariable(value = "id") String userID);
}
