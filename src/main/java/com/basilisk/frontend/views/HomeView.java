package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.HomePresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Objects;

@Tag("home-view")
@HtmlImport("home-view.html")
@Route("home")
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> implements BeforeEnterObserver {

    private HomePresenter homePresenter;

    public HomeView(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
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

    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
