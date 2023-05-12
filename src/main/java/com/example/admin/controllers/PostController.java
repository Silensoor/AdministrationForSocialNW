package com.example.admin.controllers;

import com.example.admin.model.Post;
import com.example.admin.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/delete")
    public String deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return "redirect:/post";
    }

    @PostMapping("/find")
    public String findPost(@RequestParam(value = "text", required = false) String text, Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("postFind", postService.findPost(text));
        return "post.html";
    }

    @PostMapping("/deleteFind")
    public String deletePostFind(@RequestParam("id") Long id,Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        postService.deletePost(id);
        return "post.html";
    }

}
