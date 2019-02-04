package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.LoginPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("login-view")
@HtmlImport("frontend://login-view.html")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> {

    @Id("vaadinTextField")
    private TextField vaadinTextField;

    @Id("vaadinButton")
    private Button vaadinButton;

    private LoginPresenter loginPresenter;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        // You can initialise any data required for the connected UI components here.
        vaadinButton.addClickListener(buttonClickEvent -> {

            // TODO Retrieve username/password from text fields
            String userName = "Adminn";
            String password = "password";

            if (loginPresenter.loginUser(userName, password)) {
                vaadinTextField.setLabel("Login Successful");
            } else {
                // TODO set behavior for wrong login
                vaadinTextField.setLabel("Login Unsuccessful");
            }
        });

    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
