package com.basilisk.backend.services;

import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RetweetService {

    private RetweetRepository retweetRepository;

    // Constructor
    @Autowired
    public RetweetService(RetweetRepository retweetRepository) {
        this.retweetRepository = retweetRepository;
    }

    // Public Service methods
    public List<Retweet> getUserRetweets(User user) {
        return retweetRepository.getAllByUser(user);
    }

    // private CRUD Operations
    private void createRetweet(Retweet retweet) {
        retweetRepository.save(retweet);
    }

    private void deleteRetweet(Retweet retweet) {
        retweetRepository.delete(retweet);
    }

    private void updateRetweet(Retweet retweet) {
        retweetRepository.save(retweet);
    }

    private Retweet retrieveRetweet(long id) {
        return retweetRepository.getOne(id);
    }

}
