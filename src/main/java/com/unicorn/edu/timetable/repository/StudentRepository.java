package com.unicorn.edu.timetable.repository;

import com.unicorn.edu.timetable.repository.entity.Student;

import java.util.List;

public interface StudentRepository extends BaseRepository {

    List<Student> getAll();

    Student findById(Long id);

    Student findByUsername(String username);
}
