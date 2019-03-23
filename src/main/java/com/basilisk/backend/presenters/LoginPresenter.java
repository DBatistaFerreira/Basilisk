


package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class LoginPresenter {

    private UserService userService;
    private TweetService tweetService;
    private RetweetService retweetService;
    private FollowService followService;
    private CommentService commentService;
    private static final Logger LOGGER = Logger.getLogger(LoginPresenter.class);

    @Autowired
    public LoginPresenter(UserService userService, TweetService tweetService, RetweetService retweetService,
                          FollowService followService, CommentService commentService){
        this.userService = userService;
        this.tweetService = tweetService;
        this.retweetService = retweetService;
        this.followService = followService;
        this.commentService = commentService;
    }

    public boolean loginUser(String username, String password) {
        LOGGER.info("Login Attempt: Username = " + username);
        String hashedPassword = passwordHash(password);
        User user = userService.login(username, hashedPassword);
        if (!Objects.isNull(user)) {
            LOGGER.info("Login Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Login Failure : Username = " + username);
            return false;
        }
    }

    public User getUser(String userName) {
        return userService.getUserByUsername(userName);
    }

    //Added sign up method
    public boolean signupUser(String name, String username, String password) {
        LOGGER.info("Signup Attempt: Username = " + username);

        if (name.length() == 0 || username.length() == 0 || password.length() == 0) {
            return false;
        }

        User user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            String hashedPasswordStr = passwordHash(password);
            user = new User(name, username, hashedPasswordStr, "", null);
            userService.createNewUser(user);
            LOGGER.info("Signup Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Signup Failure : Username = " + username);
            return false;
        }
    }

    public String passwordHash(String password) {

        byte[] hashedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hashedPasswordStr = new String(hashedPassword);

        return hashedPasswordStr;
    }

}
