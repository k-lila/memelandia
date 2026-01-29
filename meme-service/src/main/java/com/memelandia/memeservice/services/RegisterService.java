package com.memelandia.memeservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.memeservice.client.ICategoryClient;
import com.memelandia.memeservice.client.IUserClient;
import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.exceptions.DomainEntityNotFound;
import com.memelandia.memeservice.exceptions.ServiceException;
import com.memelandia.memeservice.repository.IMemeRepository;

import feign.FeignException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Serviço de registro", description = "Serviços de registro de memes")
@Service
public class RegisterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);
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
            LOGGER.info(
                "| usuário inválido | ID: {}",
                userID
            );
            throw new ServiceException("Erro ao validar usuário", "user-service", exception);
        }
    }

    private void validateCategory(String categoryID) {
        try {
            categoryClient.getCategoryDTOByID(categoryID);
        } catch (FeignException exception) {
            LOGGER.info(
                "| categoria inválida | ID: {}",
                categoryID
            );
            throw new ServiceException("Erro ao validar categoria", "category-service", exception);
        }
    }

    public Meme registerMeme(@Valid Meme meme) {
        validateUser(meme.getUserID());
        validateCategory(meme.getCategoryID());
        Meme registered = memeRepository.insert(meme); 
        LOGGER.info(
            "| meme registrado | nome: {}, ID: {}",
            registered.getName(),
            registered.getId() 
        );
        return registered;
    }

    public Meme updateMeme(@Valid Meme meme) {
        if (!memeRepository.existsById(meme.getId())) {
            throw new DomainEntityNotFound(meme.getClass(),"ID" , meme.getId());
        }
        validateUser(meme.getUserID());
        validateCategory(meme.getCategoryID());
        Meme updated = memeRepository.save(meme);
        LOGGER.info(
            "| meme atualizado | ID: {}",
            updated.getId()
        );
        return updated;
    }

    public void deleteMeme(String memeID) {
        Optional<Meme> meme = memeRepository.findById(memeID);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class,"ID" , memeID);
        }
        memeRepository.delete(meme.get());
        LOGGER.info(
            "| meme deletado | ID: {}",
            memeID
        );
    }
}
