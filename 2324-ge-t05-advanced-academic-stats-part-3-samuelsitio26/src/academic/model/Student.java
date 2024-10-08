package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */

 

public class Student extends orang {
    private String departement; //departement of the student
    private String tahun;
    private String progamstudy;
    private double gpa;
    private int totalCredits;

    public Student(String _id, String _name, String _tahun, String _progamstudy, String department) {
        super(_name, _id);
        this.departement = department;
        this.tahun = _tahun;
        this.progamstudy = _progamstudy;
        this.gpa = 0.0;
        this.totalCredits = 0;
    }

    public String getCode() {
        return super.getId();
    }

    public String getName() {
        return super.getName();
    }

    public String getDepartement() {
        return this.departement;
    }

    public String getTahun() {
        return this.tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getProgamstudy() {
        return this.progamstudy;
    }

    public void setProgamstudy(String _progamstudy) {
        this.progamstudy = _progamstudy;
    }

    public String getYear() {
        return null;
    }

    public double getGpa() {
        return this.gpa;
    }

    public void setGpa(double _gpa) {
        this.gpa = _gpa;
    }

    public int getTotalCredits(){
        if (this.gpa == 0.0) {
            return 0;
        } else{
        return totalCredits;
        }
    }

    public void setTotalCredits(int _totalCredits){
        this.totalCredits = _totalCredits; 
    }

  
}