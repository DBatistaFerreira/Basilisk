package com.basilisk.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class LoginView extends VerticalLayout {
    private VerticalLayout loginWrapper;
    private VerticalLayout loginWrapperHalf;
    private VerticalLayout inputWrapper;
    private HorizontalLayout buttonWrapper;
    private Button loginButton;
    private Button signUpButton;
    private TextField userName;
    private PasswordField password;

    public LoginView() {
        this.setSizeFull();
        this.setSpacing(false);
        this.setMargin(false);

        //Wrapper for the Login
        loginWrapper = new VerticalLayout();
        loginWrapper.getStyle().set("border", "1px solid #9E9E9E");
        loginWrapper.setWidth("960px");
        loginWrapper.setHeight("1100px");
        loginWrapper.setSpacing(false);
        loginWrapper.setMargin(false);
        loginWrapper.setPadding(false);
        this.add(loginWrapper);
        this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, loginWrapper);

        //Wrapper for the second half of Login
        loginWrapperHalf = new VerticalLayout();
        loginWrapperHalf.getStyle().set("border", "1px solid #6E9E9E");
        loginWrapperHalf.setWidth("50%");
        loginWrapperHalf.setHeight("100%");
        loginWrapperHalf.setSpacing(true);

        loginWrapper.add(loginWrapperHalf);
        loginWrapperHalf.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //Wrapper for the text fields
        inputWrapper = new VerticalLayout();
        inputWrapper.setWidth("400px");
        inputWrapper.setSpacing(true);
        inputWrapper.setMargin(false);
        loginWrapperHalf.add(inputWrapper);
        loginWrapperHalf.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, inputWrapper);


        //Username Field
        userName = new TextField();
        userName.setWidth("250px");
        userName.setLabel("Username");
        userName.setPlaceholder("Enter your Username");
        userName.focus();

        //Password Field
        password = new PasswordField();
        password.setWidth("250px");
        password.setLabel("Password");
        password.setPlaceholder("Enter your Password");

        inputWrapper.add(userName);
        inputWrapper.add(password);
        loginWrapperHalf.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, inputWrapper);

        //Wrapper for the buttons
        buttonWrapper = new HorizontalLayout();
        buttonWrapper.setWidth("400px");
        buttonWrapper.setSpacing(true);
        buttonWrapper.setPadding(true);

        //Creating login and sign uo buttons
        loginButton = new Button("Login");
        signUpButton = new Button("Sign Up");

        buttonWrapper.add(loginButton, signUpButton);
        loginWrapperHalf.add(buttonWrapper);
        loginWrapperHalf.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, buttonWrapper);

    }
}
