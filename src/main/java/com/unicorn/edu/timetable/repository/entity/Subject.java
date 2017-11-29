package com.unicorn.edu.timetable.repository.entity;

import javax.persistence.*;
import java.time.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "abrev", length = 5)
    private String abrev;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "lector_name", length = 30)
    private String lectorName;

    @Column(name = "room_no", length = 4)
    private String roomNo;

    @Column(name = "weekday")
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekday;

    @Column(name = "time")
    private LocalTime time;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students;
}
