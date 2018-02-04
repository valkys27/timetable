package com.unicorn.edu.timetable.repository;

import com.unicorn.edu.timetable.repository.entity.Student;
import com.unicorn.edu.timetable.repository.entity.Subject;
import com.unicorn.edu.timetable.service.ServiceLocator;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl extends BaseRepositoryImpl implements SubjectRepository {
    @Override
    public List<Subject> getAll() {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        return entityManager.createQuery("from Subject ", Subject.class).getResultList();
    }

    @Override
    public List<Subject> getAll(List<Student> students) {
        List<Subject> subjects = new ArrayList<>();
        for (Student student : students) {
            for (Subject subject : student.getSubjects()) {
                if (!subjects.contains(subject))
                    subjects.add(subject);
            }
        }
        return subjects;
    }

    @Override
    public Subject findById(Long id) {
        return ServiceLocator.createEntityManager().find(Subject.class, id);
    }

    @Override
    public Subject findByAbrev(String abrev) {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);

        Root<Subject> subjectRoot = cq.from(Subject.class);

        cq.select(subjectRoot).where(cb.equal(subjectRoot.get("abrev"), abrev));

        List<Subject> subjects = entityManager.createQuery(cq).getResultList();
        return (subjects.isEmpty()) ? null : subjects.get(0);
    }
}
