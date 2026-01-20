package com.memelandia.categoryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.memelandia.categoryservice.dtos.UserDTO;

@FeignClient(name = "user-service")
public interface IUserClient {
    @GetMapping("/users/{id}")
    UserDTO getUsertDTOByID(@PathVariable(value = "id") String userID);
}
