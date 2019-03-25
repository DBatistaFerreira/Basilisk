package com.basilisk.frontend.components;

import com.basilisk.Constants;
import com.basilisk.backend.models.Comment;
import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.TweetPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Tag("tweet-display-component")
@HtmlImport("tweet-display-component.html")
public class TweetDisplayComponent extends PolymerTemplate<TweetDisplayComponent.TweetDisplayComponentModel> {

    @Id("UserLink")
    private Anchor UserLink;
    @Id("profileImage")
    private Image profileImage;
    @Id("likeButton")
    private Button likeButton;
    @Id("dislikeButton")
    private Button dislikeButton;
    @Id("commentButton")
    private Button commentButton;
    @Id("showComments")
    private Button showComments;
    @Id("hideComments")
    private Button hideComments;
    @Id("retweetButton")
    private Button retweetButton;
    @Id("deleteButton")
    private Button deleteButton;
    @Id("tweetMessage")
    private TextArea tweetMessage;
    @Id("commentMessage")
    private TextArea commentMessage;
    @Id("commentSection")
    private VerticalLayout commentSection;
    @Id("commentDisplay")
    private VerticalLayout commentDisplay;
    @Id("retweetLabel")
    private TextArea retweetLabel;


    private static final String LIKE = "Like";
    private static final String DISLIKE = "Dislike";
    private static final String UN_LIKE = "Un-like";
    private static final String UN_DISLIKE = "Un-dislike";
    private static final String SHOW = "Show Comments";
    private static final String HIDE = "Hide Comments";
    private static final String BACKGROUND = "background";
    private static final String GREY = "Grey";
    private static final String HEX314654 = "#314654";

    private TweetPresenter tweetPresenter;
    private Tweet tweet;
    private Retweet retweet;
    private boolean isRetweet;
    private boolean isCommentHidden;
    private boolean isCommentShown;
    private int numberOfShownComments;
    private int showCommentsClickedCounter;
    private User retweetedBy;
    private Instant timeStamp;

    // Constructors

    // Tweet constructor
    public TweetDisplayComponent(TweetPresenter tweetPresenter, Tweet tweet) {
        this.tweetPresenter = tweetPresenter;
        this.tweet = tweet;
        this.isRetweet = false;
        this.timeStamp = tweet.getCreationTime();

        setTweet();
    }

