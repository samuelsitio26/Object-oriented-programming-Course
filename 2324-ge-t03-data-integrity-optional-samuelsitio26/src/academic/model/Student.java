package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Student {
    private String code;
    private String name;
    private String tahun;
    private String progamstudy;

    public Student(String code2, String _name, String _tahun, String _progamstudy) {
        this.code = code2;
        this.name = _name;
        this.tahun = _tahun;
        this.progamstudy = _progamstudy;
    }
 
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getTahun() {
        return this.tahun;
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
    
}