package com.basilisk.backend.models;

import javax.persistence.*;

@Entity
public class Tweet {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @Column
    private int likes;

    @Column
    private int dislikes;

    @Column
    private int retweets;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    public Tweet() {
    }

    public Tweet(String text, int likes, int dislikes, int retweets, User user) {
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
        this.retweets = retweets;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getRetweets() {
        return retweets;
    }

    public User getUser() {
        return user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
