package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S22032 Samuel Sitio
 */

import academic.model.Course;
import academic.model.CourseOpening;
import academic.model.Enrollment;
import academic.model.Student;
import academic.model.Lecturer;
// import academic.model.Record.*;
import academic.model.Grade;

public class Driver1 {

    public static void main(String[] args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Enrollment<String, String>> enrollments = new ArrayList<>(); // menggunakan tipe data generic
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ArrayList<Enrollment<String, String>> studentEnrollments = new ArrayList<>(); // menggunakan tipe data generic
        ArrayList<CourseOpening<String>> courseOpenings = new ArrayList<>(); // menggunakan tipe data generic
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
                    Student student = new Student(code, name, year, program);
                    students.add(student);
                }

            } else if (command.equals("enrollment-add")) {
                String courseCode = inputArray[1];
                String studentCode = inputArray[2];
                String year = inputArray[3];
                String semester = inputArray[4];
                boolean cek_enrollment = false;
                for (Enrollment<String, String> e : enrollments) {
                    if (e.getCode().equals(courseCode) && e.getNim().equals(studentCode) && e.getTahun().equals(year)
                            && e.getEven().equals(semester)) {
                        cek_enrollment = true;
                    }
                }
                if (!cek_enrollment) {
                    Enrollment<String, String> enrollment = new Enrollment<String, String>(courseCode, studentCode, year,
                            semester);
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
                    for (Enrollment<String, String> e : enrollments) {
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
                Lecturer lecturer = new Lecturer(id, name, initial, email, studyProgram);
                lecturers.add(lecturer);

            } else if (command.equals("course-add")) {
                String code = inputArray[1];
                String name = inputArray[2];
                int credits = Integer.parseInt(inputArray[3]);
                Grade grade =  Grade.valueOf(inputArray[4]);
                // String[] lecturersArray = inputArray[5].split(",");
                // String lecturerList = "";
                // for (int i = 0; i < lecturersArray.length; i++) {
                //     String lecturerInitial = lecturersArray[i];
                //     // mencari dosen dengan inisial yang sesuai
                //     for (Lecturer l : lecturers) {
                //         if (l.getInitial().equals(lecturerInitial)) {
                //             if (i != 0) {
                //                 lecturerList += ";"; // tambahkan ';' jika bukan dosen pertama
                //             }
                //             lecturerList += l.getInitial() + " (" + l.getEmail() + ")";
                //             break;
                //         }
                //     }
                // }
                Course course = new Course(code, name, credits);
                course.setGrade(grade);
                // course.setLecturer(lecturerList);
                courses.add(course);

            } else if (command.equals("student-details")) {
                String studentCode = inputArray[1];
                String[] courseString = new String[5];
                String[] nilai = new String[5];
                double totalGradePoints = 0;
                int totalCredits = 0;
                int i = 0;

                for (Enrollment<String, String> enrollment : enrollments) {
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
                                totalGradePoints += convertGradeToPoint(Grade.valueOf(nilai[j])) * course.getCredits();
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

            } else if (command.equals("enrollment-remedial")) {
                for (Enrollment<String, String> enrollment : enrollments) {
                    if (enrollment.getCode().equals(inputArray[1]) &&
                            enrollment.getNim().equals(inputArray[2]) &&
                            enrollment.getTahun().equals(inputArray[3]) &&
                            enrollment.getEven().equals(inputArray[4])) {
                        if (enrollment.getEven2().equals("None")) {
                            break;
                        } else {
                            if (enrollment.getTotalRemedi() == 0) {
                                enrollment.setPreviousGrade(inputArray[5]);
                                enrollment.swapGrade();
                                enrollment.setTotalRemedi();
                            }
                            else {
                                String previousGrade = enrollment.getPreviousGrade();
                                enrollment.setRemedial(previousGrade + "(" + inputArray[5] + ")");
                            }
                            break; // Keluar dari loop setelah menemukan entri yang sesuai
                        }
                    }
                }
            } else if (command.equals("course-open")){
                String coursecode = inputArray[1];
                String academicyear = inputArray[2];
                String semester = inputArray[3];
                String[] lecturersArray = inputArray[4].split(",");
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
                    boolean haslec = false;
                        for(Lecturer lec : lecturers){
                            if(lec.getInitial().equals(lecturerList)){
                                haslec = true;
                            }
                        }
                        
                        for (Course course : courses){
                            if(course.getCode().equals(coursecode)){
                                haslec = true;
                        }

                        if(haslec){
                            CourseOpening<String> courseOpening = new CourseOpening<String>(coursecode, academicyear, semester, lecturerList);
                            courseOpenings.add(courseOpening);
                        }
                    
                 }
            } else if (command.equals("course-history")){
                courseOpenings.sort((c1, c2) -> c2.getSemester().compareTo(c1.getSemester()));
                for (CourseOpening<String> courseOpening : courseOpenings){
                    String matkul = "";
                    String credit = "";
                    String grade = "";

                    for (Course crs : courses){
                        if(crs.getCode().equals(courseOpening.getcoursecode())){
                            matkul = crs.getStudyProgram();
                            credit = Integer.toString(crs.getCredits());
                            grade = crs.getGrade().toString();
                        }
                    System.out.println(courseOpening.getcoursecode() + "|" + matkul + "|" + credit + "|" + grade +"|"+ courseOpening.getAcademicyear() +"|"+ courseOpening.getSemester() + "|"+ courseOpening.getLecturalList());
                    } for (Enrollment<String, String> enrollment : enrollments){
                        if(enrollment.getCode().equals(courseOpening.getcoursecode()) && enrollment.getTahun().equals(courseOpening.getAcademicyear()) && enrollment.getEven().equals(courseOpening.getSemester())){
                            if (!enrollment.getPreviousGrade().equals("")) {
                                    System.out.println(enrollment.toStringRemedial());
                                } else {
                                    System.out.println(enrollment.toString());
                                }
                            
                        }
                    }
                }
            } 
            
            else if (command.equals("find-the-best-student")) {
                String academicYear = inputArray[1];
                String semester = inputArray[2];
            
                // Membuat daftar mahasiswa dari semester ganjil dan genap
                ArrayList<Student> oddSemesterStudents = new ArrayList<>();
                ArrayList<Student> evenSemesterStudents = new ArrayList<>();
            
                // Mengumpulkan nilai mahasiswa dari semester ganjil dan genap
                for (Enrollment<String, String> enrollment : enrollments) {
                    if (enrollment.getTahun().equals(academicYear) && enrollment.getEven().equals("odd")) {
                        for (Student student : students) {
                            if (student.getCode().equals(enrollment.getNim())) {
                                oddSemesterStudents.add(student);
                                break;
                            }
                        }
                    } else if (enrollment.getTahun().equals(academicYear) && enrollment.getEven().equals("even")) {
                        for (Student student : students) {
                            if (student.getCode().equals(enrollment.getNim())) {
                                evenSemesterStudents.add(student);
                                break;
                            }
                        }
                    }
                }
            
                // Mengidentifikasi mahasiswa terbaik berdasarkan performa mereka
                ArrayList<String> bestStudents = new ArrayList<>();
                for (Student oddStudent : oddSemesterStudents) {
                    for (Student evenStudent : evenSemesterStudents) {
                        if (oddStudent.getCode().equals(evenStudent.getCode())) {
                            if (oddStudent.getGpa() < evenStudent.getGpa()) {
                                bestStudents.add(oddStudent.getCode() + "|" + oddStudent.getGrade() + "/" + evenStudent.getGrade());
                            }
                        }
                    }
                }
            
                // Menampilkan mahasiswa terbaik
                for (String student : bestStudents) {
                    System.out.println(student);
                }
            } else if (command.equals("add-best-student")) {
                // Setelah mendapatkan nama-nama mahasiswa terbaik, tambahkanlah mereka ke dalam list mahasiswa terbaik
                ArrayList<String> bestStudentsList = new ArrayList<>();
                for (int i = 1; i < inputArray.length; i++) {
                    bestStudentsList.add(inputArray[i]);
                }
            
                // Menampilkan mahasiswa terbaik
                for (String student : bestStudentsList) {
                    System.out.println(student);
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
            System.out.println(lecturer.toString());
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
                    + course.getGrade());
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

        for (Enrollment<String, String> enrollment : enrollments) {
                    
            if (!enrollment.getPreviousGrade().equals("")) {
                System.out.println(enrollment.toStringRemedial());
            } else {
                System.out.println(enrollment.toString());
            }
        }
        masukan.close();
    }

    private static double convertGradeToPoint(Grade grade) {
        return Grade.convertGradeToPoint(grade);
    }
}
