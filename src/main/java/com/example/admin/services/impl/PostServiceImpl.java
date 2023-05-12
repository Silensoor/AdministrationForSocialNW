package com.example.admin.services.impl;

import com.example.admin.model.*;
import com.example.admin.model.repository.*;
import com.example.admin.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BlockHistoryRepository blockHistoryRepository;
    private final Post2TagRepository post2TagRepository;
    private final PostFilesRepository postFilesRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        List<Tag> tagsByPostId = post2TagRepository.findTagsByPostId(id);
        post2TagRepository.deletePost2TagByPostId(id);
        tagsByPostId.forEach(b->post2TagRepository.deletePost2TagByPostId(b.getId()));
        List<BlockHistory> blocksByPostId = blockHistoryRepository.findBlocksByPostId(id);
        blocksByPostId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));
        List<Comment> byPostId = commentRepository.findByPostId(id);
        for (Comment comment : byPostId) {
            List<BlockHistory> blocksByCommentId = blockHistoryRepository.findBlocksByCommentId(comment.getId());
            blocksByCommentId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));
            commentRepository.delete(comment);
        }
        List<PostFiles> postFilesByPostId = postFilesRepository.findPostFilesByPostId(id);
        postFilesByPostId.forEach(d -> postFilesRepository.deletePostFileById(d.getId()));
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findPost(String text) {
        List<Post> all = postRepository.findAll();
        List<Post> result = new ArrayList<>();
        for(Post post: all){
            Pattern pattern = Pattern.compile(text);
            String postText = post.getPostText();
            Matcher matcher = pattern.matcher(post.getPostText());
            if(matcher.find()){
                result.add(post);
            }
        }
        return result;
    }
}
