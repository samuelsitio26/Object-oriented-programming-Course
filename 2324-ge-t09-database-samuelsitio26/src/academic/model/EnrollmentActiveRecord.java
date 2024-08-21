package academic.model;
import java.sql.*;
public class EnrollmentActiveRecord {
    private String courseCode;
    private String nim;
    private String year;
    private String semester;
    private String grade;
    private String remed;

    // Constructors, getters, and setters 
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemed() {
        return remed;
    }

    public void setRemed(String remed) {
        this.remed = remed;
    }


    // Save method to insert or update the record
    public void save(Connection connection) throws SQLException {
        if (exists(connection)) {
            update(connection);
        } else {
            insert(connection);
        }
    }

    // Insert method
    private void insert(Connection connection) throws SQLException {
        String sql = "INSERT INTO enrollment (course_code, nim, year, semester, grade, remed) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, courseCode);
        statement.setString(2, nim);
        statement.setString(3, year);
        statement.setString(4, semester);
        statement.setString(5, grade);
        statement.setString(6, remed);
        statement.executeUpdate();
        statement.close();
    }

    // Update method
    private void update(Connection connection) throws SQLException {
        String sql = "UPDATE enrollment SET grade = ?, remed = ? WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, grade);
        statement.setString(2, remed);
        statement.setString(3, courseCode);
        statement.setString(4, nim);
        statement.setString(5, year);
        statement.setString(6, semester);
        statement.executeUpdate();
        statement.close();
    }

    // Check if the record exists in the database
    private boolean exists(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM enrollment WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, courseCode);
        statement.setString(2, nim);
        statement.setString(3, year);
        statement.setString(4, semester);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("count");
        statement.close();
        return count > 0;
    }

    // Find method to retrieve a record by its primary key
    public static EnrollmentActiveRecord find(String courseCode, String nim, String year, String semester, Connection connection) throws SQLException {
        String sql = "SELECT * FROM enrollment WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, courseCode);
        statement.setString(2, nim);
        statement.setString(3, year);
        statement.setString(4, semester);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            EnrollmentActiveRecord enrollment = new EnrollmentActiveRecord();
            enrollment.setCourseCode(resultSet.getString("course_code"));
            enrollment.setNim(resultSet.getString("nim"));
            enrollment.setYear(resultSet.getString("year"));
            enrollment.setSemester(resultSet.getString("semester"));
            enrollment.setGrade(resultSet.getString("grade"));
            enrollment.setRemed(resultSet.getString("remed"));
            return enrollment;
        } else {
            return null;
        }
    }

    // Delete method
    public void delete(Connection connection) throws SQLException {
        String sql = "DELETE FROM enrollment WHERE course_code = ? AND nim = ? AND year = ? AND semester = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, courseCode);
        statement.setString(2, nim);
        statement.setString(3, year);
        statement.setString(4, semester);
        statement.executeUpdate();
        statement.close();
    }

    // Other methods if needed
}
