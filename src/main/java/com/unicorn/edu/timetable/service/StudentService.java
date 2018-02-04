package com.unicorn.edu.timetable.service;

import com.unicorn.edu.timetable.repository.entity.Student;

import java.util.List;

public interface StudentService {
    void printTimeTable();

    void printFreeTime();

    void printAll();

    void printBySubject();

    void add();

    void edit();

    void activate();

    List<Student> readStudents(boolean deactivated);

    Student readStudent(boolean deactivated);
}
