package com.basilisk.backend.models;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Tweet
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String text;
    private int likes;
    private int retweets;
    private User user;

    public Tweet(String text, int likes, int retweets, User user)
    {
        this.text = text;
        this.likes = likes;
        this.retweets = retweets;
        this.user = user;
    }
}
