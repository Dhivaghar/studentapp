package com.example.studentapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repo;

    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    @Override
    public void deleteStudent(int id) {
        repo.deleteById(id);
    }
    
    @Override
    public Student getStudentById(int id) {

        return repo.findById(id).orElse(null);
    }
}