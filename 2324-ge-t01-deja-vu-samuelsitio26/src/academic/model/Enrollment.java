package academic.model;

/**
 * @author Samuel Sitio
 */
public class Enrollment {
    private final Course course;
    private final Student student;
    private final String grade;

    public Enrollment(Course course, Student student, String grade) {
        this.course = course;
        this.student = student;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "|" + course + "|" + student + "|" + grade;
    }
}