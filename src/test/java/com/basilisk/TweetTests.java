package com.basilisk;

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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class TweetTests extends Tests {

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private RetweetService retweetService;

    @Autowired
    private TweetPresenter tweetPresenter;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void likeATweetWithNoDislikeTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);
        //Create a tweet and set its text
        Tweet tweet = new Tweet();
        tweet.setText("test");
        //Add tweet to the database
        tweetService.writeTweet(tweet);
        //Check to make sure there are no likes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getLikesList().size());
        //Like the tweet
        tweetPresenter.likesTweet(user, tweet);
        //Check to ensure the list went up a person
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getLikesList().size());
    }

    @Test
    public void dislikeATweetWithNoLikeTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);
        //Create a tweet and set its text
        Tweet tweet = new Tweet();
        tweet.setText("test");
        //Add tweet to the database
        tweetService.writeTweet(tweet);
        //Check to make sure there are no dislikes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
        //Dislike the tweet
        tweetPresenter.dislikesTweet(user, tweet);
        //Ensure the tweet as one dislike
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
    }

    @Test
    public void likeATweetThatIsAlreadyDislikedTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);
        //Create a tweet and set its text
        Tweet tweet = new Tweet();
        tweet.setText("test");
        //Add tweet to the database
        tweetService.writeTweet(tweet);
        //Check to make sure there are no dislikes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
        //dislike the tweet
        tweetPresenter.dislikesTweet(user, tweet);
        //ensure the tweet has one dislike
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
        //Make sure there are no likes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getLikesList().size());
        //like the tweet and undislike it
        tweetPresenter.likesTweet(user, tweet);
        tweetPresenter.undislikesTweet(user, tweet);
        //check that there is now one like
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getLikesList().size());
        //check that there is now one dislike
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
    }

    @Test
    public void dislikeATweetThatIsAlreadyLikedTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);
        //Create a tweet and set its text
        Tweet tweet = new Tweet();
        tweet.setText("test");
        //Add tweet to the database
        tweetService.writeTweet(tweet);
        //Check to make sure there are no likes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getLikesList().size());
        //Like the tweet
        tweetPresenter.likesTweet(user, tweet);
        //ensure that there is one like
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getLikesList().size());
        //ensure that there is no dislikes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
        //dislike a tweet and unlike it
        tweetPresenter.dislikesTweet(user, tweet);
        tweetPresenter.unlikesTweet(user, tweet);
        //Ensure that there is now one dislike
        assertEquals(1, tweetService.getTweetById(tweet.getId()).getDislikesList().size());
        //Ensure that there is no likes
        assertEquals(0, tweetService.getTweetById(tweet.getId()).getLikesList().size());
    }

    @Test
    public void postingANonEmptyTweetTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);

        assertTrue(tweetPresenter.createAndSaveTweet("test", user));
    }

    @Test
    public void postingAEmptyTweetTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);

        assertFalse(tweetPresenter.createAndSaveTweet("", user));
    }

    @Test
    public void ensureTweetDeletionDeletesAllCommentsAndRetweets() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        User user2 = new User();
        user.setName("TestName2");
        user.setPassword("TestPass2");
        user.setUsername("TestUsername2");

        //Create new user
        userService.createNewUser(user);

        //Create second new user
        userService.createNewUser(user2);

        tweetPresenter.createAndSaveTweet("test", user);
        tweetPresenter.createAndSaveRetweet(tweetService.getAllTweetsByUser(user).get(0), user2);

        tweetPresenter.deleteTweet(tweetService.getAllTweetsByUser(user).get(0));

        assertTrue(tweetService.getAllTweetsByUser(user).size() == 0);
        assertTrue(retweetService.getAllRetweetsByUser(user2).size() == 0);
    }

    @Test
    public void ensureRetweetDeletionDoesntDeleteOriginalTweet() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        User user2 = new User();
        user.setName("TestName2");
        user.setPassword("TestPass2");
        user.setUsername("TestUsername2");

        //Create new user
        userService.createNewUser(user);

        //Create second new year
        userService.createNewUser(user2);

        tweetPresenter.createAndSaveTweet("test", user);
        tweetPresenter.createAndSaveRetweet(tweetService.getAllTweetsByUser(user).get(0), user2);

        tweetPresenter.deleteRetweet(retweetService.getAllRetweetsByUser(user2).get(0));

        assertNotNull(tweetService.getAllTweetsByUser(user).get(0));
    }
}

