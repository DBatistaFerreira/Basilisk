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

    public void newUser(User user) {
        createUser(user);
    }

    public void registerNewUser(User user) {
        createUser(user);
    }

    public void saveUserInfo(User currentUser) {
        updateUser(currentUser);
    }

    // private CRUD Operations
    private void createUser(User user) {
        userRepository.save(user);
    }

    private User retrieveUserByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
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
