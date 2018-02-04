package com.unicorn.edu.timetable.repository.entity;

import javax.persistence.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

@Entity
@Table(name = "subject")
public class Subject implements BaseEntity {

    public Subject() {
        students = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "abrev", length = 5)
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

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addStudents(Student[] students) {
        this.students.addAll(Arrays.asList(students));
    }

    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    public String getTimeTableItem(TimeTableItem item) {
        switch (item) {
            case ABREV: return abrev;
            case ROOM_NO: return roomNo;
            case LECTURE_NAME: return lectorName;
            default: return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s)\n", name, abrev));
        sb.append(String.format("učitel: %s\n", lectorName));
        sb.append(String.format("místnost: %s\n", roomNo));
        sb.append(String.format("den: %s\n", weekday.getDisplayName(TextStyle.FULL, new Locale("cs"))));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        sb.append(String.format("čas: %s\n", time.format(formatter)));
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subject && ((Subject) obj).getAbrev().equals(abrev);
    }

    public enum TimeTableItem {
        ABREV, ROOM_NO, LECTURE_NAME
    }
}
