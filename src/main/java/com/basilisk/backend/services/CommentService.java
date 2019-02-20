package com.basilisk.backend.services;

import com.basilisk.backend.models.Comment;
import com.basilisk.backend.models.Tweet;
import com.basilisk.backend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    // Constructor
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Public Service methods
    public List<Comment> getTweetComments(Tweet tweet) {
        return commentRepository.getAllByTweet(tweet);
    }

    // private CRUD Operations
    private void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    private void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    private void updateTweet(Comment comment) {
        commentRepository.save(comment);
    }

    private Comment retrieveComment(long id) {
        return commentRepository.getOne(id);
    }

}
