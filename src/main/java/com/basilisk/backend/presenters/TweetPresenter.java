package com.basilisk.backend.presenters;

import com.basilisk.Constants;
import com.basilisk.backend.models.Comment;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.services.CommentService;
import com.basilisk.backend.services.TweetService;
import com.basilisk.backend.services.UserService;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TweetPresenter {

    private UserService userService;
    private TweetService tweetService;
    private CommentService commentService;

    private static Logger LOGGER = Logger.getLogger(ProfilePresenter.class);

    public TweetPresenter(UserService userService, TweetService tweetService, CommentService commentService)
    {
        this.userService = userService;
        this.tweetService = tweetService;
        this.commentService = commentService;
    }

    public boolean createAndSaveTweet(String tweetText) {
        if (!tweetText.isEmpty()) {
            User user = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
            Tweet tweet = new Tweet(tweetText, user);
            LOGGER.info("Tweet creation success: " + tweetText);
            tweetService.writeTweet(tweet);
            return true;
        } else {
            LOGGER.info("Tweet creation failure " + tweetText);
            return false;
        }
    }

    public boolean createAndSaveComment(String commentText, Tweet tweet)
    {
        if (!commentText.isEmpty())
        {
            User user = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
            Comment comment = new Comment(commentText, user, tweet);
            LOGGER.info("Comment creation success: " + commentText);
            LOGGER.info("Comment: " + comment.getText() + comment.getUser().toString() + comment.getTweet().toString());
            commentService.writeComment(comment);
            return true;
        } else
        {
            LOGGER.info("Comment creation failure " + commentText);
            return false;
        }
    }

    public List<Comment> getTweetComments(Tweet tweet)
    {
        return commentService.getTweetComments(tweet);
    }


    public Tweet likesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user.getUsername() + " likes tweet " + tweet.getId());
        tweet.getLikesList().add(user);
        tweetService.editTweet(tweet);

        return tweetService.getTweetById(tweet.getId());
    }

    public Tweet dislikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user.getUsername() + " dislikes tweet " + tweet.getId());
        tweet.getDislikesList().add(user);
        tweetService.editTweet(tweet);

        return tweetService.getTweetById(tweet.getId());
    }

    public Tweet unlikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user.getUsername() + " unlikes tweet " + tweet.getId());
        tweet.getLikesList().remove(user);
        tweetService.editTweet(tweet);

        return tweetService.getTweetById(tweet.getId());
    }

    public Tweet undislikesTweet(User user, Tweet tweet) {
        LOGGER.info("User " + user.getUsername() + " undislikes tweet " + tweet.getId());
        tweet.getDislikesList().remove(user);
        tweetService.editTweet(tweet);

        return tweetService.getTweetById(tweet.getId());
    }
}
