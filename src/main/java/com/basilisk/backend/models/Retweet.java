package com.basilisk.backend.models;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Retweet {

    // Fields

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    @OneToOne(targetEntity = Tweet.class)
    @JoinColumn(name = "TWEET")
    private Tweet tweet;

    @Column
    private final Instant creationTime = Instant.now();

    // Constructors

    public Retweet() {
    }

    public Retweet(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

    //Getters and Setters

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    // Overridden Methods

    @Override
    public String toString() {
        return "Retweet{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Retweet)) return false;
        Retweet retweet = (Retweet) o;
        return id == retweet.id &&
                Objects.equals(user, retweet.user) &&
                Objects.equals(tweet, retweet.tweet) &&
                Objects.equals(creationTime, retweet.creationTime);
    }

}
