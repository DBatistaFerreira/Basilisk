package com.basilisk.frontend.views;

import com.basilisk.backend.presenters.LoginPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("login-view")
@HtmlImport("frontend://login-view.html")
@Route("")
public class LoginView extends PolymerTemplate<LoginView.LoginViewModel> {

    private LoginPresenter loginPresenter;
    @Id("div")
    private Element div;
    @Id("div1")
    private Element div1;
    @Id("h2")
    private H2 h2;
    @Id("div2")
    private Element div2;
    @Id("vaadinButton")
    private Button vaadinButton;
    @Id("div3")
    private Element div3;
    @Id("h21")
    private H2 h21;
    @Id("div4")
    private Element div4;
    @Id("username")
    private TextField username;
    @Id("password")
    private PasswordField password;
    @Id("vaadinButton1")
    private Button vaadinButton1;
   /* @Id("div5")
    private Element div5;
    @Id("ironIcon")
    private Element ironIcon;
    @Id("p")
    private Paragraph p;*/

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        // You can initialise any data required for the connected UI components here.
    }

    public interface LoginViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
