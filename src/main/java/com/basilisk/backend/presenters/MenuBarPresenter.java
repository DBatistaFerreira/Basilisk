package com.basilisk.backend.presenters;

import com.basilisk.backend.models.User;
import com.basilisk.backend.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuBarPresenter {
    private UserService userService;

    private static Logger LOGGER = Logger.getLogger(MenuBarPresenter.class);

    @Autowired
    public MenuBarPresenter(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    public List<User> getSelectedUsers(String name) {
        return userService.searchForUsers(name);
    }
}
