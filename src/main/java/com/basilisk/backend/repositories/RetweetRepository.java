package com.basilisk.backend.repositories;

import com.basilisk.backend.models.Retweet;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    List<Retweet> getAllByUser(User user);

    List<Retweet> getAllByTweet(Tweet tweet);

}
