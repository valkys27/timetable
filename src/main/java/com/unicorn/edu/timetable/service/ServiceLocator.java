package com.unicorn.edu.timetable.service;

import com.unicorn.edu.timetable.repository.StudentRepository;
import com.unicorn.edu.timetable.repository.StudentRepositoryImpl;
import com.unicorn.edu.timetable.repository.SubjectRepository;
import com.unicorn.edu.timetable.repository.SubjectRepositoryImpl;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;

import javax.persistence.*;

public class ServiceLocator {

    private static final String PERSISTENCE_UNIT_NAME = "timetable";

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static EntityManagerFactory entityManagerFactory;
    private static StudentRepository studentRepository;
    private static SubjectRepository subjectRepository;
    private static StudentService studentService;
    private static SubjectService subjectService;

    private ServiceLocator() {}

    public static EntityManager createEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return entityManagerFactory;
    }

    public static Session createSession() {
        return getSessionFactory().openSession();
    }

    public static void closeSession(Session session) {
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public static StudentRepository getStudentRepository() {
        if (studentRepository == null)
            studentRepository = new StudentRepositoryImpl();
        return studentRepository;
    }

    public static SubjectRepository getSubjectRepository() {
        if (subjectRepository ==  null)
            subjectRepository = new SubjectRepositoryImpl();
        return subjectRepository;
    }

    public static StudentService getStudentService() {
        if (studentService == null)
            studentService = new StudentServiceImpl();
        return studentService;
    }

    public static SubjectService getSubjectService() {
        if (subjectService == null)
            subjectService = new SubjectServiceImpl();
        return subjectService;
    }
}
