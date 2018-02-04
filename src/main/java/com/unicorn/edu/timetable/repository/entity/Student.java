package com.unicorn.edu.timetable.repository.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "student")
public class Student implements BaseEntity {

    public Student() {
        subjects = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "username", length = 20)
    private String username;

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean addSubject(Subject subject) {
        if (subjects.stream().noneMatch(s -> subject.getAbrev().equals(s.getAbrev())
                || (subject.getTime().equals(s.getTime()) && subject.getWeekday().equals(s.getWeekday())))) {
            subjects.add(subject);
            return true;
        }
        return false;
    }

    public boolean removeSubject(Subject subject) {
        return subjects.remove(subject);
    }

    public List<Subject> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s (%s)\n", firstName, lastName, username));
        sb.append(String.format("email: %s\n", email));
        sb.append(String.format("aktivní: %s\n", (active) ? "ANO" : "NE"));
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Student && ((Student) obj).getUsername().equals(username);
    }
}
