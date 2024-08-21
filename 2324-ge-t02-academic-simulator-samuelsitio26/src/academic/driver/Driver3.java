package academic.driver;

import java.util.ArrayList;
import java.util.Scanner;

import academic.model.Enrollment; 
/**
 * @author 12S22032 Samuel Sitio
 * @author NIM Nama
 */
public class Driver3 {

    public static void main(String[] _args) {
        ArrayList<Enrollment> students = new ArrayList<Enrollment>();
        Scanner masukan = new Scanner(System.in);
        
        while(true){
        //membaca masukan
        String input = masukan.nextLine();

        //menghentikan program jika input adalah "---"
        if (input.equals("---")){
            break;
        }
        //memecahkan masukan menjadi 4 segmen
        String[] inputArray = input.split("#");
        String code = inputArray[0];
        String nim = inputArray[1];
        String tahun = inputArray[2];
        String even = inputArray[3];

        //membuat objek enrollment
        Enrollment student = new Enrollment(code, nim, tahun, even);
        //menambahkan enrollment ke dalam arraylist
        students.add(student);
        //mengeluarkan 12S2203|12S20999|2021/2022|even|None
        System.out.println(student.getCode() + "|" + student.getNim() + "|" + student.getTahun() + "|" + student.getEven() + "|" + student.getEven2());
        }
        masukan.close();
    }
}

