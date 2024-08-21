package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Enrollment extends academic {
    private String nim;
    private String tahun;
    private String even;
    private String even2;

    public Enrollment(String _code, String _nim, String _tahun, String _even) {
        super(_code);
        this.nim = _nim;
        this.tahun = _tahun;
        this.even = _even ;
        this.even2 = "None";
 
    }
    
    public String getCode() {
        return this.code;
    }

    public String getNim() {
        return this.nim;
    }

    public String getTahun() {
        return this.tahun;
    }

    public String getEven() {
        return this.even;
    }

    public String getEven2() {
        return this.even2;
    }

    public void setEven2(String _even2) {
        this.even2 = _even2;
    }
    
    public String toString() {
        return this.code + "|" + this.nim + "|" + this.tahun + "|" + this.even + "|" + this.even2;
    }
}