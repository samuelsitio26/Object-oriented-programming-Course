package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S22032 Samuel Sitio
 * @author NIM Nama
 */

import academic.model.Course;
import academic.model.Enrollment;
import academic.model.Student;

public class Driver4 {

    public static void main(String[] args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Enrollment> enrollments = new ArrayList<>();

        while (masukan.hasNext()) {
            // Membaca masukan
            String input = masukan.nextLine();

            // Menghentikan program jika input adalah "---"
            if (input.equals("---")) {
                break;
            }

            String[] inputArray = input.split("#");
            String command = inputArray[0];

            if (command.equals("course-add")) {
                String code = inputArray[1];
                String name = inputArray[2];
                int credits = Integer.parseInt(inputArray[3]);
                String grade = inputArray[4];
                Course course = new Course(code, name, credits);
                course.setGrade(grade);
                courses.add(course);
            } else if (command.equals("student-add")) {
                String code = inputArray[1];
                String name = inputArray[2];
                String year = inputArray[3];
                String program = inputArray[4];
                Student student = new Student(code, name, year, program);
                students.add(student);
            } else if (command.equals("enrollment-add")) {
                String courseCode = inputArray[1];
                String studentCode = inputArray[2];
                String year = inputArray[3];
                String semester = inputArray[4];
                Enrollment enrollment = new Enrollment(courseCode, studentCode, year, semester);
                enrollments.add(enrollment);
            }
        }

        // Menampilkan entitas academic.model.Course
        for (Course course : courses) {
            System.out.println(course.getCode() + "|" + course.getStudyProgram() + "|" + course.getCredits() + "|" + course.getGrade());
        }

        // Menampilkan entitas academic.model.Student
        for (Student student : students) {
            System.out.println(student.getCode() + "|" + student.getName() + "|" + student.getTahun() + "|" + student.getProgamstudy());
        }

        // Menampilkan entitas academic.model.Enrollment
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment.getCode() + "|" + enrollment.getNim() + "|" + enrollment.getTahun() + "|" + enrollment.getEven() + "|" + enrollment.getEven2());
        }

        masukan.close();
    }
}
