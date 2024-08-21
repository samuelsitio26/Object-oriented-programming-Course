package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/**
 * @author 12S22032 Samuel Sitio
 */

import academic.model.Course;
import academic.model.CourseOpening;
import academic.model.Enrollment;
import academic.model.Student;
import academic.model.Lecturer;
import academic.model.Grade;

public class Driver1 {
        // Static Nested Class untuk konversi nilai Grade menjadi poin
        static class GradeConverter {
            static double convertGradeToPoint(Grade grade) {
                switch (grade) {
                    case A:
                        return 4.0;
                    case AB:
                        return 3.5;
                    case B:
                        return 3.0;
                    case BC:
                        return 2.5;
                    case C:
                        return 2.0;
                    case D:
                        return 1.0;
                    case E:
                        return 0.0;
                    default:
                        return 0.0;
                }
            }
        }

    public static void main(String[] args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Enrollment<String, String>> enrollments = new ArrayList<>(); // menggunakan tipe data generic
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ArrayList<Enrollment<String, String>> studentEnrollments = new ArrayList<>(); // menggunakan tipe data generic
        ArrayList<CourseOpening<String>> courseOpenings = new ArrayList<>(); // menggunakan tipe data generic
        // Local Class untuk melakukan pengolahan data pada metode main
        class DataProcessor {
            void processEnrollment(String courseCode, String studentCode, String year, String semester, ArrayList<Enrollment<String, String>> enrollments) {
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
            }
        }

        DataProcessor dataProcessor = new DataProcessor();

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
                // String courseCode = inputArray[1];
                // String studentCode = inputArray[2];
                // String year = inputArray[3];
                // String semester = inputArray[4];
                // boolean cek_enrollment = false;
                // for (Enrollment<String, String> e : enrollments) {
                //     if (e.getCode().equals(courseCode) && e.getNim().equals(studentCode) && e.getTahun().equals(year)
                //             && e.getEven().equals(semester)) {
                //         cek_enrollment = true;
                //     }
                // }
                // if (!cek_enrollment) {
                //     Enrollment<String, String> enrollment = new Enrollment<String, String>(courseCode, studentCode, year,
                //             semester);
                //     enrollments.add(enrollment);
                //     studentEnrollments.add(enrollment);
                // }
                dataProcessor.processEnrollment(inputArray[1], inputArray[2], inputArray[3], inputArray[4], enrollments);

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
                courses.add(course);
                // course.setLecturer(lecturerList);

            } else if (command.equals("student-details")) {
                String[] courseString = new String[5];
                String studentCode = inputArray[1];
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
                        if (crs.getCode().equals(inputArray[1]) && crs.getCode().equals(courseOpening.getcoursecode()) && 
                        crs.getStudyProgram().equals(matkul) && Integer.toString(crs.getCredits()).equals(credit) && crs.getGrade().toString().equals(grade)){
                            System.out.println(courseOpening.getcoursecode() + "|" + matkul + "|" + credit + 
                            "|" + grade +"|"+ courseOpening.getAcademicyear() +"|"+ courseOpening.getSemester() + "|"+ courseOpening.getLecturalList());
                            break;
                        }
                        
                    } 
                    for (Enrollment<String, String> enrollment : enrollments){
                        if(enrollment.getCode().equals(inputArray[1]) && enrollment.getCode().equals(courseOpening.getcoursecode()) && enrollment.getTahun().equals(courseOpening.getAcademicyear()) && enrollment.getEven().equals(courseOpening.getSemester())){
                            if (!enrollment.getPreviousGrade().equals("")) {
                                    System.out.println(enrollment.toStringRemedial());
                                } else {
                                    System.out.println(enrollment.toString());
                                }
                            
                        }
                    }
                }

            } else if (command.equals("student-transcript")){
                String studentCode = inputArray[1];
                String[] courseString = new String[5];
                String[] nilai = new String[5];
                double totalGradePoints = 0;
                int totalCredits = 0;
                int i = 0;

                String[] tahunSemester = new String[5];
                for (Enrollment<String, String> enrollment : enrollments) {
                    if (enrollment.getNim().equals(studentCode)) {
                        courseString[i] = enrollment.getCode();
                        tahunSemester[i] = enrollment.getTahun() + "|" + enrollment.getEven();
                        nilai[i] = enrollment.getEven2();
                        i++;
                    }
                }

                // for (int j = 0; j < i; j++) {
                //     for (int k = j + 1; k < i; k++) {
                //         if (courseString[j] != null && courseString[k] != null && courseString[j].equals(courseString[k])){
                //             // If there's a duplicate course, update the grade
                //             nilai[j] = nilai[k];
                //             // Set the duplicate course and grade to null
                //             courseString[k] = null;
                //             nilai[k] = null;
                //         }
                //     }
                // }

                //1
                String coursecode = "";
                String academicyear = "";
                for(int x = 0; x < enrollments.size(); x++){
                    for(int y = x+1; y < enrollments.size(); y++){
                        if(enrollments.get(x).getNim().equals(studentCode) && enrollments.get(y).getNim().equals(studentCode) && enrollments.get(x).getCode().equals(enrollments.get(y).getCode())){
                            coursecode = enrollments.get(x).getCode();
                            academicyear = enrollments.get(x).getTahun();
                            x = enrollments.size();
                            break;
                        }
                    }
                } for(int x = 0; x < enrollments.size(); x++){
                    if(enrollments.get(x).getNim().equals(studentCode) && enrollments.get(x).getCode().equals(coursecode) && enrollments.get(x).getTahun().equals(academicyear)){
                       continue;
                    }else{
                        if(enrollments.get(x).getNim().equals(studentCode)){
                            for(Course course : courses){
                                if(course.getCode().equals(enrollments.get(x).getCode())){
                                    totalGradePoints += convertGradeToPoint(Grade.valueOf(enrollments.get(x).getEven2())) * course.getCredits();
                                    totalCredits += course.getCredits();
                                    break;
                                }
    
                            }

                        }
                    }
                }
                // for (int j = 0; j < i; j++) {
                //     if (courseString[j] != null && nilai[j] != null) {
                //         for (Course course : courses) {
                //             if (course.getCode().equals(courseString[j])) {
                //                 totalGradePoints += convertGradeToPoint(Grade.valueOf(nilai[j])) * course.getCredits();
                //                 totalCredits += course.getCredits();
                //             }
                //         }
                //     }
                // }

                double gpa = totalGradePoints / totalCredits;

                if (totalGradePoints == 0.0) {
                    totalCredits = 0;   
                }
                    
                for (Student student : students) {
                    if (student.getCode().equals(studentCode)) {
                        System.out.println(student.getCode() + "|" + student.getName() + "|" + student.getTahun() + "|" + student.getProgamstudy() + "|" + String.format("%.2f", gpa) + "|" + totalCredits);
                    }
                }

                HashMap<String, Enrollment<String, String>> latestEnrollments = new HashMap<>();

                // Ulangi pendaftaran
                for (Enrollment<String, String> enrollment : enrollments) {
                    // Dapatkan kode kursus dari pendaftaran saat ini
                    String courseCode = enrollment.getCode();
                
                    // Periksa apakah pendaftaran ditujukan untuk siswa yang ditentukan
                    if (enrollment.getNim().equals(studentCode)) {
                        // Periksa apakah kode kursus sudah ada di HashMap
                        if (latestEnrollments.containsKey(courseCode)) {
                            // Dapatkan pendaftaran terbaru untuk kursus saat ini
                            Enrollment<String, String> latestEnrollment = latestEnrollments.get(courseCode);
                
                            // Bandingkan tahun pendaftaran saat ini dan pendaftaran terakhir
                            int yearComparison = enrollment.getTahun().compareTo(latestEnrollment.getTahun());
                
                            // Jika pendaftaran saat ini memiliki tahun yang lebih baru, perbarui pendaftaran terbaru
                            if (yearComparison > 0) {
                                latestEnrollments.put(courseCode, enrollment);
                            } else if (yearComparison == 0) {
                                // Jika tahunnya sama, bandingkan semesternya
                                int semesterComparison = enrollment.getEven().compareTo(latestEnrollment.getEven());
                                // Jika pendaftaran saat ini memiliki semester baru, perbarui pendaftaran terbaru
                                if (semesterComparison > 0) {
                                    latestEnrollments.put(courseCode, enrollment);
                                }
                            }
                        } else {
                            // Jika kode kursus belum ada di HashMap, tambahkan pendaftaran saat ini
                            latestEnrollments.put(courseCode, enrollment);
                        }
                    }
                }
                
                // Print the sorted enrollments
                for (Enrollment<String, String> enrollment : latestEnrollments.values()) {
                    if (!enrollment.getPreviousGrade().equals("")) {
                        System.out.println(enrollment.toStringRemedial());
                    } else {
                        System.out.println(enrollment.toString());
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
