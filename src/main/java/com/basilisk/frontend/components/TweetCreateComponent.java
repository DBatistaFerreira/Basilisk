package com.basilisk.frontend.components;

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

    @Id("createButton")
    private Button likeButton;
    @Id("tweetMessage")
    private TextArea tweetMessage;

    //private TweetCreatePresenter tweetCreatePresenter;

    public TweetCreateComponent(/*TweetCreatePresenter tweetCreatePresenter*/) {
        // You can initialise any data required for the connected UI components here.
        //this.tweetCreatePresenter = tweetCreatePresenter;
    }

    @EventHandler
    private void createButtonClicked() {
        // Called from the template click handler
        System.out.println("CREATE");
    }


    public interface TweetCreateComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
