package com.basilisk.frontend.components;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.presenters.TweetDisplayPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("tweet-create-component")
@HtmlImport("tweet-create-component.html")
public class TweetCreateComponent extends PolymerTemplate<TweetCreateComponent.TweetCreateComponentModel> {

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

    private TweetDisplayPresenter tweetDisplayPresenter;
    private Tweet tweet;

    public TweetCreateComponent(TweetDisplayPresenter tweetDisplayPresenter) {
        // You can initialise any data required for the connected UI components here.
        this.tweetDisplayPresenter = tweetDisplayPresenter;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    @EventHandler
    private void likeButtonClicked() {
        // Called from the template click handler
        if (likeButton.getText().equals("Like")) {
            likeButton.setText("Un-Like");
        } else {
            likeButton.setText("Like");
        }
    }

    @EventHandler
    private void dislikeButtonClicked() {
        // Called from the template click handler
        if (likeButton.getText().equals("Dislike")) {
            likeButton.setText("Un-Dislike");
        } else {
            likeButton.setText("Dislike");
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

    public interface TweetCreateComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
