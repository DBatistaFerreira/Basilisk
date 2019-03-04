package com.basilisk.backend.repositories;

import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameContainingIgnoreCase(String username);

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    User findByUsernameAndPassword(String username, String password);
}
