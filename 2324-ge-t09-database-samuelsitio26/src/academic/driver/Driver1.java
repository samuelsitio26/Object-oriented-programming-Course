package academic.driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 12S22032 Samuel Sitio
 */

import academic.model.Enrollment;
import academic.model.Lecturer;
import academic.model.Grade;
import academic.model.ContactDatabase;

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

        // Database
        String url = "jdbc:mysql://localhost:3306/tugas09_pbo";
        String username = "root";
        String password = "Samuel=26012004";

        // Melakukan koneksi ke database
        Connection connection = null;
        try {
            // Memuat driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database
            connection = DriverManager.getConnection(url, username, password);
            // System.out.println("Koneksi ke database MySQL berhasil!");

            ContactDatabase database = new ContactDatabase(url, username, password);
            database.createTables();
            database.cleanUpTables();

            // Mematikan otomatis commit
            connection.setAutoCommit(false);

            Scanner masukan = new Scanner(System.in);
            ArrayList<Enrollment<String, String>> enrollments = new ArrayList<>(); // menggunakan tipe data generic
            ArrayList<Lecturer> lecturers = new ArrayList<>();
            ArrayList<Enrollment<String, String>> studentEnrollments = new ArrayList<>(); // menggunakan tipe data  generic

            // Local Class untuk melakukan pengolahan data pada metode main
            class DataProcessor {
                void processEnrollment(String courseCode, String studentCode, String year, String semester,
                        ArrayList<Enrollment<String, String>> enrollments) {
                    boolean cek_enrollment = false;
                    for (Enrollment<String, String> e : enrollments) {
                        if (e.getCode().equals(courseCode) && e.getNim().equals(studentCode)
                                && e.getTahun().equals(year)
                                && e.getEven().equals(semester)) {
                            cek_enrollment = true;
                        }
                    }
                    if (!cek_enrollment) {
                        Enrollment<String, String> enrollment = new Enrollment<String, String>(courseCode, studentCode,
                                year,
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
                    String lec = "SELECT * FROM lecturer";
                    Statement lecState = connection.createStatement();
                    ResultSet lecResult = lecState.executeQuery(lec);
                    while (lecResult.next()) {
                        String id = lecResult.getString("nim");
                        String name = lecResult.getString("name");
                        String initial = lecResult.getString("initial");
                        String email = lecResult.getString("email");
                        String studyProgram = lecResult.getString("study_program");
                        System.out.println(id + "|" + name + "|" + initial + "|" + email + "|" + studyProgram);
                    }

                    String crs = "SELECT * FROM course";
                    Statement crsState = connection.createStatement();
                    ResultSet crsResult = crsState.executeQuery(crs);
                    while (crsResult.next()) {
                        String code = crsResult.getString("code");
                        String name = crsResult.getString("name");
                        int credits = crsResult.getInt("credits");
                        String grade = crsResult.getString("grade");
                        System.out.println(code + "|" + name + "|" + credits + "|" + grade);
                    }

                    String std = "SELECT * FROM students";
                    Statement stdState = connection.createStatement();
                    ResultSet stdResult = stdState.executeQuery(std);
                    while (stdResult.next()) {
                        String nim = stdResult.getString("nim");
                        String name = stdResult.getString("name");
                        String year = stdResult.getString("year");
                        String program = stdResult.getString("program_study");
                        System.out.println(nim + "|" + name + "|" + year + "|" + program);
                    }

                    String enr = "SELECT * FROM enrollment";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(enr);
                    while (resultSet.next()) {
                        String course_code = resultSet.getString("course_code");
                        String nim = resultSet.getString("nim");
                        String year = resultSet.getString("year");
                        String semester = resultSet.getString("semester");
                        String grade = resultSet.getString("grade");
                        System.out.println(course_code + "|" + nim + "|" + year + "|" + semester + "|" + grade);
                    }

                    break;
                }

                // Melakukan operasi-operasi yang diperlukan

                String[] inputArray = input.split("#");
                String command = inputArray[0];

                if (command.equals("student-add")) {
                    String code = inputArray[1];
                    String name = inputArray[2];
                    String year = inputArray[3];
                    String program = inputArray[4];

                    // Menyimpan data ke database
                    String sql = "INSERT INTO students (nim, name, year, program_study) VALUES (?, ?, ?, ?)"; //placeholder (?)
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, code);
                    statement.setString(2, name);
                    statement.setString(3, year);
                    statement.setString(4, program);
                    statement.executeUpdate();
                    statement.close();

                } else if (command.equals("enrollment-add")) {
                    dataProcessor.processEnrollment(inputArray[1], inputArray[2], inputArray[3], inputArray[4],
                            enrollments);

                    // Menyimpan data ke database
                    String sql = "INSERT INTO enrollment (nim, course_code, year, semester, grade, remed) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, inputArray[2]);
                    statement.setString(2, inputArray[1]);
                    statement.setString(3, inputArray[3]);
                    statement.setString(4, inputArray[4]);
                    statement.setString(5, "None");
                    statement.setString(6, "None");
                    statement.executeUpdate();
                    statement.close();

                } else if (command.equals("enrollment-grade")) {
                    String sql = "UPDATE enrollment SET grade = ? WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
                    String course_code = inputArray[1];
                    String nim = inputArray[2];
                    String year = inputArray[3];
                    String semester = inputArray[4];
                    String grade = inputArray[5];
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, grade);
                    preparedStatement.setString(2, course_code);
                    preparedStatement.setString(3, nim);
                    preparedStatement.setString(4, year);
                    preparedStatement.setString(5, semester);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } else if (command.equals("lecturer-add")) {
                    String id = inputArray[1];
                    String name = inputArray[2];
                    String initial = inputArray[3];
                    String email = inputArray[4];
                    String studyProgram = inputArray[5];
                    Lecturer lecturer = new Lecturer(id, name, initial, email, studyProgram);
                    lecturers.add(lecturer);

                    // Menyimpan data ke database
                    String sql = "INSERT INTO lecturer (nim, name, initial, email, study_program) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, id);
                    statement.setString(2, name);
                    statement.setString(3, initial);
                    statement.setString(4, email);
                    statement.setString(5, studyProgram);
                    statement.executeUpdate();
                    statement.close();

                } else if (command.equals("course-add")) {
                    String code = inputArray[1];
                    String name = inputArray[2];
                    int credits = Integer.parseInt(inputArray[3]);
                    Grade grade = Grade.valueOf(inputArray[4]);

                    // Menyimpan data ke database
                    String sql = "INSERT INTO course (code, name, credits, grade) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, code);
                    statement.setString(2, name);
                    statement.setInt(3, credits);
                    statement.setString(4, grade.toString());
                    statement.executeUpdate();
                    statement.close();

                } else if (command.equals("student-details")) {
                    String studentCode = inputArray[1];
                    String sql = "SELECT * FROM students AS s " +
                            "RIGHT JOIN enrollment AS e ON s.nim = e.nim " +
                            "WHERE s.nim = ? AND (e.course_code, e.year) IN " +
                            "(SELECT course_code, MAX(year) FROM enrollment WHERE nim = ? GROUP BY course_code)";

                    int totalCredit = 0;
                    double countGpa = 0;
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, studentCode);
                    statement.setString(2, studentCode);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        String code = resultSet.getString("course_code");
                        String grade = resultSet.getString("grade");
                        String[] gradeSplit = grade.split("\\(");
                        sql = "SELECT * FROM course WHERE code = ?";
                        PreparedStatement statement2 = connection.prepareStatement(sql);
                        statement2.setString(1, code);
                        ResultSet resultSet2 = statement2.executeQuery();
                        while (resultSet2.next()) {
                            int credits = resultSet2.getInt("credits");
                            totalCredit += credits;
                            countGpa += convertGradeToPoint(Grade.valueOf(gradeSplit[0])) * credits;
                        }
                    }

                    double gpa = countGpa / totalCredit;
                    sql = "SELECT * FROM students WHERE nim = ?";
                    PreparedStatement statement3 = connection.prepareStatement(sql);
                    statement3.setString(1, studentCode);
                    ResultSet resultSet3 = statement3.executeQuery();
                    while (resultSet3.next()) {
                        String name = resultSet3.getString("name");
                        String year = resultSet3.getString("year");
                        String program = resultSet3.getString("program_study");
                        System.out.println(studentCode + "|" + name + "|" + year + "|" + program + "|"
                                + String.format("%.2f", gpa) + "|" + totalCredit);
                    }

                } else if (command.equals("student-transcript")) {
                    String studentCode = inputArray[1];
                    String sql = "SELECT * FROM students AS s " +
                            "RIGHT JOIN enrollment AS e ON s.nim = e.nim " +
                            "WHERE s.nim = ? AND (e.course_code, e.year) IN " +
                            "(SELECT course_code, MAX(year) FROM enrollment WHERE nim = ? GROUP BY course_code)";

                    int totalCredit = 0;
                    double countGpa = 0;
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, studentCode);
                    statement.setString(2, studentCode);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        String code = resultSet.getString("course_code");
                        String grade = resultSet.getString("grade");
                        String[] gradeSplit = grade.split("\\(");
                        sql = "SELECT * FROM course WHERE code = ?";
                        PreparedStatement statement2 = connection.prepareStatement(sql);
                        statement2.setString(1, code);
                        ResultSet resultSet2 = statement2.executeQuery();
                        while (resultSet2.next()) {
                            int credits = resultSet2.getInt("credits");
                            totalCredit += credits;
                            countGpa += convertGradeToPoint(Grade.valueOf(gradeSplit[0])) * credits;
                        }
                    }

                    double gpa = countGpa / totalCredit;
                    sql = "SELECT * FROM students WHERE nim = ?";
                    PreparedStatement statement3 = connection.prepareStatement(sql);
                    statement3.setString(1, studentCode);
                    ResultSet resultSet3 = statement3.executeQuery();
                    while (resultSet3.next()) {
                        String name = resultSet3.getString("name");
                        String year = resultSet3.getString("year");
                        String program = resultSet3.getString("program_study");
                        System.out.println(studentCode + "|" + name + "|" + year + "|" + program + "|"
                                + String.format("%.2f", gpa) + "|" + totalCredit);

                        sql = "SELECT e1.* FROM enrollment e1 " +
                                "LEFT JOIN enrollment e2 ON e1.course_code = e2.course_code " +
                                "AND e1.nim = e2.nim AND e1.year < e2.year " +
                                "WHERE e1.nim = ? AND e2.course_code IS NULL";
                        PreparedStatement statement4 = connection.prepareStatement(sql);
                        statement4.setString(1, studentCode);
                        ResultSet resultSet4 = statement4.executeQuery();
                        while (resultSet4.next()) {
                            System.out.println(resultSet4.getString("course_code") + "|" + resultSet4.getString("nim")
                                    + "|" + resultSet4.getString("year") + "|" + resultSet4.getString("semester") + "|"
                                    + resultSet4.getString("grade"));
                        }
                    }

                } else if (command.equals("enrollment-remedial")) {
                    String courseCode = inputArray[1];
                    String studentCode = inputArray[2];
                    String year = inputArray[3];
                    String semester = inputArray[4];
                    String newGrade = inputArray[5];

                    String sql = "SELECT * FROM enrollment WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, courseCode);
                    statement.setString(2, studentCode);
                    statement.setString(3, year);
                    statement.setString(4, semester);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        String grade = resultSet.getString("grade");
                        String remed = resultSet.getString("remed");
                        if (remed.equals("None")) {
                            sql = "UPDATE enrollment SET remed = ? WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
                            String remedString = "Done";
                            PreparedStatement state = connection.prepareStatement(sql);
                            state.setString(1, remedString);
                            state.setString(2, courseCode);
                            state.setString(3, studentCode);
                            state.setString(4, year);
                            state.setString(5, semester);
                            state.executeUpdate();
                            state.close();
                            sql = "UPDATE enrollment SET grade = ? WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
                            PreparedStatement statement2 = connection.prepareStatement(sql);
                            statement2.setString(1, newGrade + "(" + grade + ")");
                            statement2.setString(2, courseCode);
                            statement2.setString(3, studentCode);
                            statement2.setString(4, year);
                            statement2.setString(5, semester);
                            statement2.executeUpdate();
                            statement2.close();
                        }
                    }

                } else if (command.equals("course-open")) {
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

                    // Menyimpan data ke database course-open yang join dengan course dan lecturer
                    String sql = "INSERT INTO courseopening (course_code, year, semester, lecturer) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, coursecode);
                    statement.setString(2, academicyear);
                    statement.setString(3, semester);
                    statement.setString(4, lecturerList);
                    statement.executeUpdate();
                    statement.close();

                } else if (command.equals("course-history")) {
                    String sql = "SELECT * FROM course LEFT JOIN courseopening ON course.code = courseopening.course_code WHERE course.code = ? ORDER BY semester DESC";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, inputArray[1]);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("code") + "|" + resultSet.getString("name") + "|"
                                + resultSet.getInt("credits") + "|" + resultSet.getString("grade") + "|"
                                + resultSet.getString("year") + "|" + resultSet.getString("semester") + "|"
                                + resultSet.getString("lecturer"));
                        String academicYear = resultSet.getString("year");
                        String semester = resultSet.getString("semester");

                        sql = "SELECT * FROM enrollment WHERE course_code = ? AND year = ? AND semester = ?";
                        PreparedStatement statement2 = connection.prepareStatement(sql);
                        statement2.setString(1, inputArray[1]);
                        statement2.setString(2, academicYear);
                        statement2.setString(3, semester);
                        ResultSet resultSet2 = statement2.executeQuery();
                        while (resultSet2.next()) {
                            System.out.println(resultSet2.getString("course_code") + "|" + resultSet2.getString("nim")
                                    + "|" + resultSet2.getString("year") + "|" + resultSet2.getString("semester") + "|"
                                    + resultSet2.getString("grade"));
                        }
                    }
                }
            }

            masukan.close();

            database.shutdown();
            
            connection.commit();
        } catch (ClassNotFoundException e) {
            System.out.println("Tidak dapat menemukan driver JDBC MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal!");
            // Rollback transaksi jika terjadi kesalahan
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Kembalikan mode otomatis commit ke default  connection.setAutoCommit(true);
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static double convertGradeToPoint(Grade grade) {
        return Grade.convertGradeToPoint(grade);
    }
}