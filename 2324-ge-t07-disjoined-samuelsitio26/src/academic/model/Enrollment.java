package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Enrollment<T,S> extends academic {
    private final S nim;
    private final String tahun;
    private final String even;
    private String even2;
    private String previousGrade;
    private String remedial;
    private int totalremedi;

    public Enrollment(T _code, S _nim, String _tahun, String _even) {
        super(_code.toString());
        this.nim = _nim;
        this.tahun = _tahun;
        this.even = _even ;
        this.even2 = "None";
        this.previousGrade = "";
        this.remedial = null;
        this.totalremedi = 0;
 
    }
    
    public String getCode() {
        return this.code;
    }

    public S getNim() {
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

    public String getPreviousGrade() {
        return this.previousGrade;
    }

    public void setPreviousGrade(String _previousGrade) {
        this.previousGrade = _previousGrade;
    }

    public String getRemedial() {
        return this.remedial;
    }

    public void setRemedial(String _remedial) {
        this.remedial = _remedial;
    }

    public int getTotalRemedi() {
        return this.totalremedi;
    }

    public void setTotalRemedi() {
        this.totalremedi += 1;
    }

    public void swapGrade() {
        String temp = "";
        temp = this.even2;
        this.even2 = this.previousGrade;
        this.previousGrade = temp;
        
    }
    

    @Override
    public String toString() {
        return this.code + "|" + this.nim + "|" + this.tahun + "|" + this.even + "|" + this.even2;
    }

    public String toStringRemedial() {
            return this.code + "|" + this.nim + "|" + this.tahun + "|" + this.even + "|" + this.even2 + "(" +this.previousGrade+")";
        }
}