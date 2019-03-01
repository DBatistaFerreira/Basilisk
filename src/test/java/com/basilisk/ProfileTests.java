package com.basilisk;

import com.basilisk.backend.presenters.ProfilePresenter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileTests extends Tests {

    @Autowired
    private ProfilePresenter profilePresenter;

    @Before
    public void setUp() {

    }
}
