package com.example.studentapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import com.example.studentapp.utils.Response;
import com.example.studentapp.utils.Validator;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    @Autowired
    private StudentService service;

    // GET ALL STUDENTS
    @GetMapping
    public List<Student> getAllStudents() {

        logger.info("REST API - Fetching all students");

        return service.getAllStudents();
    }

    // GET STUDENT BY ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {

        logger.info("REST API - Fetching student with id: {}", id);

        return service.getStudentById(id);
    }

    // SEARCH STUDENT BY NAME USING @RequestParam
    @GetMapping("/search")
    public List<Student> searchStudent(@RequestParam String name) {

        logger.info("REST API - Searching students with name: {}", name);

        return service.searchByName(name);
    }

    // CREATE STUDENT
    @PostMapping
    public Response createStudent(@RequestBody Student student) {

        logger.info("REST API - Creating student: {}", student.getName());

        Response response = new Response();

        if (!Validator.isValidName(student.getName())) {

            logger.warn("REST API - Invalid name: {}", student.getName());

            response.setMessage("Invalid Name - must be at least 2 characters");

            return response;
        }

        if (!Validator.isValidEmail(student.getEmail())) {

            logger.warn("REST API - Invalid email: {}", student.getEmail());

            response.setMessage("Invalid Email - must contain @");

            return response;
        }

        service.saveStudent(student);

        logger.info("REST API - Student created successfully: {}", student.getName());

        response.setMessage("Student Created Successfully");

        return response;
    }

    // UPDATE STUDENT
    @PutMapping("/{id}")
    public Response updateStudent(@PathVariable int id, @RequestBody Student student) {

        logger.info("REST API - Updating student with id: {}", id);

        Response response = new Response();

        if (!Validator.isValidName(student.getName())) {

            logger.warn("REST API - Invalid name for update: {}", student.getName());

            response.setMessage("Invalid Name - must be at least 2 characters");

            return response;
        }

        if (!Validator.isValidEmail(student.getEmail())) {

            logger.warn("REST API - Invalid email for update: {}", student.getEmail());

            response.setMessage("Invalid Email - must contain @");

            return response;
        }

        Student updated = service.updateStudent(id, student);

        if (updated == null) {

            logger.warn("REST API - Student not found with id: {}", id);

            response.setMessage("Student Not Found with ID: " + id);

            return response;
        }

        logger.info("REST API - Student updated successfully with id: {}", id);

        response.setMessage("Student Updated Successfully");

        return response;
    }

    // DELETE STUDENT
    @DeleteMapping("/{id}")
    public Response deleteStudent(@PathVariable int id) {

        logger.info("REST API - Deleting student with id: {}", id);

        Response response = new Response();

        Student existing = service.getStudentById(id);

        if (existing == null) {

            logger.warn("REST API - Student not found for deletion with id: {}", id);

            response.setMessage("Student Not Found with ID: " + id);

            return response;
        }

        service.deleteStudent(id);

        logger.info("REST API - Student deleted successfully with id: {}", id);

        response.setMessage("Student Deleted Successfully");

        return response;
    }
}
