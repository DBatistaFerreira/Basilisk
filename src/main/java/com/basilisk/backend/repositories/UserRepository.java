package com.basilisk.backend.repositories;

import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long>
{
    User findByUsername (String username);
}
