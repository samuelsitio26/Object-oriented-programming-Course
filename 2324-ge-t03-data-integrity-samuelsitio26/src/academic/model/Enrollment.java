package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Enrollment {
    private String code;
    private String nim;
    private String tahun;
    private String even;
    private String even2;

    public Enrollment(String _code, String _nim, String _tahun, String _even) {
        this.code = _code;
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
        this.even2 += _even2;
    }

    // cek duplikasi seperti enrollment-add#12S2203#12S20999#2021/2022#even dan keluaran invalid student|12S20000  dan invalid course|12S2000
    public boolean equals(Object _obj) {
        if (_obj instanceof Enrollment) {
            Enrollment enrollment = (Enrollment) _obj;
            return this.code.equals(enrollment.getCode()) && this.nim.equals(enrollment.getNim());
        }
        return false;
    }

    public String toString() {
        return this.code + "|" + this.nim + "|" + this.tahun + "|" + this.even + "|" + this.even2;
    }
}