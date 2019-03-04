package com.basilisk;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.LoginPresenter;
import com.basilisk.backend.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class LoginTests extends Tests {

    @Autowired
    private LoginPresenter loginPresenter;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void successfulLoginTest() {
        //Create a user in the DB
        User user = new User();
        user.setName("TestName");
        user.setPassword("TestPass");
        user.setUsername("TestUser");

        userRepository.save(user);

        boolean isLoggedIn = loginPresenter.loginUser("TestUser", "TestPass");
        assertTrue(isLoggedIn);
    }

    @Test
    public void badLoginTest() {
        boolean isLoggedIn = loginPresenter.loginUser("TestBadUser", "TestPass");
        assertFalse(isLoggedIn);
    }

    @Test
    public void emptyLoginTest() {
        boolean isLoggedIn = loginPresenter.loginUser("", "");
        assertFalse(isLoggedIn);
    }

    @Test
    public void successfulSignUp() {
        loginPresenter.signupUser("TestName", "TestUser", "TestPass");
        User user = userRepository.findByUsername("TestUser");
        assertNotNull(user);
    }

    @Test
    public void userAlreadyExistsSignUp() {
        loginPresenter.signupUser("TestName", "TestUser", "TestPass");
        boolean isSignedUp = loginPresenter.signupUser("TestName", "TestUser", "TestPass");
        assertFalse(isSignedUp);
    }

    @Test
    public void emptySignUp() {
        boolean isSignedUp = loginPresenter.signupUser("", "", "TestPass");
        assertFalse(isSignedUp);
    }
}
