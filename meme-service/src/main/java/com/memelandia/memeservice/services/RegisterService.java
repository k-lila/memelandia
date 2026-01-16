package com.memelandia.memeservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memelandia.memeservice.domain.Meme;
import com.memelandia.memeservice.exceptions.DomainEntityNotFound;
import com.memelandia.memeservice.repository.IMemeRepository;

import jakarta.validation.Valid;

@Service
public class RegisterService {

    private IMemeRepository memeRepository;

    @Autowired
    public RegisterService(IMemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    public Meme registerMeme(@Valid Meme meme) {
        return this.memeRepository.insert(meme);
    }

    public Meme updateMeme(@Valid Meme meme) {
        if (!memeRepository.existsById(meme.getId())) {
            throw new DomainEntityNotFound(meme.getClass(),"ID" , meme.getId());
        }
        return this.memeRepository.save(meme);
    }

    public void deleteMeme(String id) {
        Optional<Meme> meme = memeRepository.findById(id);
        if (meme.isEmpty()) {
            throw new DomainEntityNotFound(Meme.class,"ID" , id);
        }
        memeRepository.delete(meme.get());
    }

}
