package academic.driver;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Driver3 {

    public static void main(String[] _args) {
        //Membuat objek Scanner untuk membaca input dari pengguna
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        String code = scanner.nextLine();
        String id = scanner.nextLine();
        String academicYear = scanner.nextLine();
        String semester = scanner.nextLine();
        String grade = "None";

        

    // menampilkan hasil input
    System.out.println(code + "|" + id + "|" + academicYear + "|" + semester + "|" + grade);    
    //Menutup objek Scanner
    scanner.close();
    
    }

}