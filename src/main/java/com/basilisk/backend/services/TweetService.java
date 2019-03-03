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

    // Constructor
    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    // Public Service methods
    public void writeTweet(Tweet tweet) {
        createTweet(tweet);
    }

    public void editTweet(Tweet tweet) {
        updateTweet(tweet);
    }

    public void removeTweet(Tweet tweet) { deleteTweet(tweet); }

    public Tweet getTweetById(long id) {
        return retrieveTweet(id);
    }

    public List<Tweet> getAllTweetsByUser(User user) { return retrieveAllTweets(user); }

    //CRUD OPERATIONS
    private void createTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    private void deleteTweet(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

    private void updateTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    private Tweet retrieveTweet(long id) {
        return tweetRepository.getOne(id);
    }

    private List<Tweet> retrieveAllTweets(User user) { return tweetRepository.getAllByUser(user); }
}
