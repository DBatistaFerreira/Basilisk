package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.LoginPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
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
            vaadinTextField.setValue("Clicked!");
        });

        //todo: add this line of code to the login presenter login method if user is found
        //VaadinSession.getCurrent().setAttribute("currentUser", user);
    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
