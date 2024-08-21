package pbo.f01;

import java.util.*;

import pbo.f01.model.*;
/**
 * @author 12S22032 Samuel Sitio
 * @author 12S22024 Pimpin loi
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Constrac {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private Student[] students;
    private Dorm[] dorms;

    public Constrac(String persistenceUnit) {
        factory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = factory.createEntityManager();
        students = new Student[0];
        dorms = new Dorm[0];
    }

    public void cleanTables() {
        String deleteStudentJpql = "DELETE FROM Student s";
        String deleteDormJpql = "DELETE FROM Dorm d";

        entityManager.getTransaction().begin();
        entityManager.createQuery(deleteStudentJpql).executeUpdate();
        entityManager.createQuery(deleteDormJpql).executeUpdate();

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public Integer checkDuplicateStudent(String studentId) {
        int count = 0;
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                count++;
            }
        }
        return count;
    }

    public Integer checkDuplicateDorm(String dormName) {
        int count = 0;
        for (Dorm dorm : dorms) {
            if (dorm.getDormName().equals(dormName)) {
                count++;
            }
        }
        return count;
    }

    public void addNewStudent(String[] data) {
        Student student = new Student(data[1], data[2], data[3], data[4]);
        students = Arrays.copyOf(students, students.length + 1);
        students[students.length - 1] = student;
    }

    public void addNewDorm(String[] data) {
        Dorm dorm = new Dorm(data[1], Integer.parseInt(data[2]), data[3], 0);
        dorms = Arrays.copyOf(dorms, dorms.length + 1);
        dorms[dorms.length - 1] = dorm;
    }

    public void addStudentToDatabase(String[] data) {
        entityManager.getTransaction().begin();
        addNewStudent(data);
        entityManager.persist(students[students.length - 1]);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void addDormToDatabase(String[] data) {
        entityManager.getTransaction().begin();
        addNewDorm(data);
        entityManager.persist(dorms[dorms.length - 1]);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public Integer isValidAssignment(String studentId, String dormName) {
        int valid = 0;
        for (Dorm dorm : dorms) {
            if (dorm.getDormName().equals(dormName)) {
                for (Student student : students) {
                    if (student.getStudentId().equals(studentId) && student.getGender().equals(dorm.getDormGender())) {
                        valid++;
                    }
                }
            }
        }
        return valid;
    }

    public void assignStudentToDorm(String studentId, String dormName) {
        for (Dorm dorm : dorms) {
            if (dorm.getDormName().equals(dormName)) {
                for (Student student : students) {
                    if (student.getStudentId().equals(studentId) && dorm.getDormResident() < dorm.getDormCapacity()) {
                        student.setDorms(Set.of(dorm));
                        entityManager.persist(dorm);
                        entityManager.persist(student);
                        dorm.setDormResident(dorm.getDormResident() + 1);
                    }
                }
            }
        }
    }

    public void displayAllDormsAndStudents() {
        String jpql = "SELECT d FROM Dorm d ORDER BY d.dormName";
        List<Dorm> dormList = entityManager.createQuery(jpql, Dorm.class).getResultList();

        for (Dorm dorm : dormList) {
            System.out.println(dorm);

            Arrays.sort(students, (a, b) -> a.getStudentName().compareTo(b.getStudentName()));
            for (Student student : students) {
                if (student.getDorms().contains(dorm)) {
                    System.out.println(student);
                }
            }
        }
    }

    public void close() {
        entityManager.close();
        factory.close();
    }
}
