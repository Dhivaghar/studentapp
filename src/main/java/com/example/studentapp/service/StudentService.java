package com.example.studentapp.service;

import java.util.List;
import com.example.studentapp.model.Student;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    void deleteStudent(int id);
    
    Student getStudentById(int id);
}