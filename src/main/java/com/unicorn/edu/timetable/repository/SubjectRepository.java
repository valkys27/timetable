package com.unicorn.edu.timetable.repository;

import com.unicorn.edu.timetable.repository.entity.Student;
import com.unicorn.edu.timetable.repository.entity.Subject;

import java.util.List;

public interface SubjectRepository extends BaseRepository {

    List<Subject> getAll();

    List<Subject> getAll(List<Student> students);

    Subject findById(Long id);

    Subject findByAbrev(String abrev);
}
