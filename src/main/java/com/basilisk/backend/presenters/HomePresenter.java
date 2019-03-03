package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.FollowService;
import com.basilisk.backend.services.RetweetService;
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
    private RetweetService retweetService;
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

    public List<TweetDisplayComponent> getAllHomePageTweetsDisplayComponents(User user) {
        List<Tweet> tweetList = getAllUserAndFollowingTweets(user);
        List<Tweet> retweetList = getAllUserAndFollowingRetweets(user);
        List<TweetDisplayComponent> tweetDisplayComponentList = new LinkedList<>();

        List<Tweet> fullTweetList = new LinkedList<>();
        fullTweetList.addAll(tweetList);
        fullTweetList.addAll(retweetList);

        for (Tweet tweet : fullTweetList) {
            TweetDisplayComponent tweetDisplayComponent = new TweetDisplayComponent(tweetPresenter, tweet);
            tweetDisplayComponentList.add(tweetDisplayComponent);
        }
        return tweetDisplayComponentList;
    }

    private List<Tweet> getAllUserAndFollowingTweets(User user) {
        List<Tweet> tweetList = tweetService.getAllTweetsByUser(user);
        List<User> userFollowingList = followService.getAllUserFollowings(user);

        for (User userFollowing : userFollowingList) {
            List<Tweet> userFollowingTweetList = tweetService.getAllTweetsByUser(userFollowing);
            tweetList.addAll(userFollowingTweetList);
        }
        return tweetList;
    }

    private List<Tweet> getAllUserAndFollowingRetweets(User user) {
        List<Retweet> retweetList = retweetService.getAllRetweetsByUser(user);
        List<Tweet> tweetList = GetTweetListFromRetweetList(retweetList);
        List<User> userFollowingList = followService.getAllUserFollowings(user);

        for (User userFollowing : userFollowingList) {
            List<Retweet> userFollowingRetweets = retweetService.getAllRetweetsByUser(userFollowing);
            List<Tweet> userFollowingRetweetList = GetTweetListFromRetweetList(userFollowingRetweets);
            tweetList.addAll(userFollowingRetweetList);
        }
        return tweetList;
    }

    private List<Tweet> GetTweetListFromRetweetList(List<Retweet> retweetList) {
        List<Tweet> tweetList = new LinkedList<>();

        for (Retweet retweet : retweetList) {
            tweetList.add(retweet.getTweet());
        }
        return tweetList;
    }

}