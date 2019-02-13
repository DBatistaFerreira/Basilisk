package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
            Tweet tweet = new Tweet(tweetText, 0, user);
            LOGGER.info("Tweet creation success: " + tweetText);
            tweetService.createTweet(tweet);
            return true;
        } else {
            LOGGER.info("Tweet creation failure " + tweetText);
            return false;
        }
    }

    public Tweet likesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user + " likes tweet " + tweet);
        tweet.getLikesList().add(user);
        tweetService.updateTweet(tweet);

        return tweet;
    }

    public Tweet dislikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user + " dislikes tweet " + tweet);
        tweet.getDislikesList().add(user);
        tweetService.updateTweet(tweet);

        return tweet;
    }

    public Tweet unlikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user + " unlikes tweet " + tweet);
        tweet.getLikesList().remove(user);
        tweetService.updateTweet(tweet);

        return tweet;
    }

    public Tweet undislikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user + " undislikes tweet " + tweet);
        tweet.getDislikesList().remove(user);
        tweetService.updateTweet(tweet);

        return tweet;
    }
}
