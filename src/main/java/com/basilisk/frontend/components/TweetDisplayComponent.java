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
        tweetMessage.setReadOnly(true);
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
        tweetMessage.setValue(tweet.getText() + "\n-" + ((User) VaadinSession.getCurrent().getAttribute("currentUser")).getUsername());
        likeButton.setText("Like " + tweet.getLikes());
        dislikeButton.setText("Dislike " + 0);
    }

    @EventHandler
    private void likeButtonClicked() {
        // Called from the template click handler
        if (!likeButton.getText().contains("-")) {
            likeButton.setText("Un-Like " + tweet.getLikes());
        } else {
            likeButton.setText("Like " + tweet.getLikes());
        }
    }

    @EventHandler
    private void dislikeButtonClicked() {
        // Called from the template click handler
        if (!dislikeButton.getText().contains("-")) {
            dislikeButton.setText("Un-Dislike " + 0);
        } else {
            dislikeButton.setText("Dislike " + 0);
        }
    }

    @EventHandler
    private void commentButtonClicked() {
        // Called from the template click handler
        System.out.println("Comment");
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
