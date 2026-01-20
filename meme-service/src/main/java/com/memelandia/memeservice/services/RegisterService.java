package com.memelandia.memeservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.memeservice.client.ICategoryClient;
import com.memelandia.memeservice.client.IUserClient;
import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.exceptions.DomainEntityNotFound;
import com.memelandia.memeservice.exceptions.ServiceException;
import com.memelandia.memeservice.repository.IMemeRepository;

import feign.FeignException;
import jakarta.validation.Valid;

@Service
public class RegisterService {

    private IMemeRepository memeRepository;
    private IUserClient userClient;
    private ICategoryClient categoryClient;

    @Autowired
    public RegisterService(IMemeRepository memeRepository, IUserClient userClient, ICategoryClient categoryClient) {
        this.memeRepository = memeRepository;
        this.userClient = userClient;
        this.categoryClient = categoryClient;
    }

    private void validateUser(String userID) {
        try {
            userClient.getUserDTOByID(userID);
        } catch (FeignException exception) {
            throw new ServiceException("Erro ao validar usuário", "user-service", exception);
        }
    }

    private void validateCategory(String categoryID) {
        try {
            categoryClient.getCategoryDTOByID(categoryID);
        } catch (FeignException exception) {
            throw new ServiceException("Erro ao validar categoria", "category-service", exception);
        }
    }

    public Meme registerMeme(@Valid Meme meme) {
        validateUser(meme.getUserID());
        validateCategory(meme.getCategoryID());
        return this.memeRepository.insert(meme);
    }

    public Meme updateMeme(@Valid Meme meme) {
        if (!memeRepository.existsById(meme.getId())) {
            throw new DomainEntityNotFound(meme.getClass(),"ID" , meme.getId());
        }
        validateUser(meme.getUserID());
        validateCategory(meme.getCategoryID());
        return this.memeRepository.save(meme);
    }

    public void deleteMeme(String memeID) {
        Optional<Meme> meme = memeRepository.findById(memeID);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class,"ID" , memeID);
        }
        memeRepository.delete(meme.get());
    }

}
