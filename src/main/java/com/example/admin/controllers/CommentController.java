package com.example.admin.controllers;

import com.example.admin.model.PostComment;
import com.example.admin.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public void deleteComment(@RequestParam("id")Long id){
        commentService.deleteComment(id);
    }
    @PostMapping("/find1")
    public String findPost(@RequestParam(value = "text", required = false) String text, Model model) {
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("commentsFind", commentService.findComments(text));
        return "comment.html";
    }
    @PostMapping("/commentFind")
    public String deletePostFind(@RequestParam("id") Long id,Model model) {
        commentService.deleteComment(id);
        model.addAttribute("comments", commentService.findAll());
        return "comment.html";
    }
}
