package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;

import academic.model.Student;
/**
 * @author 12S22032 Samuel Sitio
 * @author NIM Nama
 */
public class Driver2 { 

    public static void main(String[] _args) {
        Scanner masukan = new Scanner(System.in);
        ArrayList<Student>enrollments = new ArrayList<Student>();
    
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
            String name = inputArray[1];
            String tahun = inputArray[2];
            String studyProgram = inputArray[3];

            //membuat objek enrollment
            Student enrollment = new Student(code, name, tahun, studyProgram);
            //menambahkan enrollment ke dalam arraylist
            enrollments.add(enrollment);
            //mengeluarkan 12S20999|Wiro Sableng|2020|Information Systems
            System.out.println(enrollment.getCode() + "|" + enrollment.getName() + "|" + enrollment.getTahun() + "|" + enrollment.getProgamstudy());

        }
        masukan.close();
    }
}
