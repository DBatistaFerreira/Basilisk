package com.basilisk.backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String name;
    private String username;
    private String password;

    public User (long id, String name, String username, String password)
    {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}

