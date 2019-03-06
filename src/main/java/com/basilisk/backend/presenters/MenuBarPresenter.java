package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuBarPresenter {

    private UserService userService;
    private TweetService tweetService;
    private RetweetService retweetService;
    private FollowService followService;
    private CommentService commentService;

    private static Logger LOGGER = Logger.getLogger(MenuBarPresenter.class);

    @Autowired
    public MenuBarPresenter(UserService userService, TweetService tweetService, RetweetService retweetService,
                            FollowService followService, CommentService commentService)
    {
        this.userService = userService;
        this.userService = userService;
        this.tweetService = tweetService;
        this.retweetService = retweetService;
        this.followService = followService;
    }

    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    public List<User> getSelectedUsers(String name) {
        return userService.searchForUsers(name);
    }
}