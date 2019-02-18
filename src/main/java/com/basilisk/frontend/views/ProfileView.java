package com.basilisk.frontend.views;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.ProfilePresenter;
import com.basilisk.backend.presenters.TweetPresenter;
import com.basilisk.frontend.components.MenuBarComponent;
import com.basilisk.frontend.components.TweetCreateComponent;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Tag("profile-view")
@HtmlImport("profile-view.html")
@Route("profile")
@Uses(MenuBarComponent.class)
public class ProfileView extends PolymerTemplate<ProfileView.ProfileViewModel> implements BeforeEnterObserver, HasUrlParameter<String> {

    private ProfilePresenter profilePresenter;
    private TweetPresenter tweetPresenter;
    private String usernameParameter;

    private static final String USER_PROFILE = "userProfile";
    private static final String CURRENT_USER = "currentUser";
    private static final String PROFILE_ROUTE = "profile/";

    @Id("tweetFeed")
    private Element tweetFeed;

    public ProfileView(ProfilePresenter profilePresenter, TweetPresenter tweetPresenter) {
        this.profilePresenter = profilePresenter;
        this.tweetPresenter = tweetPresenter;
    }

    public void init() {
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(CURRENT_USER);
        VaadinSession.getCurrent().setAttribute("currentPage", "profile");

        if (Objects.isNull(usernameParameter)) { //If route is just /profile then go to currentUser page
            usernameParameter = currentUser.getUsername();
            UI.getCurrent().navigate(PROFILE_ROUTE + usernameParameter);
        } else { //Else the route will be /profile/someUser
            User userProfile = profilePresenter.getUser(usernameParameter);
            if (Objects.isNull(userProfile)) {
                //TODO: Error page if user doesn't exist
                UI.getCurrent().navigate(PROFILE_ROUTE + currentUser.getUsername());
            }
            VaadinSession.getCurrent().setAttribute(USER_PROFILE, userProfile);
        }

        //Remove all children from tweetFeed so no duplicates
        tweetFeed.removeAllChildren();

        //Loading the tweets
        User userProfile = (User) VaadinSession.getCurrent().getAttribute(USER_PROFILE);
        List<TweetDisplayComponent> tweetDisplayComponentList = profilePresenter.getAllUserTweetsDisplayComponents(userProfile);
        Collections.reverse(tweetDisplayComponentList);

        //Only see the create tweet button on your own page
        if (userProfile.equals(currentUser)) {
            tweetFeed.appendChild(new TweetCreateComponent(tweetPresenter).getElement());
        }

        //Adding tweets to div element (tweetFeed)
        for (TweetDisplayComponent tweetDisplayComponent : tweetDisplayComponentList) {
            tweetFeed.appendChild(tweetDisplayComponent.getElement());
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        if (Objects.isNull(vaadinSession.getCurrent().getAttribute("currentUser"))) {
            beforeEnterEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
        }
        init();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        usernameParameter = parameter;

    }

    public interface ProfileViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
