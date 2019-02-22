package com.basilisk.backend.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Follow {

    // Fields

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn
    private User follower;

    @OneToOne
    @JoinColumn
    private User followed;

    // Constructors

    public Follow() {
    }

    public Follow(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    // Getters and Setters


    public long getId() {
        return id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    // Overridden Methods

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", follower=" + follower +
                ", followed=" + followed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return id == follow.id &&
                Objects.equals(follower, follow.follower) &&
                Objects.equals(followed, follow.followed);
    }
}
