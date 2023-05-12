package com.example.admin.controllers;

import com.example.admin.dto.request.PersonForRegistration;
import com.example.admin.dto.request.PersonRq;
import com.example.admin.model.Person;
import com.example.admin.services.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
@RequiredArgsConstructor
public class MainController {
    private final Statistic statistic;
    private final GeolocationService geolocationService;
    private final PersonService personService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String getMain(Model model)  {
        model.addAttribute("statistics",statistic.getStatic());
        return "index.html";
    }
    @GetMapping("/people")
    public String getPeople(Model model){
        model.addAttribute("person",new PersonRq());
        model.addAttribute("countries",geolocationService.getAllCountry());
        model.addAttribute("cities",geolocationService.getAllCity());
        model.addAttribute("listPerson",personService.getListPerson());
        model.addAttribute("allPersons",personService.getAllPerson());
        model.addAttribute("personRegister",new PersonForRegistration());
        return "people.html";
    }
    @GetMapping("/post")
    public String getPost(Model model){
        model.addAttribute("posts", postService.getAllPosts());
        return "post.html";
    }
    @GetMapping("/comment")
    public String getComment(Model model){
        model.addAttribute("comments",commentService.findAll());
        return "comment.html";
    }

}
