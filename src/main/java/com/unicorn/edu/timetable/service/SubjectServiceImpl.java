package com.unicorn.edu.timetable.service;

import com.unicorn.edu.timetable.repository.entity.*;

import javax.persistence.EntityManager;
import java.time.*;
import java.util.*;

public class SubjectServiceImpl implements SubjectService {

    private Scanner scanner;

    public SubjectServiceImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void printAll() {
        EntityManager entityManager = ServiceLocator.createEntityManager();
        List<Subject> subjects = entityManager.createQuery("from Subject", Subject.class).getResultList();
        printSubjects(subjects);
    }

    private void printSubjects(List<Subject> subjects) {
        for (Subject subject:
                subjects) {
            System.out.println(subject.toString());
        }
    }

    @Override
    public void printByStudent() {
        Student student = ServiceLocator.getStudentService().readStudent(false);
        printSubjects(student.getSubjects());
    }

    @Override
    public void add() {
        Subject subject = new Subject();
        readSubjectAttributes(subject);
        ServiceLocator.getStudentRepository().save(subject);
        System.out.println("Předmět byl úspěšně přidán.");

    }

    private void readSubjectAttributes(Subject subject) {
        System.out.print("zkratka: ");
        subject.setAbrev(scanner.nextLine());
        System.out.print("název: ");
        subject.setName(scanner.nextLine());
        System.out.print("jméno učitele: ");
        subject.setLectorName(scanner.nextLine());
        System.out.print("místnost: ");
        subject.setRoomNo(scanner.nextLine());
        int day;
        do {
            System.out.print("den (1-5): ");
            day = scanner.nextInt();
        } while (day < 1 || day > 5);
        subject.setWeekday(DayOfWeek.of(day));
        int hour;
        do {
            System.out.print("hodina (8-11): ");
            hour = scanner.nextInt();
        } while (hour < 8 || hour > 11);
        subject.setTime(LocalTime.of(hour, 0));
    }

    @Override
    public void edit() {
        Subject edited;
        do {
            System.out.print("Zadejte zkratku předmetu, který chcete editovat: ");
            String abrev = scanner.nextLine();
            edited = ServiceLocator.getSubjectRepository().findByAbrev(abrev);
        } while (edited == null);
        System.out.println(edited.toString());
        readSubjectAttributes((edited));
        ServiceLocator.getStudentRepository().save(edited);
        System.out.println("Předmět byl úspěšně upraven.");
    }

    @Override
    public void addToSubject() {
        Subject subject = readSubject();
        List<Student> students = ServiceLocator.getStudentService().readStudents(false);
        for (Student student : students) {
            boolean added = student.addSubject(subject);
            if (added) {
                subject.addStudent(student);
                ServiceLocator.getStudentRepository().save(student);
                System.out.printf("Student %s byl zapsán na předmět %s.", student.getFullName(), subject.getName());
            } else
                System.out.printf("Student %s již je zapsán na předmět %s nebo má v tu dobu jiný předmět.", student.getFullName(), subject.getName());
        }
        ServiceLocator.getStudentRepository().save(subject);
    }

    @Override
    public Subject readSubject() {
        Subject subject;
        do {
            System.out.print("Zadejte zkratku předmětu: ");
            String abrev = scanner.nextLine();
            subject = ServiceLocator.getSubjectRepository().findByAbrev(abrev);
        } while (subject == null);
        return subject;
    }

    @Override
    public void removeFromSubject() {
        Subject subject = readSubject();
        List<Student> students = ServiceLocator.getStudentService().readStudents(false);
        for (Student student : students) {
            boolean removed = subject.removeStudent(student);
            if (removed) {
                student.removeSubject(subject);
                ServiceLocator.getStudentRepository().save(student);
                System.out.printf("Student %s byl odepsán z předmětu %s.", student.getFullName(), subject.getName());
            } else
                System.out.printf("Student %s nebyl zapsán na předmět %s.", student.getFullName(), subject.getName());
        }
        ServiceLocator.getStudentRepository().save(subject);
    }

    public void removeFromAllSubjects(Student student) {
        for (Subject subject : student.getSubjects()) {
            boolean removed = subject.removeStudent(student);
            if (removed) {
                student.removeSubject(subject);
                ServiceLocator.getSubjectRepository().save(subject);
            }
        }
        ServiceLocator.getStudentRepository().save(student);
        System.out.println("Student byl odepsán ze všech předmětů.");
    }
}
