package com.unicorn.edu.timetable;

import com.unicorn.edu.timetable.repository.entity.*;
import com.unicorn.edu.timetable.service.ServiceLocator;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.*;

public class SessionMain {

    static EntityManager entityManager;

    public static void main(String[] args) {
        entityManager = ServiceLocator.createEntityManager();
        prepareTestData();
        char choice;

        do {
            do {
                choice = 'a';
                printMenu();
                try {
                    System.in.read(new byte[System.in.available()]);
                    choice = (char) System.in.read();
                } catch (IOException e) {}
            } while (choice < '0' || choice > '=');
            if (choice != '=') {
                try {
                    System.in.read(new byte[System.in.available()]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                doTransaction(choice);
            }
        } while (choice != '=');
        entityManager.close();
        ServiceLocator.shutdown();
    }

    private static void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("##### MENU #####\n");
        sb.append("0) zobrazit rozvrh\n");
        sb.append("1) zobrazit volný čas\n");
        sb.append("2) zobrazit výpis všech studentů\n");
        sb.append("3) zobrazit přihlášené studenty\n");
        sb.append("4) zobrazit výpis předmětů\n");
        sb.append("5) zobrazit výpis předmětů studenta\n");
        sb.append("6) přidat studenta\n");
        sb.append("7) editace studenta\n");
        sb.append("8) aktivace/deaktivace účtu studenta\n");
        sb.append("9) přidat předmět\n");
        sb.append(":) editovat předmět\n");
        sb.append(";) přihlášení předmětu\n");
        sb.append("<) odhlášení předmetu\n");
        sb.append("=) konec\n");
        sb.append("################\n");
        System.out.println(sb.toString());
    }

    private static void doTransaction(char choice) {
        switch (choice) {
            case '0':
                ServiceLocator.getStudentService().printTimeTable();
                break;
            case '1':
                ServiceLocator.getStudentService().printFreeTime();
                break;
            case '2':
                ServiceLocator.getStudentService().printAll();
                break;
            case '3':
                ServiceLocator.getStudentService().printBySubject();
                break;
            case '4':
                ServiceLocator.getSubjectService().printAll();
                break;
            case '5':
                ServiceLocator.getSubjectService().printByStudent();
                break;
            case '6':
                ServiceLocator.getStudentService().add();
                break;
            case '7':
                ServiceLocator.getStudentService().edit();
                break;
            case '8':
                ServiceLocator.getStudentService().activate();
                break;
            case '9':
                ServiceLocator.getSubjectService().add();
                break;
            case ':':
                ServiceLocator.getSubjectService().edit();
                break;
            case ';':
                ServiceLocator.getSubjectService().addToSubject();
                break;
            case '<':
                ServiceLocator.getSubjectService().removeFromSubject();
                break;
            default:
                break;
        }
    }

    static void prepareTestData() {
        Subject subject = new Subject();
        subject.setAbrev("MAT");
        subject.setName("Matematika");
        subject.setWeekday(DayOfWeek.MONDAY);
        subject.setTime(LocalTime.of(10, 0));
        subject.setLectorName("Navrátil");
        subject.setRoomNo("SEM401");
        ServiceLocator.getSubjectRepository().save(subject);
        Subject subject2 = new Subject();
        subject2.setAbrev("CHEM");
        subject2.setName("Chemie");
        subject2.setWeekday(DayOfWeek.TUESDAY);
        subject2.setTime(LocalTime.of(11, 0));
        subject2.setLectorName("Neuvirthová");
        subject2.setRoomNo("LAB301");
        ServiceLocator.getSubjectRepository().save(subject2);
        Subject subject3 = new Subject();
        subject3.setAbrev("FYZ");
        subject3.setName("Fyzika");
        subject3.setWeekday(DayOfWeek.TUESDAY);
        subject3.setTime(LocalTime.of(9, 0));
        subject3.setLectorName("Joska");
        subject3.setRoomNo("LAB302");
        ServiceLocator.getSubjectRepository().save(subject3);
        Subject subject4 = new Subject();
        subject4.setAbrev("PROG");
        subject4.setName("Programování");
        subject4.setWeekday(DayOfWeek.THURSDAY);
        subject4.setTime(LocalTime.of(9, 0));
        subject4.setLectorName("Fikejz");
        subject4.setRoomNo("PC101");
        ServiceLocator.getSubjectRepository().save(subject4);
        Subject subject5 = new Subject();
        subject5.setAbrev("GRAF");
        subject5.setName("Grafika");
        subject5.setWeekday(DayOfWeek.WEDNESDAY);
        subject5.setTime(LocalTime.of(8, 0));
        subject5.setLectorName("Veselý");
        subject5.setRoomNo("PC102");
        ServiceLocator.getSubjectRepository().save(subject5);
        Subject subject6 = new Subject();
        subject6.setAbrev("AJ");
        subject6.setName("Anglictina");
        subject6.setWeekday(DayOfWeek.MONDAY);
        subject6.setTime(LocalTime.of(8, 0));
        subject6.setLectorName("Strnadelová");
        subject6.setRoomNo("SEM401");
        ServiceLocator.getSubjectRepository().save(subject6);
        Subject subject7 = new Subject();
        subject7.setAbrev("DATS");
        subject7.setName("Datové struktury");
        subject7.setWeekday(DayOfWeek.TUESDAY);
        subject7.setTime(LocalTime.of(10, 0));
        subject7.setLectorName("Kávička");
        subject7.setRoomNo("PC103");
        ServiceLocator.getSubjectRepository().save(subject7);
        Subject subject8 = new Subject();
        subject8.setAbrev("WEB");
        subject8.setName("Webové stránky");
        subject8.setWeekday(DayOfWeek.TUESDAY);
        subject8.setTime(LocalTime.of(9, 0));
        subject8.setLectorName("Brožek");
        subject8.setRoomNo("PC104");
        ServiceLocator.getSubjectRepository().save(subject8);
        Subject subject9 = new Subject();
        subject9.setAbrev("PS1");
        subject9.setName("Počítačové sítě 1");
        subject9.setWeekday(DayOfWeek.THURSDAY);
        subject9.setTime(LocalTime.of(11, 0));
        subject9.setLectorName("Neradová");
        subject9.setRoomNo("LAB303");
        ServiceLocator.getSubjectRepository().save(subject9);
        Subject subject10 = new Subject();
        subject10.setAbrev("HIT");
        subject10.setName("Historie techniky");
        subject10.setWeekday(DayOfWeek.WEDNESDAY);
        subject10.setTime(LocalTime.of(11, 0));
        subject10.setLectorName("Javůrek");
        subject10.setRoomNo("SEM405");
        ServiceLocator.getSubjectRepository().save(subject10);

        Student student = new Student();
        student.setFirstName("Tomáš");
        student.setLastName("Valko");
        student.setUsername("valto");
        student.setActive(true);
        student.setEmail("tomas.valko@upce.cz");
        student.addSubject(subject);
        student.addSubject(subject2);
        student.addSubject(subject3);
        student.addSubject(subject4);
        student.addSubject(subject5);
        ServiceLocator.getStudentRepository().save(student);
        Student student2 = new Student();
        student2.setFirstName("Martin");
        student2.setLastName("Mikulecký");
        student2.setUsername("mikma");
        student2.setActive(true);
        student2.setEmail("martin.mikulecky@upce.cz");
        student2.addSubject(subject);
        student2.addSubject(subject2);
        student2.addSubject(subject3);
        student2.addSubject(subject4);
        student2.addSubject(subject5);
        ServiceLocator.getStudentRepository().save(student2);
        Student student3 = new Student();
        student3.setFirstName("Marcel");
        student3.setLastName("Pešek");
        student3.setUsername("pesma");
        student3.setActive(true);
        student3.setEmail("marcel.pesek@upce.cz");
        student3.addSubject(subject);
        student3.addSubject(subject2);
        student3.addSubject(subject3);
        student3.addSubject(subject4);
        student3.addSubject(subject5);
        ServiceLocator.getStudentRepository().save(student3);
        Student student4 = new Student();
        student4.setFirstName("Tomáš");
        student4.setLastName("Roček");
        student4.setUsername("rocto");
        student4.setActive(true);
        student4.setEmail("tomas.rocek@upce.cz");
        student4.addSubject(subject);
        student4.addSubject(subject2);
        student4.addSubject(subject3);
        student4.addSubject(subject4);
        student4.addSubject(subject5);
        ServiceLocator.getStudentRepository().save(student4);
        Student student5 = new Student();
        student5.setFirstName("Marcel");
        student5.setLastName("Chalupník");
        student5.setUsername("chama");
        student5.setActive(true);
        student5.setEmail("marcel.chalupnik@upce.cz");
        student5.addSubject(subject);
        student5.addSubject(subject2);
        student5.addSubject(subject3);
        student5.addSubject(subject4);
        student5.addSubject(subject5);
        ServiceLocator.getStudentRepository().save(student5);
        Student student6 = new Student();
        student6.setFirstName("Tomáš");
        student6.setLastName("Dočekal");
        student6.setUsername("docto");
        student6.setActive(true);
        student6.setEmail("tomas.docekal@upce.cz");
        student6.addSubject(subject6);
        student6.addSubject(subject7);
        student6.addSubject(subject8);
        student6.addSubject(subject9);
        student6.addSubject(subject10);
        ServiceLocator.getStudentRepository().save(student6);
        Student student7 = new Student();
        student7.setFirstName("Tomáš");
        student7.setLastName("Forman");
        student7.setUsername("forto");
        student7.setActive(true);
        student7.setEmail("tomas.Forman@upce.cz");
        student7.addSubject(subject6);
        student7.addSubject(subject7);
        student7.addSubject(subject8);
        student7.addSubject(subject9);
        student7.addSubject(subject10);
        ServiceLocator.getStudentRepository().save(student7);
        Student student8 = new Student();
        student8.setFirstName("Ondřej");
        student8.setLastName("Tichý");
        student8.setUsername("ticon");
        student8.setActive(true);
        student8.setEmail("ondrej.tichy@upce.cz");
        student8.addSubject(subject6);
        student8.addSubject(subject7);
        student8.addSubject(subject8);
        student8.addSubject(subject9);
        student8.addSubject(subject10);
        ServiceLocator.getStudentRepository().save(student8);
        Student student9 = new Student();
        student9.setFirstName("Jakub");
        student9.setLastName("Čada");
        student9.setUsername("cadja");
        student9.setActive(true);
        student9.setEmail("jakub.cada@upce.cz");
        student9.addSubject(subject6);
        student9.addSubject(subject7);
        student9.addSubject(subject8);
        student9.addSubject(subject9);
        student9.addSubject(subject10);
        ServiceLocator.getStudentRepository().save(student9);
        Student student10 = new Student();
        student10.setFirstName("Radek");
        student10.setLastName("Vagenknecht");
        student10.setUsername("vagra");
        student10.setActive(true);
        student10.setEmail("radek.vagenknecht@upce.cz");
        student10.addSubject(subject6);
        student10.addSubject(subject7);
        student10.addSubject(subject8);
        student10.addSubject(subject9);
        student10.addSubject(subject10);
        ServiceLocator.getStudentRepository().save(student10);

        Student[] students = { student, student2, student3, student4, student5 };
        Student[] students2 = { student6, student7, student8, student9, student10 };
        subject.addStudents(students);
        ServiceLocator.getSubjectRepository().save(subject);
        subject2.addStudents(students);
        ServiceLocator.getSubjectRepository().save(subject2);
        subject3.addStudents(students);
        ServiceLocator.getSubjectRepository().save(subject3);
        subject4.addStudents(students);
        ServiceLocator.getSubjectRepository().save(subject4);
        subject5.addStudents(students);
        ServiceLocator.getSubjectRepository().save(subject5);
        subject6.addStudents(students2);
        ServiceLocator.getSubjectRepository().save(subject6);
        subject7.addStudents(students2);
        ServiceLocator.getSubjectRepository().save(subject7);
        subject8.addStudents(students2);
        ServiceLocator.getSubjectRepository().save(subject8);
        subject9.addStudents(students2);
        ServiceLocator.getSubjectRepository().save(subject9);
        subject10.addStudents(students2);
        ServiceLocator.getSubjectRepository().save(subject10);
    }
}