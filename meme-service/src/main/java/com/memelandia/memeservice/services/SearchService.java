package com.memelandia.memeservice.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.exceptions.DomainEntityNotFound;
import com.memelandia.memeservice.repository.IMemeRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Serviço de busca", description = "Serviços de busca de memes")
@Service
public class SearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);
    private IMemeRepository memeRepository;

    @Autowired
    public SearchService(IMemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    public Page<Meme> searchAll(Pageable pageable) {
        Page<Meme> page = memeRepository.findAll(pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            memeRepository.count()
        );
        return page;
    }

    public Meme searchById(String memeID) {
        Optional<Meme> meme = memeRepository.findById(memeID);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class, "ID" , memeID);
        }
        Meme found = meme.get();
        LOGGER.info(
            "| meme encontrado | ID: {}",
            found.getId()
        );
        return found;
    }

    public Page<Meme> searchByName(String name, Pageable pageable) {
        Page<Meme> memes = memeRepository.findByName(name, pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total: {}, páginas: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            memes.getTotalElements(),
            memes.getTotalPages()
        );
        return memes;    
    }

    public Page<Meme> searchByCategory(String categoryID, Pageable pageable) {
        Page<Meme> memes = memeRepository.findByCategoryID(categoryID, pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total: {}, páginas: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            memes.getTotalElements(),
            memes.getTotalPages()
        );
        return memes;
    }

    public Page<Meme> searchByUser(String user, Pageable pageable) {
        Page<Meme> memes = memeRepository.findByUserID(user, pageable);
        LOGGER.info(
            "| página encontrada | {}x{} | total: {}, páginas: {}",
            pageable.getPageSize(),
            pageable.getPageNumber(),
            memes.getTotalElements(),
            memes.getTotalPages()
        );
        return memes;
    }

    public Meme randomMeme() {
        long total = memeRepository.count();
        int randNum = (int) (total * Math.random());
        Pageable pageable = PageRequest.of(randNum, 1);
        Page<Meme> randomMeme = memeRepository.findAll(pageable);
        if (randomMeme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class);
        }
        Meme meme = randomMeme.getContent().getFirst();
        LOGGER.info(
            "| Meme escolhido | ID: {}",
            meme.getId()
        );
        return meme;
    }

}
