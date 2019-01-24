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

    public User (long m_id, String m_name, String m_username, String m_password)
    {
        this.id = m_id;
        this.name = m_name;
        this.username = m_username;
        this.password = m_password;
    }
}

