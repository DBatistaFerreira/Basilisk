package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.ProfilePresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("profile-view")
@HtmlImport("profile-view.html")
@Route("profile")
public class ProfileView extends PolymerTemplate<ProfileView.ProfileViewModel> {

    private ProfilePresenter profilePresenter;

    public ProfileView(ProfilePresenter profilePresenter) {
        this.profilePresenter = profilePresenter;
        // You can initialise any data required for the connected UI components here.
    }

    public interface ProfileViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
