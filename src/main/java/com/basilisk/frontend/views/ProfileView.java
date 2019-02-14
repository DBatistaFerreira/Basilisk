package com.basilisk.frontend.views;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.ProfilePresenter;
import com.basilisk.backend.presenters.TweetPresenter;
import com.basilisk.frontend.components.TweetCreateComponent;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Tag("profile-view")
@HtmlImport("profile-view.html")
@Route("profile")
public class ProfileView extends PolymerTemplate<ProfileView.ProfileViewModel> implements BeforeEnterObserver {

    private ProfilePresenter profilePresenter;

    @Id("tweetFeed")
    private Element tweetFeed;
    @Id("homeTab")
    private Tab homeTab;

    public ProfileView(ProfilePresenter profilePresenter, TweetPresenter tweetPresenter) {
        this.profilePresenter = profilePresenter;

        List<TweetDisplayComponent> tweetDisplayComponentList = profilePresenter.getAllUserTweets((User) VaadinSession.getCurrent().getAttribute("currentUser"));
        Collections.reverse(tweetDisplayComponentList);
        tweetFeed.appendChild(new TweetCreateComponent(tweetPresenter).getElement());

        for (TweetDisplayComponent tweetDisplayComponent : tweetDisplayComponentList) {
            tweetFeed.appendChild(tweetDisplayComponent.getElement());
        }

        homeTab.getElement().addEventListener("click", (event) -> {
            UI.getCurrent().navigate("home");
        });


        // You can initialise any data required for the connected UI components here.
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (Objects.isNull(VaadinSession.getCurrent().getAttribute("currentUser"))) {
            beforeEnterEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
        }
    }

    public interface ProfileViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
