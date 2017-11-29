package com.unicorn.edu.timetable.repository.entity;

import javax.persistence.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {

    public Subject() {
        students = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "abrev", length = 5)
    private String abrev;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "lector_name", length = 30)
    private String lectorName;

    @Column(name = "room_no", length = 6)
    private String roomNo;

    @Column(name = "weekday")
    @Enumerated(EnumType.STRING)
    private DayOfWeek weekday;

    @Column(name = "time")
    private LocalTime time;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students;

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLectorName() {
        return lectorName;
    }

    public void setLectorName(String lectorName) {
        this.lectorName = lectorName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public DayOfWeek getWeekday() {
        return weekday;
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
