package com.basilisk;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.ProfilePresenter;
import com.basilisk.backend.repositories.FollowRepository;
import com.basilisk.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
public class ProfileTests extends Tests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ProfilePresenter profilePresenter;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void followUserTest() {
        // Create current user (currentUser) and user to be followed (userToFollow)
        User currentUser = new User();
        User userToFollow = new User();

        // Initialize current user: currentUser
        currentUser.setName("Follower Sheep");
        currentUser.setUsername("xXHerdSheepXx");
        currentUser.setPassword("sheeplife1234");

        // Initialize user to be followed: userToFollow
        userToFollow.setName("Alfa Wulf");
        userToFollow.setUsername("LoanWolf69");
        userToFollow.setPassword("w0lfp4ck_h0000wl");

        // Save current user and user to be followed to the user repository
        userRepository.save(currentUser);
        userRepository.save(userToFollow);

        // Test the followUser function from profilePresenter by making currentUser follow userToFollow
        profilePresenter.followUser(currentUser, userToFollow);

        // Verify that the followUser function worked by checking to see if the follow entry was created in the follow repository
        assertNotNull(followRepository.getByFollowerAndAndFollowed(currentUser, userToFollow));
    }

    @Test
    public void unfollowUserTest() {
        // Create current user (currentUser) and user to be unfollowed (userToUnfollow)
        User currentUser = new User();
        User userToUnfollow = new User();

        // Initialize current user: currentUser
        currentUser.setName("Gawn Sohlow");
        currentUser.setUsername("x_xStrivingToLeadx_x");
        currentUser.setPassword("only_follow_your_dreams");

        // Initialize user to be unfollowed: userToUnfollow
        userToUnfollow.setName("Ullweys Ab-Adond");
        userToUnfollow.setUsername("plzLIKEme");
        userToUnfollow.setPassword("dun_laff@me");

        // Save current user and user to be followed to the user repository
        userRepository.save(currentUser);
        userRepository.save(userToUnfollow);

        // Make the current user (currentUser) follow the user to be unfollowed (userToUnfollow)
        profilePresenter.followUser(currentUser, userToUnfollow);

        // Test the unfollowUser function from profilePresenter by making currentUser unfollow userToUnfollow
        profilePresenter.unfollowUser(currentUser, userToUnfollow);

        // Verify that the unfollowUser function worked by checking to see if the follow entry was deleted from the follow repository
        assertNull(followRepository.getByFollowerAndAndFollowed(currentUser, userToUnfollow));
    }

    @Test
    public void uploadNewProfilePictureTest() {
        User user = new User();
        userRepository.save(user);
        byte[] testBytes = {0, 1, 2};
        InputStream targetStream = new ByteArrayInputStream(testBytes);
        profilePresenter.uploadProfileImage(targetStream, user);
        assertNotNull(userRepository.getOne(user.getId()).getProfilePicture());
    }

    @Test
    public void uploadNewCoverPictureTest() {
        User user = new User();
        userRepository.save(user);
        byte[] testBytes = {0, 1, 2};
        InputStream targetStream = new ByteArrayInputStream(testBytes);
        profilePresenter.uploadCoverImage(targetStream, user);
        assertNotNull(userRepository.getOne(user.getId()).getCoverPicture());
    }
}
