package com.basilisk.frontend.components;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.TweetPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

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

    private TweetPresenter tweetPresenter;
    private Tweet tweet;

    public TweetDisplayComponent(TweetPresenter tweetPresenter) {
        // You can initialise any data required for the connected UI components here.
        this.tweetPresenter = tweetPresenter;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
        tweetMessage.setValue(tweet.getText() + "\n-" + ((User) VaadinSession.getCurrent().getAttribute("currentUser")).getUsername());

        VaadinSession vaadinSession = VaadinSession.getCurrent();
        User currentUser = (User) vaadinSession.getAttribute("currentUser");

        if (tweet.getLikesList().contains(currentUser)) {
            likeButton.setText("Un-Like " + tweet.getLikesList().size());
            likeButton.getStyle().set("background", "GREY");
        } else {
            likeButton.setText("Like " + tweet.getLikesList().size());
            likeButton.getStyle().set("background", "#314654");
        }

        if (tweet.getDislikesList().contains(currentUser)) {
            dislikeButton.setText("Un-Dislike " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set("background", "GREY");
        } else {
            dislikeButton.setText("Dislike " + tweet.getDislikesList().size());
        }
    }

    @EventHandler
    private void likeButtonClicked() {
        // Called when the like button is pressed
        User currentUser = (User) VaadinSession.getCurrent().getAttribute("currentUser");
        if (tweet.getLikesList().contains(currentUser)) { // If user already liked the tweet
            tweet = tweetPresenter.unlikesTweet(currentUser, tweet);
            likeButton.setText("Like " + tweet.getLikesList().size());
            likeButton.getStyle().set("background", "#314654");
        } else { // If user didn't already dislike the tweet
            if (tweet.getDislikesList().contains(currentUser)) {
                // If user already disliked the tweet, undislike it.
                tweet = tweetPresenter.undislikesTweet(currentUser, tweet);
            }
            tweet = tweetPresenter.likesTweet(currentUser, tweet);
            likeButton.setText("Un-Like " + tweet.getLikesList().size());
            likeButton.getStyle().set("background", "GREY");
            dislikeButton.setText("Dislike " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set("background", "#314654");
        }
    }

    @EventHandler
    private void dislikeButtonClicked() {
        // Called when the like button is pressed
        User currentUser = (User) VaadinSession.getCurrent().getAttribute("currentUser");
        if (tweet.getDislikesList().contains(currentUser)) { // If user already disliked the tweet
            tweet = tweetPresenter.undislikesTweet(currentUser, tweet);
            dislikeButton.setText("Dislike " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set("background", "#314654");
        } else { // If user didn't already like the tweet
            if (tweet.getLikesList().contains(currentUser)) {
                // If user already liked the tweet, unlike it.
                tweet = tweetPresenter.unlikesTweet(currentUser, tweet);
            }
            tweet = tweetPresenter.dislikesTweet(currentUser, tweet);
            dislikeButton.setText("Un-Dislike " + tweet.getDislikesList().size());
            dislikeButton.getStyle().set("background", "GREY");
            likeButton.setText("Like " + tweet.getLikesList().size());
            likeButton.getStyle().set("background", "#314654");
        }
    }

    @EventHandler
    private void commentButtonClicked() {
        // Called from the template click handler

    }

    @EventHandler
    private void retweetButtonClicked() {
        // Called from the template click handler
        System.out.println("Retweet");
    }

    public interface TweetDisplayComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
