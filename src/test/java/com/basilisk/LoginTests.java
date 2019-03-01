package com.basilisk;

import com.basilisk.backend.presenters.LoginPresenter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginTests extends Tests {

    @Autowired
    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {

    }
}
