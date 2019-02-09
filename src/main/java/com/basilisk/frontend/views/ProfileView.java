package com.basilisk.frontend.views;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.ProfilePresenter;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;
import java.util.Objects;

@Tag("profile-view")
@HtmlImport("profile-view.html")
@Route("profile")
public class ProfileView extends PolymerTemplate<ProfileView.ProfileViewModel> implements BeforeEnterObserver {

    private ProfilePresenter profilePresenter;

    @Id("tweetDisplaySpot")
    private VerticalLayout verticalLayout;
    @Id("tweetFeed")
    private Element tweetFeed;

    public ProfileView(ProfilePresenter profilePresenter) {
        this.profilePresenter = profilePresenter;

        List<TweetDisplayComponent> tweetDisplayComponentList = profilePresenter.getAllUserTweets((User) VaadinSession.getCurrent().getAttribute("currentUser"));

        for (TweetDisplayComponent tweetDisplayComponent : tweetDisplayComponentList) {
            verticalLayout.addComponentAsFirst(tweetDisplayComponent);
        }

        // You can initialise any data required for the connected UI components here.
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        if(Objects.isNull(vaadinSession.getAttribute("currentUser"))) {
            UI.getCurrent().navigate("");
            UI.getCurrent().getPage().reload();
        }
    }

    public interface ProfileViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
