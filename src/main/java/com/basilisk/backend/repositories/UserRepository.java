package com.basilisk.backend.repositories;

import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUsernameContaining(String username);

    User findByUsernameAndPassword(String username, String password);
}
