package com.basilisk.frontend.views;

import com.basilisk.Constants;
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
    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("loginButton")
    private Button loginButton;
    @Id("fullNameTextField")
    private TextField fullNameTextField;
    @Id("createUsernameTextField")
    private TextField createUsernameTextField;
    @Id("createPasswordTextField")
    private PasswordField createPasswordTextField;
    @Id("signUpButton")
    private Button signUpButton;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void init() {
        VaadinSession.getCurrent().setAttribute("currentPage", "login");
        loginButton.addClickListener(buttonClickEvent -> {
            String userName = usernameTextField.getValue(); // Valid value: Admin
            String password = passwordTextField.getValue(); // Valid value: password

            if (loginPresenter.loginUser(userName, password)) {
                VaadinSession.getCurrent().setAttribute(Constants.CURRENT_USER, loginPresenter.getUser(userName));
                UI.getCurrent().navigate(Constants.HOME_ROUTE);
                passwordTextField.setInvalid(false);
            } else {
                passwordTextField.setInvalid(true);
                passwordTextField.setErrorMessage("The username and password you entered do not match our records. Please double-check and try again.");
            }
        });

        //Setting the maximum length of characters for each input field in the sign up section.
        fullNameTextField.setMaxLength(30);
        fullNameTextField.setRequired(true);

        createUsernameTextField.setMaxLength(15);
        createUsernameTextField.setRequired(true);
        //The pattern says: no whitespace at any character spot
        createUsernameTextField.setPattern("\\S*");
        createUsernameTextField.setPreventInvalidInput(true);

        createPasswordTextField.setMaxLength(15);
        createPasswordTextField.setRequired(true);

        signUpButton.addClickListener(buttonClickEvent -> {
            String fullName = fullNameTextField.getValue();
            String newUserName = createUsernameTextField.getValue();
            String newPassword = createPasswordTextField.getValue();

            //Create a new account
            if (loginPresenter.signupUser(fullName, newUserName, newPassword)) {
                VaadinSession.getCurrent().setAttribute("currentUser", loginPresenter.getUser(newUserName));
                UI.getCurrent().navigate("profile");
                createUsernameTextField.setInvalid(false);
            } else {

                if (fullName.length() == 0 || newUserName.length() == 0 || newPassword.length() == 0) {
                    if (fullName.length() == 0) {
                        fullNameTextField.setInvalid(true);
                        fullNameTextField.setErrorMessage("Please enter your full name.");
                    }

                    if (newUserName.length() == 0) {
                        createUsernameTextField.setInvalid(true);
                        createUsernameTextField.setErrorMessage("Please enter a username.");
                    }

                    if (newPassword.length() == 0) {
                        createPasswordTextField.setInvalid(true);
                        createPasswordTextField.setErrorMessage("Please enter a password.");
                    }
                } else {
                    createUsernameTextField.setInvalid(true);
                    createUsernameTextField.setErrorMessage("Username has already been taken! Please choose another one.");
                }
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!Objects.isNull(VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER))) {
            beforeEnterEvent.rerouteTo(HomeView.class);
            UI.getCurrent().navigate(Constants.HOME_ROUTE);
        } else {
            init();
        }
    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
