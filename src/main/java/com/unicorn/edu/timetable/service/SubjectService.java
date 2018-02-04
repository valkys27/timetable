package com.unicorn.edu.timetable.service;

import com.unicorn.edu.timetable.repository.entity.*;

public interface SubjectService {

    void printAll();

    void printByStudent();

    void add();

    void edit();

    void addToSubject();

    void removeFromSubject();

    Subject readSubject();

    void removeFromAllSubjects(Student student);
}
