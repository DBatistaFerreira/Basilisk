package com.basilisk.backend.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class User implements Cloneable {

    // Fields

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String biography;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] profilePicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] coverPicture;

    @Column
    private final Instant creationTime = Instant.now();

    // Constructors

    public User() {
    }

    public User(String name, String username, String password, String biography, byte[] profilePicture) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.biography = biography;
        this.profilePicture = profilePicture;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public byte[] getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(byte[] coverPicture) {
        this.coverPicture = coverPicture;
    }

    // Overridden Methods

    @Override
    public String toString() {
        return name + " @" + username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;

        return this.id == user.id && this.name.equals(user.name) && this.username.equals(user.username);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


