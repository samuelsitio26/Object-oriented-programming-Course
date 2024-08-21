package academic.model;
import java.sql.*;
public class StudentMapper {
    private Connection connection;

    public StudentMapper(Connection connection) {
        this.connection = connection;
    }

    public void insert(Student student) throws SQLException {
        String sql = "INSERT INTO students (nim, name, year, program_study) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getId());
        statement.setString(2, student.getName());
        statement.setString(3, student.getYear());
        statement.setString(4, student.getProgamstudy());
        statement.executeUpdate();
        statement.close();
    }

    // Other methods like update, delete, findById, etc. 
    
    public void delete(Student student) throws SQLException {
        String sql = "DELETE FROM students WHERE nim = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getId());
        statement.executeUpdate();
        statement.close();
    }

    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, year = ?, program_study = ? WHERE nim = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, student.getName());
        statement.setString(2, student.getYear());
        statement.setString(3, student.getProgamstudy());
        statement.setString(4, student.getId());
        statement.executeUpdate();
        statement.close();
    }
}
