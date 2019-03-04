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
    public void writeComment(Comment comment)
    {
        createComment(comment);
    }

    public void editComment(Comment comment)
    {
        updateComment(comment);
    }

    public Comment getCommentById(long id)
    {
        return retrieveComment(id);
    }

    public List<Comment> getTweetComments(Tweet tweet) {
        return commentRepository.getAllByTweet(tweet);
    }

    public void removeComment(Comment comment) {
        deleteComment(comment);
    }

    // private CRUD Operations
    private void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    private void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    private void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    private Comment retrieveComment(long id) {
        return commentRepository.getOne(id);
    }

}
