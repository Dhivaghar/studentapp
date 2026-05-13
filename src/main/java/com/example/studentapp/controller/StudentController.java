package com.example.studentapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import com.example.studentapp.utils.Response;
import com.example.studentapp.utils.Validator;

@Controller
@RequestMapping("/students")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService service;

    // MVC HOME PAGE
    @GetMapping
    public String viewHome(Model model) {

        logger.info("Fetching all students");

        model.addAttribute("students", service.getAllStudents());

        return "index";
    }

    // ADD PAGE
    @GetMapping("/add")
    public String showForm(Model model) {

        logger.info("Showing add student form");

        model.addAttribute("student", new Student());

        return "add";
    }

    // SAVE STUDENT
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {

        logger.info("Saving student: {}", student.getName());

        if(!Validator.isValidEmail(student.getEmail())) {
            logger.warn("Invalid email: {}", student.getEmail());
            return "redirect:/students/add";
        }

        service.saveStudent(student);

        return "redirect:/students";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {

        logger.info("Deleting student with id: {}", id);

        service.deleteStudent(id);

        return "redirect:/students";
    }

    // REST API GET
    @ResponseBody
    @GetMapping("/api")
    public List<Student> getStudentsApi() {

        logger.info("API - Fetching all students");

        return service.getAllStudents();
    }

    // REST API POST
    @ResponseBody
    @PostMapping("/api")
    public Response createStudentApi(@RequestBody Student student) {

        logger.info("API - Creating student: {}", student.getName());

        Response response = new Response();

        if(!Validator.isValidName(student.getName())) {

            logger.warn("API - Invalid name: {}", student.getName());

            response.setMessage("Invalid Name");

            return response;
        }

        service.saveStudent(student);

        response.setMessage("Student Saved Successfully");

        return response;
    }
    
 
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {

        logger.info("Updating student: {}", student.getName());

        service.saveStudent(student);

        return "redirect:/students";
    }
    
    @ResponseBody
    @GetMapping("/search")
    public String searchStudent(
            @RequestParam String name) {

        logger.info("Searching student with name: {}", name);

        return "Searching student: " + name;
    }
    
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {

        logger.info("Editing student with id: {}", id);

        Student student = service.getStudentById(id);

        model.addAttribute("student", student);

        return "edit";
    }
}