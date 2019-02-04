package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.LoginPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("login-view")
@HtmlImport("frontend://login-view.html")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> {

    private LoginPresenter loginPresenter;
    @Id("singUpButton")
    private Button singUpButton;
    @Id("username")
    private TextField username;
    @Id("password")
    private PasswordField password;
    @Id("loginButton")
    private Button loginButton;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        // You can initialise any data required for the connected UI components here.
    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
