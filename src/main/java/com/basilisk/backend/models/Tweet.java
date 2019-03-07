package com.basilisk.backend.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tweet implements Cloneable {

    // Fields

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "TWEET_LIKES",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likesList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "TWEET_DISLIKES",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> dislikesList = new ArrayList<>();


    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    @Column
    private final Instant creationTime = Instant.now();

    // Constructors

    public Tweet() {
    }

    public Tweet(String text, User user) {
        this.text = text;
        this.user = user;
    }

    //Getters and Setters

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

    public List<User> getDislikesList() {
        return dislikesList;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    // Overridden Methods

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", likes=" + likesList.size() +
                ", dislikes=" + dislikesList.size() +
                ", user=" + user +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
