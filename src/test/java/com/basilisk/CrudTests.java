package com.basilisk;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.*;
import com.basilisk.backend.services.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CrudTests extends Tests {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private RetweetService retweetService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private RetweetRepository retweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @BeforeAll
    public static void setUp() {
    }

    @Test
    public void createUserTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Create new user
        userService.createNewUser(user);

        //See if the user exists in the DB
        User retrievedUser = userRepository.getOne(user.getId());
        assertNotNull(retrievedUser);
    }

    @Test
    public void retrieveUserTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Save a user
        userRepository.save(user);

        //Retrieve that user
        User retrievedUser = userService.getUserByUsername("TestUsername");

        //See that the user is not null
        assertNotNull(retrievedUser);
    }

    @Test
    public void updateUserTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Save a user
        userRepository.save(user);

        User sameUser = userRepository.findByUsernameIgnoreCase("TestUsername");
        //Create a copy of sameUser because sameUser is the same in memory as user.
        User originalUser = null;
        try {
            originalUser = (User) sameUser.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        //Update the user
        user.setUsername("TestUserName2");
        user.setPassword("TestPass2");
        user.setName("TestName2");

        //Update db of the user
        userService.saveUserInfo(user);

        //Get that user
        User updatedUser = userRepository.getOne(user.getId());

        //See if the user does not equal the new user
        assertNotEquals(originalUser, updatedUser);
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUsername");

        //Save a new user
        userRepository.save(user);

        //Remove the user from the DB
        userService.removeUser(user);

        //Get that user
        User retrievedUser = userService.getUserByUsername("TestUsername");

        //Check if that user is null
        assertNull(retrievedUser);
    }

    @Test
    public void createTweetTest() {
        // Create current user
        User user = new User();

        // Initialize current user
        user.setName("Tweetie Burd");
        user.setUsername("heard_through_grapevine");
        user.setPassword("chirpie1337");

        // Save current user to the user repository
        userRepository.save(user);

        // Create tweet to be tweeted
        Tweet tweet = new Tweet("Happy Daze!", user);

        // Test create tweet in the tweet repository
        tweetService.writeTweet(tweet);

        // Verify that the tweet was successfully created in the repository by attempting to retrieve it
        Tweet createdTweet = null;
        try {
            createdTweet = tweetRepository.getOne(tweet.getId());
        } catch (JpaObjectRetrievalFailureException e) {
            e.printStackTrace();
        }

        // Verify the retrieved created tweet from the repository is not null
        assertNotNull(createdTweet);
    }

    @Test
    public void deleteTweetTest() {
        // Create current user
        User user = new User();

        // Initialize current user
        user.setName("deletie Burd");
        user.setUsername("picked_grapevine");
        user.setPassword("silence81");

        // Save current user to the user repository
        userRepository.save(user);

        // Create tweet to be tweeted
        Tweet tweet = new Tweet("SILENCE FOOLS!", user);

        // Create tweet in the tweet repository
        tweetService.writeTweet(tweet);

        // Test delete tweet from repository
        tweetService.removeTweet(tweet);

        // Verify that the tweet was successfully deleted from the repository by attempting to retrieve it
        Tweet deletedTweet = null;
        try {
            deletedTweet = tweetRepository.getOne(tweet.getId());
        } catch (JpaObjectRetrievalFailureException e) {
            //e.printStackTrace();
        }

        // Verify the retrieved deleted tweet from the repository is not there (null)
        assertNull(deletedTweet);
    }

    @Test
    public void retrieveTweetTest() {
        // Create current user
        User user = new User();

        // Initialize current user
        user.setName("Speedy Gonzales");
        user.setUsername("speedy_gonesalad");
        user.setPassword("riibariiba100");

        // Save current user to the user repository
        userRepository.save(user);

        // Create tweet to be tweeted
        Tweet tweet = new Tweet("catch me if you can!", user);

        // Create tweet in the tweet repository
        tweetService.writeTweet(tweet);

        // Test retrieve tweet was successfully retrieved from the repository
        Tweet retrievedTweet = null;
        try {
            retrievedTweet = tweetRepository.getOne(tweet.getId());
        } catch (JpaObjectRetrievalFailureException e) {
            e.printStackTrace();
        }

        // Verify the retrieved tweet from the repository is not null
        assertNotNull(retrievedTweet);
    }

    @Test
    public void updateTweetTest() {
        // Create current user
        User user = new User();

        // Initialize current user
        user.setName("Chenj Maeind");
        user.setUsername("nevaMIND");
        user.setPassword("notAnym0r3");

        // Save current user to the user repository
        userRepository.save(user);

        // Create tweet to be tweeted
        Tweet tweet = new Tweet("Hello World", user);

        // Create tweet in the tweet repository
        tweetService.writeTweet(tweet);

        // Make a clone of the original tweet before updating for comparison
        Tweet originalTweet = null;
        try {
            originalTweet = (Tweet) tweet.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertNotNull(originalTweet);

        String updatedTweetText = "Goodbye World";

        // Change the text of the tweet
        tweet.setText(updatedTweetText);

        // Test update tweet
        tweetService.editTweet(tweet);

        // Verify the updated tweet from the repository is different from the original tweet
        assertNotEquals(originalTweet.getText(), tweet.getText());
    }
}
