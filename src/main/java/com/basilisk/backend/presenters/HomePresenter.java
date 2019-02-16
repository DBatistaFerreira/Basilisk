package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> searchForUser(String username) {
        LOGGER.info("Searching for user: " + username);
        List<User> users = userService.searchForUser(username);
        LOGGER.info("Search Results: ");
        for (User user : users) {
            LOGGER.info(user);
        }
        return users;
    }
}