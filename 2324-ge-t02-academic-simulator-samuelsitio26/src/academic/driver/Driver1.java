package academic.driver;

import java.util.Scanner;

import java.util.ArrayList;
import academic.model.Course; 

/**
 * @author 12S22032 Samuel Sitio
 * @author NIM Nama
 */
public class Driver1 {

    public static void main(String[] _args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Course> courses = new ArrayList<Course>();

        while (true){
            //membaca masukan
            String input = masukan.nextLine();

            //menghentikan program jika input adalah "---"
            if (input.equals("---")){
                break;
            }

            //memecahkan masukan menjadi 4 segmen
            String[] inputArray = input.split("#");
            String code = inputArray[0];
            String studyProgram = inputArray[1];
            int credits = Integer.parseInt(inputArray[2]);
            String grade = inputArray[3];

            //membuat objek course
            Course course = new Course(code, studyProgram, credits);
            course.setGrade(grade);

            //menambahkan course ke dalam arraylist
            courses.add(course);
            //mengeluarkan 12S2203|Object-oriented Programming|3|C
            System.out.println(course.getCode() + "|" + course.getStudyProgram() + "|" + course.getCredits() + "|" + course.getGrade());
        }
        masukan.close();


    }

}