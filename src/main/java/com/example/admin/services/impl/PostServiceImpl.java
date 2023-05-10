package com.example.admin.services.impl;

import com.example.admin.model.Post;
import com.example.admin.model.repository.PostRepository;
import com.example.admin.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
