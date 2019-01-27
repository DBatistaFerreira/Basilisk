package com.basilisk.backend.services;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    //CRUD OPERATIONS
    //Save tweet into DB
    public void createTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    //Delete tweet from DB
    public void deleteTweet(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

    //Update tweet into DB
    public void updateTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    //Get one tweet with tweet id
    public Tweet retrieveTweet(long id) {
        return tweetRepository.getOne(id);
    }

    //Get all tweets from specific user 
    public List<Tweet> retrieveAllTweets(User user) {
        return tweetRepository.getAllByUser(user);
    }
}
