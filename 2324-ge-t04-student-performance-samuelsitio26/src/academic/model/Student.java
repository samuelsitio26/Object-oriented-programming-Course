package academic.model;
/**
 * @author 12S22032 Samuel Sitio
 */
public class Student {
    private String id;
    private String name;
    private double gpa;
    private int credit;
    
    public Student(String _id, String _name, double _gpa, int _credit) {
        id = _id;
        name = _name;
        gpa = _gpa;
        credit = _credit;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredit() {
        return credit;
    }   
    
}
