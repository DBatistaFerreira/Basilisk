package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginPresenter {

    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginPresenter.class);

    @Autowired
    public LoginPresenter(UserService userService) {
        this.userService = userService;
    }

    public void loginUser(User user) {
        LOGGER.info("Login username: " + user.getUsername());
        LOGGER.info("Login password: " + user.getPassword());
    }
}