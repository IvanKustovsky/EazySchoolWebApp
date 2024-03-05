package com.example.controllers;


import com.example.model.Address;
import com.example.model.Person;
import com.example.model.Profile;
import com.example.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller("profileControllerBean") // added to avoid ambiguity between data REST(it creates automatically 'profileController')
public class ProfileController {

    private final PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(HttpSession session) {
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        if(person.getAddress() !=null && person.getAddress().getAddressId()>0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String displayMessages(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                  HttpSession session) {
        if(errors.hasErrors()){
            log.error("Profile form update failed due to: " + errors);
            return "profile";
        } else {
            Person person = (Person) session.getAttribute("loggedInPerson");
            person.setName(profile.getName());
            person.setEmail(profile.getEmail());
            person.setMobileNumber(profile.getMobileNumber());
            if(person.getAddress() == null || !(person.getAddress().getAddressId() > 0)){
                person.setAddress(new Address());
            }
            person.getAddress().setAddress1(profile.getAddress1());
            person.getAddress().setAddress2(profile.getAddress2());
            person.getAddress().setCity(profile.getCity());
            person.getAddress().setState(profile.getState());
            person.getAddress().setZipCode(profile.getZipCode());

            personRepository.save(person);
            session.setAttribute("loggedInPerson", person);

            return "redirect:/displayProfile";
        }

    }
}
