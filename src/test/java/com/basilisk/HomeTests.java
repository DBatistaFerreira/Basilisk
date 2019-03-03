package com.basilisk;

import com.basilisk.backend.presenters.HomePresenter;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HomeTests extends Tests {

    @Autowired
    private HomePresenter homePresenter;

    @BeforeAll
    public static void setUp() {

    }
}
