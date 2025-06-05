package pbo.model;

// Refactored by Copilot: struktur diubah, fungsi tetap sama
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
  @Id
  @Column(name = "nim", length = 10, nullable = false)
  private String nim;

  @Column(name = "nama_student", length = 50, nullable = false)
  private String nama;

  @Column(name = "prodi", length = 50, nullable = false)
  private String prodi;

  @ManyToMany
  @JoinTable(name = "STU_COUR",
    joinColumns = @JoinColumn(name = "STU_NIM", referencedColumnName = "nim"),
    inverseJoinColumns = @JoinColumn(name = "COUR_KODE", referencedColumnName = "kode")
  )
  private List<Course> courses;

  // Default constructor
  public Student() {}

  // Main constructor
  public Student(String nim, String nama, String prodi) {
    this.nim = nim;
    this.nama = nama;
    this.prodi = prodi;
  }

  // Getters
  public String getNim() { return nim; }
  public String getNama() { return nama; }
  public String getProdi() { return prodi; }
  public List<Course> getCourses() { return courses; }

  // Setters
  public void setNim(String nim) { this.nim = nim; }
  public void setNama(String nama) { this.nama = nama; }
  public void setProdi(String prodi) { this.prodi = prodi; }
  public void setCourses(List<Course> courses) { this.courses = courses; }

  // Print detail student and enrolled courses
  public void printDetail() {
    System.out.println(nim + "|" + nama + "|" + prodi);
    if (courses != null && !courses.isEmpty()) {
      List<Course> sorted = new ArrayList<>(courses);
      sorted.sort(Comparator.comparing(Course::getSemester).thenComparing(Course::getKode));
      for (Course c : sorted) {
        System.out.println(c.toString());
      }
    }
  }

  // String representation
  @Override
  public String toString() {
    return nim + "|" + nama + "|" + prodi;
  }
}
