package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginPresenter {

    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginPresenter.class);

    @Autowired
    public LoginPresenter(UserService userService) {
        this.userService = userService;
    }

    public boolean loginUser(String username, String password) {
        LOGGER.info("Login Attempt: Username = " + username);

        User user = userService.login(username, password);
        if (!Objects.isNull(user)) {
            VaadinSession.getCurrent().setAttribute("currentUser", user);
            UI.getCurrent().navigate("home");
            LOGGER.info("Login Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Login Failure : Username = " + username);
            return false;
        }


    }

    //Added sign up presenter
    public boolean signupUser(String name, String username, String password) {
        LOGGER.info("Signup Attempt: Username = " + username);

        User user = userService.retrieveUserByUserName(username);
        if (Objects.isNull(user)) {
            user = new User(name, username, password);
            userService.newUser(user);
            LOGGER.info("Signup Success : Username = " + username);
            VaadinSession.getCurrent().setAttribute("currentUser", user);
            UI.getCurrent().navigate("profile");
            LOGGER.info("Login Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Signup Failure : Username = " + username);
            return false;
        }

    }
}