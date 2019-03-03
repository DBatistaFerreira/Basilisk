package com.basilisk;

import com.basilisk.backend.presenters.MenuBarPresenter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MenuBarTests extends Tests {

    @Autowired
    private MenuBarPresenter menuBarPresenter;

    @BeforeAll
    public static void setUp() {

    }
}


