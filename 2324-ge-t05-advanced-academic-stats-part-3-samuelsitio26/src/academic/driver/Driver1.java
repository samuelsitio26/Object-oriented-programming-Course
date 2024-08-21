package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S22032 Samuel Sitio
 */

import academic.model.Course;
import academic.model.Enrollment;
import academic.model.Student;
import academic.model.Lecturer;

public class Driver1 {

    public static void main(String[] args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ArrayList<Enrollment> studentEnrollments = new ArrayList<>();

        while (masukan.hasNext()) {
            // Membaca masukan
            String input = masukan.nextLine();

            // Menghentikan program jika input adalah "---"
            if (input.equals("---")) {
                break;
            }

            String[] inputArray = input.split("#");
            String command = inputArray[0];

            if (command.equals("student-add")) {
                String code = inputArray[1];
                String name = inputArray[2];
                String year = inputArray[3];
                String program = inputArray[4];
                boolean hasStd = false;

                for (int i = 0; i < students.size(); i++) {
                    if (students.get(i).getCode().equals(code)) {
                        hasStd = true;
                    }
                }
                if (!hasStd) {
                    Student student = new Student(code, name, year, program, "");
                    students.add(student);
                }

            } else if (command.equals("enrollment-add")) {
                String courseCode = inputArray[1];
                String studentCode = inputArray[2];
                String year = inputArray[3];
                String semester = inputArray[4];
                boolean cek_enrollment = false;
                for (Enrollment e : enrollments) {
                    if (e.getCode().equals(courseCode) && e.getNim().equals(studentCode) && e.getTahun().equals(year)
                            && e.getEven().equals(semester)) {
                        cek_enrollment = true;
                    }
                }
                if (!cek_enrollment) {
                    Enrollment enrollment = new Enrollment(courseCode, studentCode, year, semester);
                    enrollments.add(enrollment);
                    studentEnrollments.add(enrollment);
                }
                // Enrollment enrollment = new Enrollment(courseCode, studentCode, year,
                // semester);
                // enrollments.add(enrollment);

            } else if (command.equals("enrollment-grade")) {
                String courseCode = inputArray[1];
                String studentCode = inputArray[2];
                String year = inputArray[3];
                String semester = inputArray[4];
                
                if (inputArray.length > 5) {
                    String grade = inputArray[5];
                    for (Enrollment e : enrollments) {
                        if (e.getCode().equals(courseCode) && e.getNim().equals(studentCode)
                                && e.getTahun().equals(year) && e.getEven().equals(semester)) {
                            e.setEven2(grade);
                        }
                    }
                }

            } else if (command.equals("lecturer-add")) {
                String id = inputArray[1];
                String name = inputArray[2];
                String initial = inputArray[3];
                String email = inputArray[4];
                String studyProgram = inputArray[5];
                Lecturer lecturer = new Lecturer(id, name, initial, email, studyProgram, "");
                lecturers.add(lecturer);

            } else if (command.equals("course-add")) {
                String code = inputArray[1];
                String name = inputArray[2];
                int credits = Integer.parseInt(inputArray[3]);
                String grade = inputArray[4];
                String[] lecturersArray = inputArray[5].split(",");
                String lecturerList = "";
                for (int i = 0; i < lecturersArray.length; i++) {
                    String lecturerInitial = lecturersArray[i];
                    // mencari dosen dengan inisial yang sesuai
                    for (Lecturer l : lecturers) {
                        if (l.getInitial().equals(lecturerInitial)) {
                            if (i != 0) {
                                lecturerList += ";"; // tambahkan ';' jika bukan dosen pertama
                            }
                            lecturerList += l.getInitial() + " (" + l.getEmail() + ")";
                            break;
                        }
                    }
                }
                Course course = new Course(code, name, credits);
                course.setGrade(grade);
                course.setLecturer(lecturerList);
                courses.add(course);

            } else if (command.equals("student-details")) {
                String studentCode = inputArray[1];
                String[] courseString = new String[5];
                String[] nilai = new String[5];
                double totalGradePoints = 0;
                int totalCredits = 0;
                int i = 0;

                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getNim().equals(studentCode)) {
                        courseString[i] = enrollment.getCode();
                        nilai[i] = enrollment.getEven2();
                        i++;
                    }
                }

                for (int j = 0; j < i; j++) {
                    for (int k = j + 1; k < i; k++) {
                        if (courseString[j].equals(courseString[k])) {
                            // If there's a duplicate course, update the grade
                            nilai[j] = nilai[k];
                            // Set the duplicate course and grade to null
                            courseString[k] = null;
                            nilai[k] = null;
                        }
                    }
                }
                

                for (int j = 0; j < i; j++) {
                    if (courseString[j] != null && nilai[j] != null) {
                        for (Course course : courses) {
                            if (course.getCode().equals(courseString[j])) {
                                totalGradePoints += convertGradeToPoint(nilai[j]) * course.getCredits();
                                totalCredits += course.getCredits();
                            }
                        }
                    }
                }

                double gpa = totalGradePoints / totalCredits;

                if (totalGradePoints == 0.0) {
                    totalCredits = 0;   
                }
                    
                for (Student student : students) {
                    if (student.getCode().equals(studentCode)) {
                        System.out.println(student.getCode() + "|" + student.getName() + "|" + student.getTahun() + "|" + student.getProgamstudy() + "|" + String.format("%.2f", gpa) + "|" + totalCredits);
                    }
                }

            }
        }

        // Menampilkan entitas academic.model.lecturer hapus duplikasi
        for (int i = 0; i < lecturers.size(); i++) {
            for (int j = i + 1; j < lecturers.size(); j++) {
                if (lecturers.get(i).equals(lecturers.get(j))) {
                    lecturers.remove(j);
                    j--;
                }
            }
        }

        for (Lecturer lecturer : lecturers) {
            System.out.println(lecturer.getId() + "|" + lecturer.getName() + "|" + lecturer.getInitial() + "|"
                    + lecturer.getEmail() + "|" + lecturer.getStudyProgram());
        }

        // Menampilkan entitas academic.model.Course
        // Menghapus duplikasi
        for (int i = 0; i < courses.size(); i++) {
            for (int j = i + 1; j < courses.size(); j++) {
                if (courses.get(i).equals(courses.get(j))) {
                    courses.remove(j);
                    j--;
                }
            }
        }
        for (Course course : courses) {
            System.out.println(course.getCode() + "|" + course.getStudyProgram() + "|" + course.getCredits() + "|"
                    + course.getGrade() + "|" + course.getLecturer());
        }
        // Menampilkan entitas academic.model.Student
        // menghapus duplikasi
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).equals(students.get(j))) {
                    students.remove(j);
                    j--;
                }
            }
        }
        for (Student student : students) {
            System.out.println(student.getCode() + "|" + student.getName() + "|" + student.getTahun() + "|"
                    + student.getProgamstudy());
        }

        // menghapus duplikasi
        for (int i = 0; i < enrollments.size(); i++) {
            for (int j = i + 1; j < enrollments.size(); j++) {
                if (enrollments.get(i).equals(enrollments.get(j))) {
                    enrollments.remove(j);
                    // j--;
                }
            }
        }
        // for (Enrollment enrollment : enrollments) {
        // System.out.println(enrollment.getCode() + "|" + enrollment.getNim() + "|" +
        // enrollment.getTahun() + "|" + enrollment.getEven() + "|" +
        // enrollment.getEven2());
        // }

        for (Enrollment enrollment : studentEnrollments) {
            System.out.println(enrollment.getCode() + "|" + enrollment.getNim() + "|" + enrollment.getTahun() + "|"
                    + enrollment.getEven() + "|" + enrollment.getEven2());
        }
        masukan.close();
    }

    private static double convertGradeToPoint(String grade) {
        switch (grade) {
            case "A":
                return 4.0;
            case "AB":
                return 3.5;
            case "B":
                return 3.0;
            case "BC":
                return 2.5;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "E":
                return 0.0;
            default:
                return 0.0; // Assuming "None" grade has 0 points
        }
    }
}