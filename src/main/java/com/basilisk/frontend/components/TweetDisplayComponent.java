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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Tag("tweet-display-component")
@HtmlImport("tweet-display-component.html")
public class TweetDisplayComponent extends PolymerTemplate<TweetDisplayComponent.TweetDisplayComponentModel> {

    @Id("likeButton")
    private Button likeButton;
    @Id("dislikeButton")
    private Button dislikeButton;
    @Id("commentButton")
    private Button commentButton;
    @Id("retweetButton")
    private Button retweetButton;
    @Id("tweetMessage")
    private TextArea tweetMessage;
    @Id("commentMessage")
    private TextArea commentMessage;
    @Id("commentSection")
    private VerticalLayout commentSection;
    @Id("retweetLabel")
    private TextArea retweetLabel;

    private static final String LIKE = "Like";
    private static final String DISLIKE = "Dislike";
    private static final String UN_LIKE = "Un-like";
    private static final String UN_DISLIKE = "Un-dislike";
    private static final String BACKGROUND = "background";
    private static final String GREY = "Grey";
    private static final String HEX314654 = "#314654";

    private TweetPresenter tweetPresenter;
    private Tweet tweet;
    private boolean isRetweet;
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
        this.isRetweet = true;
        this.retweetedBy = retweet.getUser();
        this.timeStamp = retweet.getCreationTime();

        setTweet();
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    private void setTweet() {
        tweetMessage.setValue(tweet.getText() + "\n-" + tweet.getUser().getUsername());
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        User currentUser = (User) vaadinSession.getAttribute(Constants.CURRENT_USER);

        if (isRetweet) {
            retweetLabel.setValue("Retweeted by " + retweetedBy.getUsername());
        }
        else {
            retweetLabel.setVisible(false);
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

        //Add comments/text boxes to tweets
        List<Comment> tweetComments = tweetPresenter.getTweetComments(tweet);
        for (Comment comment : tweetComments) {
            CommentDisplayComponent wComment = new CommentDisplayComponent(comment);
            commentSection.add(wComment);
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
        //tweet =
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        tweetPresenter.createAndSaveComment(commentMessage.getValue(), tweet, currentUser);
        UI.getCurrent().getPage().reload();
    }

    @EventHandler
    private void retweetButtonClicked() {
        // Called from the template click handler
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        tweetPresenter.createAndSaveRetweet(tweet, currentUser);
        UI.getCurrent().getPage().reload();
    }

    public interface TweetDisplayComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }

    public static class TweetDisplayComponentByTimeStampComparator implements Comparator<TweetDisplayComponent> {

        @Override
        public int compare(TweetDisplayComponent comp1, TweetDisplayComponent comp2) {
            return comp1.getTimeStamp().compareTo(comp2.getTimeStamp());
        }
    }
}
