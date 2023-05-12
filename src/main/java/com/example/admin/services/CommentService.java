package com.example.admin.services;

import com.example.admin.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();
    void deleteComment(Long id);

    List<Comment> findComments(String text);
}
