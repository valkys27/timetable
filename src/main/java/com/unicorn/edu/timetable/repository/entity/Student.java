package com.unicorn.edu.timetable.repository.entity;

import com.unicorn.edu.timetable.utils.StringUtils;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "student")
public class Student {

    public Student() {
        subjects = new ArrayList<>();
    }

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

    public boolean addSubject(Subject subject) {
        if (subjects.stream().noneMatch(s ->
                subject.getTime().equals(s.getTime()) && subject.getWeekday().equals(s.getWeekday())
        )) {
            subjects.add(subject);
            return true;
        }
        return false;
    }

    public Collection<Subject> getSubjects() {
        return Collections.unmodifiableCollection(subjects);
    }

    public void printSubjectList() {
        StringBuilder sb = new StringBuilder();
        for (Subject subject : subjects) {
            sb.append(subject.getAbrev());
            sb.append(subject.getName());
            sb.append(subject.getLectorName());
            sb.append("\n");
        }
        System.out.printf(sb.toString());
    }

    public void printTimeTable() {
        Locale locale = new Locale("cs");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("################## %s %s (%s) - rozvrh hodin "  + "##################", getFirstName(), getLastName(), getUsername()));
        sb.append("\n\t\t8:00 - 8:45\t\t9:00 - 9:45\t\t10:00 - 10:45\t\t11:00 - 11:45");
        Map<DayOfWeek, List<Subject>> subjectsByDay =
                subjects.stream().collect(
                        Collectors.groupingBy(s -> s.getWeekday())
                );
        subjectsByDay.forEach((d, s) -> s.sort(Comparator.comparing(Subject::getTime)));
        Arrays.asList(DayOfWeek.values()).subList(0, 5).forEach(d -> {
            List<Subject> subjects = subjectsByDay.getOrDefault(d, new ArrayList<>());
            sb.append("\n-------------------------------------------------------------------------\n");
            subjects.forEach(s -> sb.append(String.format("%s", StringUtils.alignCenter(s.getAbrev()))));
            sb.append(String.format("\n%s", d.getDisplayName(TextStyle.SHORT, locale)));
            subjects.forEach(s -> sb.append(String.format("%s", StringUtils.alignCenter(s.getRoomNo()))));
            sb.append("\n");
            subjects.forEach(s -> sb.append(String.format("%s", StringUtils.alignCenter(s.getLectorName()))));
        });
        sb.append("\n#########################################################################");
        System.out.printf(sb.toString());
    }

    public void printFreeTime() {

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
        return "Student{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
