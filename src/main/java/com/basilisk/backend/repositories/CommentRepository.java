package com.basilisk.backend.repositories;

import com.basilisk.backend.models.Comment;
import com.basilisk.backend.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByTweet(Tweet tweet);
}
