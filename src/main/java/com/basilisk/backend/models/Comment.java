package com.basilisk.backend.models;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String text;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    @OneToOne(targetEntity = Tweet.class)
    @JoinColumn(name = "TWEET")
    private Tweet tweet;

    @Column
    private final Instant creationTime = Instant.now();

    public Comment() {
    }

    public Comment(String text, User user, Tweet tweet) {
        this.text = text;
        this.user = user;
        this.tweet = tweet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                Objects.equals(text, comment.text) &&
                Objects.equals(user, comment.user) &&
                Objects.equals(creationTime, comment.creationTime);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
