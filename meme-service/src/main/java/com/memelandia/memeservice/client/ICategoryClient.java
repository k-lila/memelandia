package com.memelandia.memeservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.memelandia.memeservice.dtos.CategoryDTO;

@FeignClient(name = "category-service")
public interface ICategoryClient {
    @GetMapping(value = "/categories/{id}")
    CategoryDTO getCategoryDTOByID(@PathVariable(value = "id") String categoryID);
}
