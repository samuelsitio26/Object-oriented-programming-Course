package academic.model;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 12S22032 Samuel Sitio
 */

public class ContactDatabase extends AbstractDb {
    public ContactDatabase(String url, String username, String password) throws SQLException {
        super(url, username, password);
    }

    public void createTables() throws SQLException {

        // create table student with column nim, nama, year, program_study
        String students = " CREATE TABLE IF NOT EXISTS students ("
                + "nim VARCHAR(10) NOT NULL,"
                + "name VARCHAR(50) NOT NULL,"
                + "year VARCHAR(5) NOT NULL,"
                + "program_study VARCHAR(30) NOT NULL,"
                + "PRIMARY KEY(nim)"
                + ");";

        // create table lecturer with column nim, name, initial, email, study_program
        String lecturer = " CREATE TABLE IF NOT EXISTS lecturer ("
                + "nim VARCHAR(40) NOT NULL,"
                + "name VARCHAR(50) NOT NULL,"
                + "initial VARCHAR(5) NOT NULL,"
                + "email VARCHAR(50) NOT NULL,"
                + "study_program VARCHAR(40) NOT NULL"
                + ");";

        // create table course nim, mata_kuliah, sks, gread
        String course = " CREATE TABLE IF NOT EXISTS course ("
                + "code VARCHAR(20) NOT NULL,"
                + "name VARCHAR(50) NOT NULL,"
                + "credits VARCHAR(5) NOT NULL,"
                + "grade VARCHAR(5) NOT NULL,"
                + "PRIMARY KEY(code)"
                + ");";

        // create table enrollment-add#12S1101#12S20001#2020/2021#odd nim, course_code,
        // year, semester
        String enrollment = " CREATE TABLE IF NOT EXISTS enrollment ("
                + "course_code VARCHAR(50) NOT NULL,"
                + "nim VARCHAR(20) NOT NULL,"
                + "year VARCHAR(10) NOT NULL,"
                + "semester VARCHAR(10) NOT NULL,"
                + "grade VARCHAR(10) NOT NULL,"
                + "remed VARCHAR(10) NOT NULL"
                + ");";

        // crate table course-open#12S2102#2022/2023#odd#IUS,RSL nim, year, semester,
        // lecturer join table course with column code, year, semester, lecturer
        String courseOpening = " CREATE TABLE IF NOT EXISTS courseopening ("
                + "course_code VARCHAR(40) NOT NULL,"
                + "year VARCHAR(10) NOT NULL,"
                + "semester VARCHAR(10) NOT NULL,"
                + "lecturer VARCHAR(100) NOT NULL"
                + ");";

        // step 3
        Statement statement = this.getConection().createStatement();
        statement.execute(students);
        statement.execute(lecturer);
        statement.execute(course);
        statement.execute(enrollment);
        statement.execute(courseOpening);

        // step 5
        statement.close();
    }

    public void cleanUpTables() throws SQLException {
        String cleanUpSQL[] = {
                "DELETE FROM lecturer",
                "DELETE FROM course",
                "DELETE FROM enrollment",
                "DELETE FROM students",
                "DELETE FROM courseopening"
        };

        Statement statement = this.getConection().createStatement();

        for (String sql : cleanUpSQL) {
            statement.execute(sql);
        }

        statement.close();
    }
}