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
            UI.getCurrent().getPage().reload();
            LOGGER.info("Login Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Login Failure : Username = " + username);
            return false;
        }


    }
}