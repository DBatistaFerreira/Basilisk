package com.basilisk.backend.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class FriendList {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private int numberOfFriends;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "USER")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "FRIENDS")
    private List<User> friendList;

    public FriendList() {
    }

    public FriendList(long id, int numberOfFriends, User user, List<User> friendList) {
        this.numberOfFriends = numberOfFriends;
        this.user = user;
        this.friendList = friendList;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getNumberOfFriends() {
        return numberOfFriends;
    }

    public User getUser() {
        return user;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setNumberOfFriends(int numberOfFriends) {
        this.numberOfFriends = numberOfFriends;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }
}