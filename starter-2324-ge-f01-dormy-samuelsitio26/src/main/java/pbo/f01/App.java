package pbo.f01;

import java.util.Scanner;

import pbo.f01.model.*;
/**
 * @author 12S22032 Samuel Sitio
 * @author 12S22024 Pimpin loi
 */
public class App {
    public static void main(String[] _args) {
        Constrac constrac = new Constrac("dormy_pu");
        Scanner input = new Scanner(System.in);
        boolean loop = true;
        constrac.cleanTables();

        while (loop) {
            String data = input.nextLine();
            String[] newData = data.split("#");

            if (newData[0].equals("---")) {
                loop = false;
            } else if (newData[0].equals("student-add")) {
                if (constrac.checkDuplicateStudent(newData[1]) == 0) {
                    constrac.addStudentToDatabase(newData);
                }
            } else if (newData[0].equals("dorm-add")) {
                if (constrac.checkDuplicateDorm(newData[1]) == 0) {
                    constrac.addDormToDatabase(newData);
                }
            } else if (newData[0].equals("assign")) {
                if (constrac.isValidAssignment(newData[1], newData[2]) == 1) {
                    constrac.assignStudentToDorm(newData[1], newData[2]);
                }
            } else if (newData[0].equals("display-all")) {
                constrac.displayAllDormsAndStudents();
            }
        }

        constrac.cleanTables();
        constrac.close();
        input.close();
    }
}