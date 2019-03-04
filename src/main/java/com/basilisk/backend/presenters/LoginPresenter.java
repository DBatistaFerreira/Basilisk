package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.UserService;
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
            LOGGER.info("Login Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Login Failure : Username = " + username);
            return false;
        }


    }

    public User getUser(String userName) {
        return userService.getUserByUsername(userName);
    }

    //Added sign up method
    public boolean signupUser(String name, String username, String password) {
        LOGGER.info("Signup Attempt: Username = " + username);

        if (name.length() == 0 || username.length() == 0 || password.length() == 0) {
            return false;
        }

        User user = userService.getUserByUsername(username);
        if (Objects.isNull(user)) {
            user = new User(name, username, password, "", null);
            userService.createNewUser(user);
            LOGGER.info("Signup Success : Username = " + username);
            return true;
        } else {
            LOGGER.info("Signup Failure : Username = " + username);
            return false;
        }

    }
}