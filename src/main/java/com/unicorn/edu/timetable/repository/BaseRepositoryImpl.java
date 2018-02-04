package com.unicorn.edu.timetable.repository;

import com.unicorn.edu.timetable.repository.entity.BaseEntity;
import com.unicorn.edu.timetable.repository.entity.Student;
import com.unicorn.edu.timetable.repository.entity.Subject;
import com.unicorn.edu.timetable.service.ServiceLocator;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryImpl implements BaseRepository {
    @Override
    public void save(BaseEntity baseEntity) {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        entityManager.getTransaction().begin();
        if (baseEntity.getId() == null)
            entityManager.persist(baseEntity);
        else
            entityManager.merge(baseEntity);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
