package com.basilisk;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.MenuBarPresenter;
import com.basilisk.backend.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class SearchTests extends Tests {

    @Autowired
    private MenuBarPresenter menuBarPresenter;

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void GetAllUsersTest() {
        User testUser = new User("TestUser", "testUser", "password", "", null);

        /* New user created to test if menuBarPresenter.getAllUsers()
        returns a list containing that user */
        userService.createNewUser(testUser);

        assertDoesNotThrow(() -> {
            List<User> allUsers = menuBarPresenter.getAllUsers();
        });
        List<User> allUsers = menuBarPresenter.getAllUsers();

        assertNotNull(allUsers);

        assertTrue(allUsers.contains(testUser));

        userService.removeUser(testUser);
    }

    @Test
    public void searchByUsernameTest() {
        User testUser = new User("TestUser", "qwertyuiop", "password", "", null);

        /* Created user has a specific username to test if menuBarPresenter.searchByUsername()
        returns a list containing that user */
        userService.createNewUser(testUser);

        assertDoesNotThrow(() -> {
            List<User> allUsers = menuBarPresenter.searchByUsername("asdf");
        });
        // testUser must be in the list if its username contains the search term
        assertTrue(menuBarPresenter.searchByUsername("qwertyuiop").contains(testUser));
        assertTrue(menuBarPresenter.searchByUsername("ertyui").contains(testUser));

        // testUser won't be in the list otherwise
        assertFalse(menuBarPresenter.searchByUsername("asdfghjkl").contains(testUser));

        userService.removeUser(testUser);
    }

}
