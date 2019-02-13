package com.basilisk.backend.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tweet {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @OneToMany(targetEntity = Tweet.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "LIKES_LIST")
    private List<User> likesList;

    @OneToMany(targetEntity = Tweet.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "DISLIKES_LIST")
    private List<User> dislikesList;

    @Column
    private int retweets;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    public Tweet() {
    }

    public Tweet(String text, List<User> likesList, List<User> dislikesList, int retweets, User user) {
        this.text = text;
        this.likesList = likesList;
        this.dislikesList = dislikesList;
        this.retweets = retweets;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<User> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<User> likesList) {
        this.likesList = likesList;
    }

    public List<User> getDislikesList() {
        return dislikesList;
    }

    public void setDislikesList(List<User> dislikesList) {
        this.dislikesList = dislikesList;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
