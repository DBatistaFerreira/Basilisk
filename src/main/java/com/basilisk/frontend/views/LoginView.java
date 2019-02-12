package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.LoginPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Objects;

@Tag("login-view")
@HtmlImport("frontend://login-view.html")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> implements BeforeEnterObserver {

    private LoginPresenter loginPresenter;
    @Id("signUpButton")
    private Button signUpButton;
    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("loginButton")
    private Button loginButton;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        // You can initialise any data required for the connected UI components here.
        loginButton.addClickListener(buttonClickEvent -> {
            String userName = usernameTextField.getValue(); // Valid value: Admin
            String password = passwordTextField.getValue(); // Valid value: password

            if (loginPresenter.loginUser(userName, password)) {
                passwordTextField.setInvalid(false);
            } else {
                passwordTextField.setInvalid(true);
                passwordTextField.setErrorMessage("The username and password you entered do not match our records. Please double-check and try again.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!Objects.isNull(VaadinSession.getCurrent().getAttribute("currentUser"))) {
            beforeEnterEvent.rerouteTo(HomeView.class);
            UI.getCurrent().navigate("home");
        }
    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
