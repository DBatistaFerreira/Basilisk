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

    public User retrieveUserByUserName(String username) {
        return retrieveUserbyUsername(username);
    }

    // private CRUD Operations for UserService which call the user repository
    private void createUser(User user) {
        userRepository.save(user);
    }

    private User retrieveUserbyUsername(String username) {
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
