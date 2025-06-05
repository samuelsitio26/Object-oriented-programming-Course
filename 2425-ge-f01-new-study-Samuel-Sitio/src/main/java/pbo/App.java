package pbo;

/**
 * @author 12S21018 williammanik
 */

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import pbo.model.*;
/**
 * Main class test
 *
 */
public class App {
 
  private static EntityManagerFactory emFactory;
  private static EntityManager em;
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    
    emFactory = Persistence.createEntityManagerFactory("study_plan_pu");
    em = emFactory.createEntityManager();
    
    // Untuk membersihkan data pada tabel
    String[] hapusSql = {
        "DELETE FROM Student",
        "DELETE FROM Course"
    };
    em.getTransaction().begin();
    for(String query : hapusSql){
        em.createQuery(query).executeUpdate();
    }
    em.getTransaction().commit();

    boolean running = true;
    while(running) {
      String perintah = input.nextLine();

      switch (perintah) {
        case "---":
          running = false;
          break;
        default:
          String[] argumen = perintah.split("#");
          executeCommand(argumen);
      }
    }

    input.close();
    em.close();
    emFactory.close();
  }

  private static void executeCommand(String[] argumen) {
    switch (argumen[0]){
      case "student-add":
          addStudent(argumen);
          break;
      case "course-add":
          addCourse(argumen);
          break;
      case "enroll":
          enrollStudent(argumen);
          break;
      case "student-show":
          showStudent(argumen);
          break;
      case "student-show-all":
          showAllStudents();
          break;
      case "course-show-all":
          showAllCourses();
          break;
      default:
          System.out.println("Invalid Input!");
    }
  }

  private static void addStudent(String[] argumen) {
    String nimBaru = argumen[1];
    String namaBaru = argumen[2];
    String prodiBaru = argumen[3];

    em.getTransaction().begin();
    Student mahasiswaBaru;
    if ((mahasiswaBaru = em.find(Student.class, nimBaru)) == null) {
      Student student = new Student(nimBaru, namaBaru, prodiBaru);
      em.persist(student);
    } else {
      if (!mahasiswaBaru.getNim().equals(nimBaru)) {
        Student student = new Student(nimBaru, namaBaru, prodiBaru);
        em.persist(student);
      }
    }
    em.getTransaction().commit();
  }

  private static void addCourse(String[] argumen) {
    String kodeBaru = argumen[1];
    String namaMatkul = argumen[2];
    int semesterBaru = Integer.parseInt(argumen[3]);
    int kreditBaru = Integer.parseInt(argumen[4]);

    em.getTransaction().begin();
    Course matkulBaru;
    if ((matkulBaru = em.find(Course.class, kodeBaru)) == null) {
        Course course = new Course(kodeBaru, namaMatkul, semesterBaru, kreditBaru);
        em.persist(course);
    } else {
      if (!matkulBaru.getKode().equals(kodeBaru)) {
        Course course = new Course(kodeBaru, namaMatkul, semesterBaru, kreditBaru);
        em.persist(course);
      }
    }
    em.getTransaction().commit();
  }

  private static void enrollStudent(String[] argumen) {
    String nimEnroll = argumen[1];
    String kodeEnroll = argumen[2];

    em.getTransaction().begin();
    Student mhsEnroll = em.find(Student.class, nimEnroll);
    Course mkEnroll = em.find(Course.class, kodeEnroll);
    if (mhsEnroll != null && mkEnroll != null) {
      mhsEnroll.getCourses().add(mkEnroll);
      mkEnroll.getStudents().add(mhsEnroll);
      em.persist(mhsEnroll);
      em.persist(mkEnroll);
      em.getTransaction().commit();
    } else {
      em.getTransaction().rollback();
    }
  }

  private static void showStudent(String[] argumen) {
    String nimShow = argumen[1];

    em.getTransaction().begin();
    Student mhsShow = em.find(Student.class, nimShow);
    mhsShow.printDetail();
    em.getTransaction().commit();
  }

  private static void showAllStudents() {
    String sqlMhs = "SELECT s FROM Student s ORDER BY s.nim";
    List<Student> daftarMhs = em.createQuery(sqlMhs, Student.class).getResultList();
    List<Student> urutMhs = daftarMhs.stream().sorted(Comparator.comparing(Student::getNim))
                                                  .collect(Collectors.toList());
    for(Student mhs : urutMhs){
      System.out.println(mhs.toString());
    }
  }

  private static void showAllCourses() {
    String sqlMk = "SELECT c FROM Course c ORDER BY c.kode";
    List<Course> daftarMk = em.createQuery(sqlMk, Course.class).getResultList();
    List<Course> urutMk = daftarMk.stream().sorted(Comparator.comparing(Course::getSemester).thenComparing(Course::getKode))
                                                  .collect(Collectors.toList());
    for (Course mk : urutMk) {
      System.out.println(mk.toString());
    }
  }
}
