package com.basilisk.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class FriendList
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private int numberOfFriends;
    private User user;
    private List<User> friendList;

    public FriendList(long id,int numberOfFriends,User user,List<User> friendList)
    {
        this.numberOfFriends = numberOfFriends;
        this.user = user;
        this.friendList = friendList;
        this.id = id;
    }
}