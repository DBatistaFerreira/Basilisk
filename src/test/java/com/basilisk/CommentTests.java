package com.basilisk;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.TweetPresenter;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class CommentTests extends Tests {

    @Autowired
    private TweetPresenter tweetPresenter;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;


    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void emptyCommentTest(){
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create tweet to comment on
        Tweet tweet = new Tweet();
        tweet.setText("Test Tweet");
        tweetService.writeTweet(tweet);

        //Create new user
        userService.createNewUser(user);
        assertFalse(tweetPresenter.createAndSaveComment("", tweet, user));

    }

    @Test
    public void nonEmptyCommentTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create tweet to comment on
        Tweet tweet = new Tweet();
        tweet.setText("Test Tweet");
        tweetService.writeTweet(tweet);

        //Create new user
        userService.createNewUser(user);

        assertTrue(tweetPresenter.createAndSaveTweet("test", user));
        assertTrue(tweetPresenter.createAndSaveComment("Test Comment", tweet, user));
    }
}
