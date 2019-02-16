package com.basilisk.frontend.views;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.HomePresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
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
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> implements BeforeEnterObserver {

    private HomePresenter homePresenter;

    @Id("searchField")
    private TextField searchField;

    @Id("searchButton")
    private Button searchButton;

    @Id("reloadButton")
    private Button reloadButton;

    @Id("logoutButton")
    private Button logoutButton;

    @Id("profileTab")
    private Tab profileTab;

    public HomeView(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
        // You can initialise any data required for the connected UI components here.
        profileTab.getElement().addEventListener("click", (event) -> {
            UI.getCurrent().navigate("profile");
        });

        searchButton.addClickListener(buttonClickEvent -> {
            String query = searchField.getValue();
            List<User> users = homePresenter.searchForUser(query);
            // The search is successful and returns the users whose username contains the query.
            // TODO generate a visual list of users that can be clicked.

        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        if(Objects.isNull(vaadinSession.getAttribute("currentUser"))) {
            beforeEnterEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
        }
    }

    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
