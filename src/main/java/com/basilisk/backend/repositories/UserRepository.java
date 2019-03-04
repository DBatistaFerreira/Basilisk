package com.basilisk.backend.repositories;

import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    User findByUsernameIgnoreCaseAndPassword(String username, String password);
}
