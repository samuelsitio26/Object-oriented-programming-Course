package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Course extends academic {
    private final String studyProgram;
    private final int credits;
    private Grade grade;
    private String lecturer_initial;

    public Course(String code, String studyProgram, int credits) {
        super(code);
        this.studyProgram = studyProgram;
        this.credits = credits;
        this.grade = Grade.None;
        this.lecturer_initial = "";
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStudyProgram() {
        return this.studyProgram;
    }

    public int getCredits() {
        return this.credits;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getLecturer() {
        return this.lecturer_initial;
    }

    public void setLecturer(String lecturer) {
        this.lecturer_initial = lecturer;
    }

    public String toString() { 
        return this.code + "|" + this.studyProgram + "|" + this.credits + "|" + this.grade + "|" + this.lecturer_initial + "(" + this.getEmail() +");"; 
    }

    //menampilkan email dari lecturer seperti 12S2102|Basisdata|4|C|PAT (mona.togatorop@del.ac.id);IUS (iustisia.simbolon@del.ac.id);RSL (rosni@del.ac.id)
    public String getEmail() {
        return null;
    }  
}
