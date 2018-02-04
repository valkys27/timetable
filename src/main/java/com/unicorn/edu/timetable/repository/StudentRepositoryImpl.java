package com.unicorn.edu.timetable.repository;

import com.unicorn.edu.timetable.repository.entity.Student;
import com.unicorn.edu.timetable.service.ServiceLocator;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

public class StudentRepositoryImpl extends BaseRepositoryImpl implements StudentRepository {
    @Override
    public List<Student> getAll() {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        return entityManager.createQuery("from Student ", Student.class).getResultList();
    }

    @Override
    public Student findById(Long id) {
        return ServiceLocator.createEntityManager().find(Student.class, id);
    }

    @Override
    public Student findByUsername(String username) {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);

        Root<Student> studentRoot = cq.from(Student.class);

        cq.select(studentRoot).where(cb.equal(studentRoot.get("username"), username));

        List<Student> students = entityManager.createQuery(cq).getResultList();
        return (students.isEmpty()) ? null : students.get(0);
    }
}
