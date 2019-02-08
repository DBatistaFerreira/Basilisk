package com.basilisk.frontend.components;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("tweet-component")
@HtmlImport("tweet-component.html")
public class TweetComponentDisplay extends PolymerTemplate<TweetComponentDisplay.TweetComponentDsiplayModel> {

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

    public TweetComponentDisplay() {
        // You can initialise any data required for the connected UI components here.
    }

    public interface TweetComponentDsiplayModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
