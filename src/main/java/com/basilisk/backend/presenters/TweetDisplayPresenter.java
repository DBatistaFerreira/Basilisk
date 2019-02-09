package com.basilisk.backend.presenters;

import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TweetDisplayPresenter {

    private UserService userService;
    private TweetService tweetService;

    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    public TweetDisplayPresenter(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }


}
