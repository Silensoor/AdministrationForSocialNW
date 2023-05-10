package com.example.admin.controllers;


import com.example.admin.dto.request.PersonRq;
import com.example.admin.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor

public class PersonController {


    private final PersonService personService;

    @PostMapping("/create")
    public String createPeopleBySettings(PersonRq personRq)  {
        personService.createPersonBySettings(personRq);
        return "redirect:/people";
    }

    @PostMapping("/creates")
    public String createPeople() {
        personService.createPerson();
        return "redirect:/people";
    }
    @PostMapping("/clear")
    public String deleteCreatePersons(@RequestParam(value = "id",required = false) Long id){
        personService.delete(id);
        return "redirect:/people";
    }
    @PostMapping("/register")
    public String addUser(Integer count){
        personService.registerUser(count);
        return "redirect:/people";
    }
    @PostMapping("/change")
    public String changeUser(PersonRq personRq){
        personService.changeUser(personRq);
        return "redirect:/people";
    }
}
