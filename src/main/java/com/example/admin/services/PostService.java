package com.example.admin.services;

import com.example.admin.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    void deletePost(Long id);
    List<Post> findPost(String text);
}
