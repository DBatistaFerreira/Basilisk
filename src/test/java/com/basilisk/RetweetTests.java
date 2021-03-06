package com.basilisk;

import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.TweetPresenter;
import com.basilisk.backend.services.RetweetService;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class RetweetTests extends Tests {

    @Autowired
    public UserService userService;

    @Autowired
    public TweetPresenter tweetPresenter;

    @Autowired
    public TweetService tweetService;

    @Autowired
    public RetweetService retweetService;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void RetweetingOtherUsersTweetTest() {

        // create user1 to post tweet
        User user1 = new User();
        user1.setName("TestName1");
        user1.setUsername("TestUser1");
        user1.setPassword("TestPassword1");

        // create user2 to retweet tweet
        User user2 = new User();
        user2.setName("TestName1");
        user2.setUsername("TestUser1");
        user2.setPassword("TestPassword1");

        userService.createNewUser(user1);
        userService.createNewUser(user2);

        // create tweet
        tweetPresenter.createAndSaveTweet("Retweet test 1", user1);
        Tweet tweet = tweetService.getAllTweetsByUser(user1).get(0);

        // retweet the tweet
        long retweetID = tweetPresenter.createAndSaveRetweet(tweet, user2);

        // validate
        Retweet retweet = retweetService.getRetweetById(retweetID);
        assertNotNull(retweet);
        assertEquals(retweet.getUser(), user2);
    }

    @Test
    public void RetweetingYourOwnTweetTest() {

        // create user
        User user = new User();
        user.setName("TestName");
        user.setUsername("TestUser");
        user.setPassword("TestPassword");

        userService.createNewUser(user);

        // create tweet
        tweetPresenter.createAndSaveTweet("Retweet test 2", user);
        Tweet tweet = tweetService.getAllTweetsByUser(user).get(0);

        // retweet the tweet
        long retweetID = tweetPresenter.createAndSaveRetweet(tweet, user);

        // validate
        Retweet retweet = retweetService.getRetweetById(retweetID);
        assertNotNull(retweet);
        assertEquals(retweet.getUser(), user);
    }

}
