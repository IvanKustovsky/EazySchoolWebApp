package com.example.controllers;

import com.example.model.Person;
import com.example.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("public")
public class PublicController {

    private final PersonService personService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String displayRegisterPage(Model model){
        model.addAttribute("person", new Person());
        return "register";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String displayRegisterPage(@Valid @ModelAttribute("person") Person person, Errors errors){
        if(errors.hasErrors()){
            log.error("Person form validation failed due to: " + errors);
            return "register";
        }
        boolean isSaved = personService.createNewPerson(person);
        return (isSaved) ? "redirect:/login?register=true" : "register";
    }
}
