package com.unicorn.edu.timetable;

import com.unicorn.edu.timetable.repository.entity.*;
import com.unicorn.edu.timetable.service.ServiceLocator;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class EntityManagerMain {

    static EntityManager entityManager;

    public static void main(String[] args) {
        entityManager = ServiceLocator.createEntityManager();
        char volba = 'a';

        do {
            do {
                printMenu();
                try {
                    volba = (char) System.in.read();
                } catch (IOException e) {}
            } while (volba < '0' || volba > ':');
            if (volba != '0')
                doTransaction(volba);
        } while (volba != '0');
        entityManager.close();
        ServiceLocator.shutdown();
    }

    public static void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("##### MENU #####\n");
        sb.append("0) zobrazit rozvrh\n");
        sb.append("1) zobrazit volný čas\n");
        sb.append("2) zobrazit přihlášené studenty\n");
        sb.append("3) zobrazit výpis předmětů\n");
        sb.append("4) přidat studenta\n");
        sb.append("5) editace studenta\n");
        sb.append("6) deaktivace studenta\n");
        sb.append("7) výpis detailu předmětu\n");
        sb.append("8) přihlášení předmětu\n");
        sb.append("9) odhlášení předmetu\n");
        sb.append(":) konec\n");
        sb.append("################\n");
        System.out.println(sb.toString());
    }

    static void doTransaction(char volba) {
        entityManager.getTransaction().begin();
        switch (volba) {
            case '0':
                break;
            case '1':
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
            case '7':
                break;
            case '8':
                break;
            case '9':
                break;
            default:
                break;
        }
        entityManager.getTransaction().commit();
    }

    static void printTimeTable() {
        List<Student> students = entityManager.createQuery("from students", Student.class).getResultList();

    }
}