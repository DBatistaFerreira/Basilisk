package com.basilisk;

import com.basilisk.backend.presenters.TweetPresenter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TweetTests extends Tests {

    @Autowired
    private TweetPresenter tweetPresenter;

    @Before
    public void setUp() {

    }
}