    // Retweet constructor
    public TweetDisplayComponent(TweetPresenter tweetPresenter, Retweet retweet) {
        this.tweetPresenter = tweetPresenter;
        this.tweet = retweet.getTweet();
        this.retweet = retweet;
        this.isRetweet = true;
        this.retweetedBy = retweet.getUser();
        this.timeStamp = retweet.getCreationTime();

        setTweet();
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    private void setTweet() {
        byte[] profileImageData = tweet.getUser().getProfilePicture();
        if (Objects.isNull(profileImageData)) {
            profileImage.setSrc("frontend/defaultProfileImage.jpg");
        } else {
            StreamResource profilePictureResource = new StreamResource(tweet.getUser().getUsername() + ".jpg", () -> new ByteArrayInputStream(profileImageData));
            profileImage.setSrc(profilePictureResource);
        }

        UserLink.setText(tweet.getUser().getName() + " (@" + tweet.getUser().getUsername() + ")");
        UserLink.setHref(Constants.PROFILE_ROUTE + tweet.getUser().getUsername());

        tweetMessage.setValue(tweet.getText() + "\n-" + tweet.getUser().getUsername());
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        User currentUser = (User) vaadinSession.getAttribute(Constants.CURRENT_USER);

        if (isRetweet) {
            retweetLabel.setValue("Retweeted by " + retweetedBy.getUsername());
            if (!currentUser.equals(retweetedBy))
                deleteButton.setVisible(false);
        } else {
            retweetLabel.setVisible(false);
            if (!currentUser.equals(tweet.getUser()))
                deleteButton.setVisible(false);
        }

        if (tweet.getLikesList().contains(currentUser)) {
            likeButton.setText(UN_LIKE + " " + tweet.getLikesList().size());
            likeButton.getStyle().set(BACKGROUND, GREY);
        } else {
            likeButton.setText(LIKE + " " + tweet.getLikesList().size());
        }

        if (tweet.getDislikesList().contains(currentUser)) {
            dislikeButton.setText(UN_DISLIKE + " " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set(BACKGROUND, GREY);
        } else {
            dislikeButton.setText(DISLIKE + " " + tweet.getDislikesList().size());
        }

        isCommentHidden = true;
        isCommentShown = false;
        numberOfShownComments = 0;
        showCommentsClickedCounter = 1;

        //Add comments/text boxes to tweets if isCommentHidden set to false
        List<Comment> tweetComments = tweetPresenter.getTweetComments(tweet);
        if (tweetComments.size() > 0) {
            showComments.setText(SHOW + " (" + tweetComments.size() + ")");
            hideComments.setVisible(false);
        } else {
            showComments.setVisible(false);
            hideComments.setVisible(false);
        }
    }

    @EventHandler
    private void likeButtonClicked() {
        // Called when the like button is pressed
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        if (tweet.getLikesList().contains(currentUser)) { // If user already liked the tweet
            tweet = tweetPresenter.unlikesTweet(currentUser, tweet);
            likeButton.setText(LIKE + " " + tweet.getLikesList().size());
            likeButton.getStyle().set(BACKGROUND, HEX314654);
        } else { // If user didn't already dislike the tweet
            if (tweet.getDislikesList().contains(currentUser)) {
                // If user already disliked the tweet, undislike it.
                tweet = tweetPresenter.undislikesTweet(currentUser, tweet);
            }
            tweet = tweetPresenter.likesTweet(currentUser, tweet);
            likeButton.setText(UN_LIKE + " " + tweet.getLikesList().size());
            likeButton.getStyle().set(BACKGROUND, GREY);
            dislikeButton.setText(DISLIKE + " " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set(BACKGROUND, HEX314654);
        }
    }

    @EventHandler
    private void dislikeButtonClicked() {
        // Called when the like button is pressed
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        if (tweet.getDislikesList().contains(currentUser)) { // If user already disliked the tweet
            tweet = tweetPresenter.undislikesTweet(currentUser, tweet);
            dislikeButton.setText(DISLIKE + " " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set(BACKGROUND, HEX314654);
        } else { // If user didn't already like the tweet
            if (tweet.getLikesList().contains(currentUser)) {
                // If user already liked the tweet, unlike it.
                tweet = tweetPresenter.unlikesTweet(currentUser, tweet);
            }
            tweet = tweetPresenter.dislikesTweet(currentUser, tweet);
            dislikeButton.setText(UN_DISLIKE + " " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set(BACKGROUND, GREY);
            likeButton.setText(LIKE + " " + tweet.getLikesList().size());
            likeButton.getStyle().set(BACKGROUND, HEX314654);
        }
    }

    @EventHandler
    private void commentButtonClicked() {
        // Called from the template click handler
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        tweetPresenter.createAndSaveComment(commentMessage.getValue(), tweet, currentUser);
        UI.getCurrent().getPage().reload();
    }

    @EventHandler
    private void showCommentsClicked() {
        // Called from the template click handler
        List<Comment> tweetComments = tweetPresenter.getTweetComments(tweet);
        isCommentHidden = false;
        isCommentShown = true;
        for (; numberOfShownComments < tweetComments.size() && numberOfShownComments < 5 * showCommentsClickedCounter; ++numberOfShownComments) {
            CommentDisplayComponent wComment = new CommentDisplayComponent(tweetComments.get(numberOfShownComments));
            commentDisplay.add(wComment);
        }

        hideComments.setText(HIDE + " (" + numberOfShownComments + ")");
        if (showCommentsClickedCounter == 1 && numberOfShownComments > 0) {
            hideComments.setVisible(true);
            commentButton.getStyle().set("left", "283px");
        }

        if (tweetComments.size() - numberOfShownComments <= 0) {
            showComments.setVisible(false);
        } else {
            showComments.setText(SHOW + " (" + (tweetComments.size() - numberOfShownComments) + ")");
            ++showCommentsClickedCounter;
        }
    }

    @EventHandler
    private void hideCommentsClicked() {
        List<Comment> tweetComments = tweetPresenter.getTweetComments(tweet);
        if (numberOfShownComments > 0) {
            if (isCommentShown == true) {
                isCommentHidden = true;
                isCommentShown = false;
                hideComments.setText(HIDE + " (" + tweetComments.size() + ")");
                commentDisplay.removeAll();

                numberOfShownComments = 0;
                showCommentsClickedCounter = 1;
                hideComments.setVisible(false);
                commentButton.getStyle().set("left", "450px");

                showComments.setVisible(true);
                showComments.setText(SHOW + " (" + (tweetComments.size() - numberOfShownComments) + ")");
            }
        } else {
            hideComments.setVisible(false);
        }
    }

    @EventHandler
    private void retweetButtonClicked() {
        // Called from the template click handler
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        tweetPresenter.createAndSaveRetweet(tweet, currentUser);
        UI.getCurrent().getPage().reload();
    }

    @EventHandler
    private void deleteButtonClicked() {
        // Called when the delete button is clicked
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        if (isRetweet) {
            if (currentUser.equals(retweetedBy))
                tweetPresenter.deleteRetweet(retweet);
        } else {
            if (currentUser.equals(tweet.getUser()))
                tweetPresenter.deleteTweet(tweet);
        }
        UI.getCurrent().getPage().reload();
    }

    public interface TweetDisplayComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }

    public static class TweetDisplayComponentByTimeStampComparator implements Comparator<TweetDisplayComponent> {

        @Override
        public int compare(TweetDisplayComponent comp1, TweetDisplayComponent comp2) {
            return -comp1.getTimeStamp().compareTo(comp2.getTimeStamp());
        }
    }
}
