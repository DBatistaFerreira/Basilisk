package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.FriendListService;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.basilisk.frontend.components.TweetDisplayComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfilePresenter {

    private UserService userService;
    private TweetService tweetService;
    private FriendListService friendListService;
    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    @Autowired
    public ProfilePresenter(UserService userService, TweetService tweetService, FriendListService friendLisService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.friendListService = friendLisService;
    }

    public List<TweetDisplayComponent> getAllUserTweets(User user) {
        List<Tweet> tweetList = tweetService.retrieveAllTweets(user);
        Collections.reverse(tweetList); //Switch list to have highest id on the top

        List<TweetDisplayComponent> tweetDisplayComponentList = new LinkedList<>();

        for (Tweet e : tweetList) {
            //tweetDisplayComponentList.add(new TweetDisplayComponent(e));
        }
        return tweetDisplayComponentList;
    }
}