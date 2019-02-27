package com.basilisk.backend.presenters;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.FollowService;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class ProfilePresenter {

    private UserService userService;
    private TweetService tweetService;
    private FollowService followService;
    private TweetPresenter tweetPresenter;
    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    @Autowired
    public ProfilePresenter(UserService userService, TweetService tweetService, TweetPresenter tweetPresenter, FollowService followService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.tweetPresenter = tweetPresenter;
        this.followService = followService;
    }

    public List<TweetDisplayComponent> getAllUserTweetsDisplayComponents(User user) {
        List<Tweet> tweetList = tweetService.getAllTweetsByUser(user);
        List<TweetDisplayComponent> tweetComponentDisplayList = new LinkedList<>();

        for (Tweet tweet : tweetList) {
            TweetDisplayComponent tweetDisplayComponent = new TweetDisplayComponent(tweetPresenter);
            tweetDisplayComponent.setTweet(tweet);
            tweetComponentDisplayList.add(tweetDisplayComponent);
        }
        return tweetComponentDisplayList;
    }

    public User getUser(String username) {
        LOGGER.info("Retrieving user: " + username);
        User user = userService.retrieveUserByUserName(username);
        LOGGER.info("Retrieved user: " + username);
        return user;
    }

    public void saveBiography(User currentUser) {
        userService.saveUserInfo(currentUser);
        LOGGER.info("Saved Biography for: " + currentUser.getName());
    }

    public int getNumberOfFollowings(User currentUser) {
        return followService.getAllUserFollowings(currentUser).size();
    }

    public int getNumberOfFollowers(User currentUser) {
        return followService.getAllUserFollowers(currentUser).size();
    }

    public void setImages(Image profilePicture, Image coverPicture, User userProfile) {
        byte[] profileImageData = userProfile.getProfilePicture();
        byte[] coverImageData = userProfile.getCoverPicture();

        if (Objects.isNull(profileImageData)) {
            profilePicture.setSrc("frontend/defaultProfileImage.jpg");
        } else {
            StreamResource profilePictureResource = new StreamResource(userProfile.getUsername() + ".jpg", () -> new ByteArrayInputStream(profileImageData));
            profilePicture.setSrc(profilePictureResource);
        }

        if (Objects.isNull(coverImageData)) {
            coverPicture.setSrc("frontend/defaultCoverImage.jpg");
        } else {
            StreamResource coverPictureResource = new StreamResource(userProfile.getUsername() + ".jpg", () -> new ByteArrayInputStream(coverImageData));
            coverPicture.setSrc(coverPictureResource);
        }
    }

    public void uploadProfileImage(InputStream inputStream, User currentUser) {
        LOGGER.info("Uploading Profile Image");
        byte[] profileBytes = null;
        try {
            profileBytes = getImageBytes(inputStream);
        } catch (IOException e) {
            LOGGER.info("Failed to Upload Profile Image");
        }
        currentUser.setProfilePicture(profileBytes);
        userService.saveUserInfo(currentUser);
    }

    public void uploadCoverImage(InputStream inputStream, User currentUser) {
        LOGGER.info("Uploading Cover Image");
        byte[] coverBytes = null;
        try {
            coverBytes = getImageBytes(inputStream);
        } catch (IOException e) {
            LOGGER.info("Failed to Upload Cover Image");
        }
        currentUser.setCoverPicture(coverBytes);
        userService.saveUserInfo(currentUser);
    }

    private byte[] getImageBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inputStreamToByteArray(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void inputStreamToByteArray(InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        int nRead;
        byte[] data = new byte[inputStream.available()];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            byteArrayOutputStream.write(data, 0, nRead);
        }
    }

    public void followUser(User currentUser, User userProfile) {
        followService.follow(currentUser, userProfile);
        LOGGER.info(currentUser.getUsername() + " has just followed " + userProfile.getUsername());
    }

    public void unfollowUser(User currentUser, User userProfile) {
        followService.unfollow(currentUser, userProfile);
        LOGGER.info(currentUser.getUsername() + " has just unfollowed " + userProfile.getUsername());
    }

    public boolean checkIfFollowing(User currentUser, User userProfile) {
        if (followService.getAllUserFollowers(userProfile).contains(currentUser)) {
            return true;
        }
        return false;
    }
}