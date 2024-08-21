package academic.driver;
import java.util.Scanner;
import java.util.ArrayList;


import academic.model.Student;
/**
 * @author 12S22032 Samuel Sitio
 */
public class Driver1 {

    public static void main(String[] _args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<Student>();

        while (masukan.hasNextLine()) {
            String input = masukan.nextLine();

            if (input.equals("---")) {
                break;
            }

            String[] data = input.split("#");
            String command = data[0];

            if (command.equals("student-add")) {
                String id = data[1];
                String name = data[2];
                double gpa = Double.parseDouble(data[3]);
                int credit = Integer.parseInt(data[4]);
                students.add(new Student(id, name, gpa, credit));
            } else if (command.equals("student-show-all")) {
                for (Student student : students) {
                    System.out.println(student.getId() + "|" + student.getName() + "|" + student.getGpa() + "|" + student.getCredit());
                }
            }
        }
        masukan.close();

    }

}