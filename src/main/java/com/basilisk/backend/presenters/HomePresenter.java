package com.basilisk.backend.presenters;

import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomePresenter {

    private UserService userService;
    private TweetService tweetService;
    private static final Logger LOGGER = Logger.getLogger(HomePresenter.class);

    @Autowired
    public HomePresenter(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }
}