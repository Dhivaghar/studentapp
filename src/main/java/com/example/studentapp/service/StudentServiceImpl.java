package com.example.studentapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository repo;

    @Override
    public Student saveStudent(Student student) {

        logger.info("Service - Saving student: {}", student.getName());

        return repo.save(student);
    }

    @Override
    public List<Student> getAllStudents() {

        logger.info("Service - Fetching all students");

        return repo.findAll();
    }

    @Override
    public void deleteStudent(int id) {

        logger.info("Service - Deleting student with id: {}", id);

        repo.deleteById(id);
    }

    @Override
    public Student getStudentById(int id) {

        logger.info("Service - Fetching student with id: {}", id);

        return repo.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(int id, Student student) {

        logger.info("Service - Updating student with id: {}", id);

        Student existing = repo.findById(id).orElse(null);

        if (existing == null) {

            logger.warn("Service - Student not found with id: {}", id);

            return null;
        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());

        return repo.save(existing);
    }

    @Override
    public List<Student> searchByName(String name) {

        logger.info("Service - Searching students by name: {}", name);

        return repo.findByNameContaining(name);
    }
}