package com.example.controllers;


import com.example.model.Person;
import com.example.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboardPage(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(person.getEazyClass() != null && person.getEazyClass().getName() != null){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        return "dashboard";
    }


}
