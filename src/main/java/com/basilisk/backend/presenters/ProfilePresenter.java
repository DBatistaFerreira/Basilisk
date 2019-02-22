package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Follow;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.FollowService;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.basilisk.frontend.components.TweetDisplayComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfilePresenter {

    private UserService userService;
    private TweetService tweetService;
    private TweetPresenter tweetPresenter;
    private FollowService followService;
    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    @Autowired
    public ProfilePresenter(UserService userService, TweetService tweetService, TweetPresenter tweetPresenter, FollowService followService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.tweetPresenter = tweetPresenter;
        this.followService = followService;
    }

    public List<TweetDisplayComponent> getAllUserTweetsDisplayComponents(User user) {
        List<Tweet> tweetList = tweetService.getAllTweetsByUser(user);
        List<TweetDisplayComponent> tweetComponentDisplayList = new LinkedList<>();

        for (Tweet tweet : tweetList) {
            TweetDisplayComponent tweetDisplayComponent = new TweetDisplayComponent(tweetPresenter);
            tweetDisplayComponent.setTweet(tweet);
            tweetComponentDisplayList.add(tweetDisplayComponent);
        }
        return tweetComponentDisplayList;
    }

    public User getUser(String username) {
        LOGGER.info("Retrieving user: " + username);
        User user = userService.retrieveUserByUserName(username);
        LOGGER.info("Retrieved user: " + username);
        return user;
    }
}