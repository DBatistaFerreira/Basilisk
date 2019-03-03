package com.basilisk.frontend.views;

import com.basilisk.Constants;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.HomePresenter;
import com.basilisk.frontend.components.MenuBarComponent;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;
import java.util.Objects;

@Tag("home-view")
@HtmlImport("home-view.html")
@Route("home")
@Uses(MenuBarComponent.class)
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> implements BeforeEnterObserver {

    private HomePresenter homePresenter;
    @Id("tweetLayout")
    private VerticalLayout tweetLayout;

    public HomeView(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    public void init() {
        // Reset the tweets on the page
        tweetLayout.removeAll();

        VaadinSession.getCurrent().setAttribute("currentPage", "home");

        // Get all tweets of user and user's followings
        User userHome = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        List<TweetDisplayComponent> tweetDisplayComponentList = homePresenter.getAllHomePageTweetsDisplayComponents(userHome);

        // Displaying tweets on page
        for (TweetDisplayComponent tweetDisplayComponent : tweetDisplayComponentList) {
            tweetLayout.add(tweetDisplayComponent);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        if (Objects.isNull(vaadinSession.getAttribute(Constants.CURRENT_USER))) {
            beforeEnterEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
        }
        init();
    }

    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
