package com.basilisk;

import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.*;
import com.basilisk.backend.services.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        userService.registerNewUser(user);

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
        User retrievedUser = userService.getUser("TestUsername");

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

        User sameUser = userRepository.findByUsername("TestUsername");
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
        User retrievedUser = userService.getUser("TestUsername");

        //Check if that user is null
        assertNull(retrievedUser);
    }
}
