package com.example.studentapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.studentapp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNameContaining(String name);
}