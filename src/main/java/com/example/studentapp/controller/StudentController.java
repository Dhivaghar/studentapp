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

        model.addAttribute("student", new Student());

        return "add";
    }

    // SAVE STUDENT
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {

        if(!Validator.isValidEmail(student.getEmail())) {
            return "redirect:/students/add";
        }

        service.saveStudent(student);

        return "redirect:/students";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {

        service.deleteStudent(id);

        return "redirect:/students";
    }

    // REST API GET
    @ResponseBody
    @GetMapping("/api")
    public List<Student> getStudentsApi() {

        return service.getAllStudents();
    }

    // REST API POST
    @ResponseBody
    @PostMapping("/api")
    public Response createStudentApi(@RequestBody Student student) {

        Response response = new Response();

        if(!Validator.isValidName(student.getName())) {

            response.setMessage("Invalid Name");

            return response;
        }

        service.saveStudent(student);

        response.setMessage("Student Saved Successfully");

        return response;
    }
    
 
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {

        service.saveStudent(student);

        return "redirect:/students";
    }
    
    @ResponseBody
    @GetMapping("/search")
    public String searchStudent(
            @RequestParam String name) {

        return "Searching student: " + name;
    }
    
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {

        Student student = service.getStudentById(id);

        model.addAttribute("student", student);

        return "edit";
    }
}