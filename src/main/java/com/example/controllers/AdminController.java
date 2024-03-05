package com.example.controllers;


import com.example.model.Course;
import com.example.model.EazyClass;
import com.example.model.Person;
import com.example.repository.CourseRepository;
import com.example.repository.EazyClassRepository;
import com.example.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("admin")
public class AdminController {

    private final PersonRepository personRepository;
    private final EazyClassRepository eazyClassRepository;
    private final CourseRepository courseRepository;

    @RequestMapping(value = "/displayClasses")
    public ModelAndView displayClassesPage() {
        List<EazyClass> eazyClasses = eazyClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes");
        modelAndView.addObject("eazyClass", new EazyClass());
        modelAndView.addObject("eazyClasses", eazyClasses);
        return modelAndView;
    }

    @PostMapping(value = "/addNewClass")
    public ModelAndView addNewClass(@ModelAttribute("eazyClass") EazyClass eazyClass) {
        eazyClassRepository.save(eazyClass);
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam(name = "classId") int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage;
        ModelAndView modelAndView = new ModelAndView("students");
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);
        modelAndView.addObject("person", new Person());
        if (eazyClass.isPresent()) {
            modelAndView.addObject("eazyClass", eazyClass.get());
            session.setAttribute("eazyClass", eazyClass.get());
        }
        if (error != null) {
            errorMessage = "Invalid Email entered";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id) {
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        if (eazyClass.isPresent()) {
            for (Person person : eazyClass.get().getPersons()) {
                person.setEazyClass(null);
                personRepository.save(person);
            }
            eazyClassRepository.deleteById(id);
        }
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @PostMapping(value = "/addStudent")
    public ModelAndView addStudent(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId() +
                    "&error=true");
        } else {
            personEntity.setEazyClass(eazyClass);
            eazyClass.getPersons().add(personEntity);
            personRepository.save(personEntity);
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
        }
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(@RequestParam int personId, HttpSession session) {
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()) {
            person.get().setEazyClass(null);
            eazyClass.getPersons().remove(person.get());
            EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);
            session.setAttribute("eazyClass", eazyClassSaved);
        }
        return new ModelAndView("redirect:/admin/displayStudents?classId=" + eazyClass.getClassId());
    }

    @GetMapping(value = "/displayCourses")
    public ModelAndView displayCoursesPage() {
        //List<Course> courses = courseRepository.findAllByOrderByName();
        List<Course> courses = courseRepository.findAll(Sort.by("name").ascending());
        ModelAndView modelAndView = new ModelAndView("courses_secure");
        modelAndView.addObject("course", new Course());
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    @PostMapping(value = "/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return new ModelAndView("redirect:/admin/displayCourses");
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam(name = "id") int courseId, HttpSession session,
                                     @RequestParam(required = false) String error) {
        String errorMessage;
        ModelAndView modelAndView = new ModelAndView("course_students");
        Optional<Course> course = courseRepository.findById(courseId);
        modelAndView.addObject("person", new Person());
        if (course.isPresent()) {
            modelAndView.addObject("course", course.get());
            session.setAttribute("course", course.get());
        }
        if (error != null) {
            errorMessage = "Invalid Email entered";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping(value = "/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Course course = (Course) session.getAttribute("course");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId() +
                    "&error=true");
        } else {
            personEntity.getCourses().add(course);
            course.getPersons().add(personEntity);
            personRepository.save(personEntity);
            session.setAttribute("course", course);
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + course.getCourseId());
        }
        return modelAndView;
    }


    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId, HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()) {
            person.get().getCourses().remove(course);
            course.getPersons().remove(person.get());
            Course courseSaved = courseRepository.save(course);
            session.setAttribute("course", courseSaved);
        }
        return new ModelAndView("redirect:/admin/viewStudents?id=" + course.getCourseId());
    }
}
