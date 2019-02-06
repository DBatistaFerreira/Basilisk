package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.HomePresenter;
import com.vaadin.flow.component.Tag;;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("home-view")
@HtmlImport("home-view.html")
@Route("home")
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> {

    private HomePresenter homePresenter;

    public HomeView(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
        // You can initialise any data required for the connected UI components here.
    }

    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
