package exercise.env;

import java.util.Scanner;
import java.util.Arrays;

/**
 * @author 12S22032 Samuel Janring Sitio
 * 
 */

public class exercise {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] margaAyahLaki = {"manurung", "sirait", "butar-butar", "sitorus"};
        String margaAyah = scanner.nextLine();
        String margaKekasih = scanner.nextLine();
        String result = checkMarga(margaAyah, margaKekasih, margaAyahLaki);
        System.out.println(result);
        scanner.close();
    }
    private static String checkMarga(String margaAyah, String margaKekasih, String[] margaAyahLaki) {
        if (!Arrays.asList(margaAyahLaki).contains(margaAyah)) {
            return "no";
        }

        if (margaAyah.equalsIgnoreCase(margaKekasih)) {
            return "no";
        } else {
            return "yes";
        }
    }
}