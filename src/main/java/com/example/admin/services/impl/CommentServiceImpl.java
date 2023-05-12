package com.example.admin.services.impl;

import com.example.admin.model.BlockHistory;
import com.example.admin.model.Comment;
import com.example.admin.model.Post;
import com.example.admin.model.repository.BlockHistoryRepository;
import com.example.admin.model.repository.CommentRepository;
import com.example.admin.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BlockHistoryRepository blockHistoryRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteComment(Long id) {
        List<BlockHistory> blocksByPostId = blockHistoryRepository.findBlocksByCommentId(id);
        blocksByPostId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findComments(String text) {
        List<Comment> all = commentRepository.findAll();
        List<Comment> result = new ArrayList<>();
        for(Comment comment: all){
            Pattern pattern = Pattern.compile(text);
            String commentText = comment.getCommentText();
            Matcher matcher = pattern.matcher(commentText);
            if(matcher.find()){
                result.add(comment);
            }
        }
        return result;
    }

}
