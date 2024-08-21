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

        String margaAyahPria = scanner.nextLine();
        String margaAyahKekasih = scanner.nextLine();

        String margaIbuPria = scanner.nextLine();

        String margaIbuKekasih = scanner.nextLine();

        String result = checkMarriageEligibility(margaAyahPria, margaAyahKekasih, margaIbuPria, margaIbuKekasih);

        System.out.println(result);

        scanner.close();
    }

    private static String checkMarriageEligibility(String margaAyahPria, String margaAyahKekasih,
                                                   String margaIbuPria, String margaIbuKekasih) {
        String[] margaAyahPriaValid = {"sitorus", "situmorang", "pardede"};
        String[] margaIbuPriaValid = {"tampubolon", "pardede"};

        boolean isMargaAyahPriaValid = Arrays.asList(margaAyahPriaValid).contains(margaAyahPria);
        boolean isMargaIbuPriaValid = Arrays.asList(margaIbuPriaValid).contains(margaIbuPria);

        if (isMargaAyahPriaValid && isMargaIbuPriaValid) {
            if (!margaAyahPria.equalsIgnoreCase(margaAyahKekasih) && !margaIbuPria.equalsIgnoreCase(margaIbuKekasih)) {
                return "yes";
            } else {
                return "no";
            }
        } else {
            return "no";
        }
    }
}