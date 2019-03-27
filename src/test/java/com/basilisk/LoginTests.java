package com.basilisk;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.LoginPresenter;
import com.basilisk.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String hashedPassword = loginPresenter.passwordHash("TestPass");
        user.setPassword(hashedPassword);
        user.setUsername("TestUser");

        userRepository.save(user);

        boolean isLoggedIn = loginPresenter.loginUser("TestUser", "TestPass");
        assertTrue(isLoggedIn);
    }

    @Test
    public void badLoginTest() {
        String hashedPassword = loginPresenter.passwordHash("TestPass");
        boolean isLoggedIn = loginPresenter.loginUser("TestBadUser", hashedPassword);
        assertFalse(isLoggedIn);
    }

    @Test
    public void emptyLoginTest() {
        boolean isLoggedIn = loginPresenter.loginUser("", "");
        assertFalse(isLoggedIn);
    }

    @Test
    public void successfulSignUp() {
        String hashedPassword = loginPresenter.passwordHash("TestPass");
        loginPresenter.signupUser("TestName", "TestUser", hashedPassword);
        User user = userRepository.findByUsernameIgnoreCase("TestUser");
        assertNotNull(user);
    }

    @Test
    public void userAlreadyExistsSignUp() {
        String hashedPassword = loginPresenter.passwordHash("TestPass");
        loginPresenter.signupUser("TestName", "TestUser", hashedPassword);
        boolean isSignedUp = loginPresenter.signupUser("TestName", "TestUser", hashedPassword);
        assertFalse(isSignedUp);
    }

    @Test
    public void emptySignUp() {
        String hashedPassword = loginPresenter.passwordHash("TestPass");
        boolean isSignedUp = loginPresenter.signupUser("", "", hashedPassword);
        assertFalse(isSignedUp);
    }
}
