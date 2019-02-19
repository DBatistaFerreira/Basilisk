package com.basilisk.backend.services;


import com.basilisk.backend.models.User;
import com.basilisk.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    // Constructor
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Public Service methods
    public User login(String username, String password) {
        return retrieveUserByUsernameAndPassword(username, password);
    }

    public List<User> searchForUsers(String username) {
        return retrieveUserByUsernameContainingIgnoreCase(username);
    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public User retrieveUserByUserName(String username) {
        return retrieveUserByUsername(username);
    }

    //Added 2019-02-18 to be used by Login Presenter
    public void newUser(User user) {
        createUser(user);
    }

    // private CRUD Operations for UserService which call the user repository
    private void createUser(User user) {
        userRepository.save(user);
    }

    //changed the 'b' in 'by' to capital 'B' 2019-02-18 @ 5:30 am
    private User retrieveUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private User retrieveUserByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    private List<User> retrieveUserByUsernameContainingIgnoreCase(String searchTerm) {
        return userRepository.findAllByUsernameContainingIgnoreCase(searchTerm);
    }

    private void updateUser(User user) {
        userRepository.save(user);
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }

}
