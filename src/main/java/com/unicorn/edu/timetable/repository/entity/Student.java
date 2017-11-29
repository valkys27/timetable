package com.unicorn.edu.timetable.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "email", length = 5)
    private String email;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;
}
