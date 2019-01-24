package com.basilisk.backend.repositories;

import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TweetRepository extends JpaRepository<Tweet, Long>
{
    List<Tweet> getAllByUser(User user);
}
