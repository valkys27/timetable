package com.unicorn.edu.timetable.service;

import com.unicorn.edu.timetable.repository.entity.*;
import com.unicorn.edu.timetable.utils.StringUtils;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private static Locale locale = new Locale("cs");

    private Scanner scanner;

    public StudentServiceImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void printTimeTable() {
        StringBuilder sb = new StringBuilder();
        List<Student> students = readStudents(true);
        sb.append("######################### vybraní studenti - rozvrh hodin "  + "#########################");
        sb.append("\n\t\t8:00 - 8:45\t\t  9:00 - 9:45\t\t  10:00 - 10:45\t\t  11:00 - 11:45");
        printSubjects(sb, getSubjectPenetration(students));
        sb.append("\n##################################################################################\n");
        System.out.print(sb.toString());
    }

    private void printSubjects(StringBuilder sb, List<Subject> printedSubjects) {
        Map<DayOfWeek, List<Subject>> subjectsByDay =
                printedSubjects.stream().collect(
                        Collectors.groupingBy(Subject::getWeekday)
                );
        subjectsByDay.forEach((d, s) -> s.sort(Comparator.comparing(Subject::getTime)));
        Arrays.asList(DayOfWeek.values()).subList(0, 5).forEach(d -> {
            List<Subject> subjects = subjectsByDay.getOrDefault(d, new ArrayList<>());
            sb.append("\n--------------------------------------------------------------------------------\n");
            StringUtils.appendTimeTableItem(sb, subjects, Subject.TimeTableItem.ABREV);
            sb.append(String.format("\n%s", d.getDisplayName(TextStyle.SHORT, locale)));
            StringUtils.appendTimeTableItem(sb, subjects, Subject.TimeTableItem.ROOM_NO);
            sb.append("\n");
            StringUtils.appendTimeTableItem(sb, subjects, Subject.TimeTableItem.LECTURE_NAME);
        });
    }

    private static List<Subject> getSubjectPenetration(List<Student> students) {
        List<Subject> penetration = new ArrayList<>(students.get(0).getSubjects());
        for (Student student : students) {
            penetration.retainAll(student.getSubjects());
        }
        return penetration;
    }

    @Override
    public void printFreeTime() {
        List<Student> students = readStudents(false);
        List<Subject> subjectPenetration = getSubjectPenetration(students);
        printFreeTime(subjectPenetration);
    }

    private void printFreeTime(List<Subject> subjects) {
        Map<DayOfWeek, List<Subject>> subjectsByDay =
                subjects.stream().collect(
                        Collectors.groupingBy(Subject::getWeekday)
                );
        subjectsByDay.forEach((d, s) -> s.sort(Comparator.comparing(Subject::getTime)));
        StringBuilder sb = new StringBuilder();
        sb.append("######################### vybraní studenti - volný čas "  + "#########################");
        sb.append("\n\t\t8:00 - 8:45\t\t  9:00 - 9:45\t\t  10:00 - 10:45\t\t  11:00 - 11:45");
        Arrays.asList(DayOfWeek.values()).subList(0, 5).forEach(d -> {
            sb.append(String.format("\n%s\t\t", d.getDisplayName(TextStyle.SHORT, locale)));
            Iterator<Subject> it = subjectsByDay.getOrDefault(d, new ArrayList<>()).iterator();
            boolean next = it.hasNext();
            Subject subject = null;
            for (int i = 8; i < 12 || next; i++) {
                if (next) {
                    subject = it.next();
                }
                if (subject != null && subject.getTime().getHour() == i) {
                    sb.append("xxxxxxxxxxx\t\t\t");
                    next = it.hasNext();
                }
                else {
                    sb.append("ooooooooooo\t\t\t");
                    next = false;
                }
            }
            sb.append("\n");
        });
        sb.append("\n##################################################################################\n");
        System.out.println(sb.toString());
    }

    @Override
    public void printAll() {
        List<Student> students = ServiceLocator.getStudentRepository().getAll();
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }

    @Override
    public void printBySubject() {
        Subject subject = ServiceLocator.getSubjectService().readSubject();
        for (Student student:
                subject.getStudents()) {
            System.out.println(student.toString());
        }
    }

    @Override
    public void add() {
        Student student = new Student();
        readStudentAttributes(student);
        student.setActive(true);
        ServiceLocator.getStudentRepository().save(student);
        System.out.println("Student byl úspěšně přidán.");
    }

    private void readStudentAttributes(Student student) {
        System.out.print("uživatelské jméno: ");
        student.setUsername(scanner.nextLine());
        System.out.print("křestní jméno: ");
        student.setFirstName(scanner.nextLine());
        System.out.print("příjmení: ");
        student.setLastName(scanner.nextLine());
        System.out.print("email: ");
        student.setEmail(scanner.nextLine());
    }

    @Override
    public void edit() {
        Student edited;
        do {
            System.out.print("Zadejte uživatelské jméno studenta, kterého chcete editovat: ");
            String username = scanner.nextLine();
            edited = ServiceLocator.getStudentRepository().findByUsername(username);
        } while (edited == null);
        if (!edited.isActive()) {
            System.out.println("Účet studenta byl deaktivován.");
            return;
        }
        System.out.println(edited.toString());
        readStudentAttributes(edited);
        ServiceLocator.getStudentRepository().save(edited);
        System.out.println("Student byl úspěšně upraven.");
    }

    @Override
    public void activate() {
        Student student;
        do {
            System.out.print("Zadejte uživatelské jméno studenta, jehož účet chcete aktivovat/deaktivovat: ");
            String username = scanner.nextLine();
            student = ServiceLocator.getStudentRepository().findByUsername(username);
        } while (student == null);
        ServiceLocator.getSubjectService().removeFromAllSubjects(student);
        student.setActive(!student.isActive());
        ServiceLocator.getStudentRepository().save(student);
        if (student.isActive())
            System.out.println("Účet studenta byl úspěšně aktivován.");
        else
            System.out.println("Účet studenta byl úspěšně deaktivován.");
    }

    public List<Student> readStudents(boolean deactivated) {
        List<Student> students = new ArrayList<>();
        do {
            System.out.print("Zadejte uživatelská jména studentů oddělena čárkami: ");
            String[] usernames = scanner.nextLine().split(",");
            for (String username : usernames) {
                Student student = ServiceLocator.getStudentRepository().findByUsername(username);
                if (student != null)
                    students.add(student);
            }
        } while (students.isEmpty());
        return students;
    }

    public Student readStudent(boolean deactivated) {
        Student student;
        do {
            System.out.print("Zadejte uživatelské jméno studenta: ");
            String username = scanner.nextLine();
            student = ServiceLocator.getStudentRepository().findByUsername(username);
            if (deactivated && !student.isActive())
                System.out.println("Student není aktivní.");
        } while (student == null || (deactivated && !student.isActive()));
        return student;
    }
}
