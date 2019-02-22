package com.basilisk.backend.repositories;

import com.basilisk.backend.models.Follow;
import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Returns all x for which x follows y
    // In other words, returns all followers of user
    List<Follow> getAllByFollowed(User user);

    // Returns all y for which x follows y
    // In other words, returns all followings of user
    List<Follow> getAllByFollower(User user);
}
