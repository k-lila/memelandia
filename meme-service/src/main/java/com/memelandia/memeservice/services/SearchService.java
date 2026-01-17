package com.memelandia.memeservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.exceptions.DomainEntityNotFound;
import com.memelandia.memeservice.repository.IMemeRepository;

@Service
public class SearchService {

    private IMemeRepository memeRepository;

    @Autowired
    public SearchService(IMemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    public Page<Meme> searchAll(Pageable pageable) {
        return memeRepository.findAll(pageable);
    }

    public Meme searchById(String memeID) {
        Optional<Meme> meme = memeRepository.findById(memeID);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class, "ID" , memeID);
        }
        return meme.get();
    }

    public Meme searchByName(String name) {
        Optional<Meme> meme = memeRepository.findByName(name);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class,"Name" , name);
        }
        return meme.get();    
    }

    public Page<Meme> searchByCategory(String categoryID, Pageable pageable) {
        Page<Meme> memes = memeRepository.findByCategoryID(categoryID, pageable);
        if (memes.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class, "Category ID" , categoryID);
        }
        return memes;
    }

    public Page<Meme> searchByUser(String user, Pageable pageable) {
        Page<Meme> memes = memeRepository.findByUserID(user, pageable);
        if (memes.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class, "User ID" , user);
        }
        return memes;
    }

}
