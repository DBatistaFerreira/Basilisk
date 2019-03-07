package com.basilisk.backend.services;

import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetweetService {

    private RetweetRepository retweetRepository;

    // Constructor
    @Autowired
    public RetweetService(RetweetRepository retweetRepository) {
        this.retweetRepository = retweetRepository;
    }

    // Public Service methods
    public void postRetweet(Retweet retweet) { createRetweet(retweet); }

    public void removeRetweet(Retweet retweet) { deleteRetweet(retweet); }

    public Retweet getRetweetById(long id) { return retrieveRetweet(id); }

    public List<Retweet> getAllRetweetsByUser(User user) { return retrieveAllRetweets(user); }

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

    private List<Retweet> retrieveAllRetweets(User user) { return retweetRepository.getAllByUser(user); }

}
