package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TweetPresenter {

    private UserService userService;
    private TweetService tweetService;

    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    public TweetPresenter(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    public boolean createAndSaveTweet(String tweetText) {
        if (!tweetText.isEmpty()) {
            User user = (User) VaadinSession.getCurrent().getAttribute("currentUser");
            Tweet tweet = new Tweet(tweetText, new ArrayList<User>(), new ArrayList<User>(), 0, user);
            LOGGER.info("Tweet creation success: " + tweetText);
            tweetService.createTweet(tweet);
            return true;
        } else {
            LOGGER.info("Tweet creation failure " + tweetText);
            return false;
        }
    }

}
