package com.basilisk.backend.presenters;

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
public class HomePresenter {

    private UserService userService;
    private TweetService tweetService;
    private FollowService followService;
    private TweetPresenter tweetPresenter;
    private static final Logger LOGGER = Logger.getLogger(HomePresenter.class);

    @Autowired
    public HomePresenter(UserService userService, TweetService tweetService, FollowService followService, TweetPresenter tweetPresenter) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.followService = followService;
        this.tweetPresenter = tweetPresenter;
    }

    public List<User> searchForUsers(String username) {
        LOGGER.info("Searching for user: " + username);
        List<User> users = userService.searchForUsers(username);
        LOGGER.info("Search Results: ");
        for (User user : users) {
            LOGGER.info(user);
        }
        return users;
    }

    public List<TweetDisplayComponent> getAllUserTweetsDisplayComponents(User user) {
        List<Tweet> tweetList = tweetService.getAllTweetsByUser(user);
        List<User> userFollowingsList = followService.getAllUserFollowings(user);

        for (User userFollowing : userFollowingsList) {
            List<Tweet> userFollowingTweetList = tweetService.getAllTweetsByUser(userFollowing);

            for (Tweet userFollowingTweet : userFollowingTweetList) {
                tweetList.add(userFollowingTweet);
            }
        }

        List<TweetDisplayComponent> tweetComponentDisplayList = new LinkedList<>();

        for (Tweet tweet : tweetList) {
            TweetDisplayComponent tweetDisplayComponent = new TweetDisplayComponent(tweetPresenter, tweet);
            tweetComponentDisplayList.add(tweetDisplayComponent);
        }
        return tweetComponentDisplayList;
    }

}