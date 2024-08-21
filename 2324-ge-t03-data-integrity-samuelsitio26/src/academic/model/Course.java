package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Course {
    private String code;
    private String studyProgram;
    private int credits;
    private String grade;

    public Course(String _code, String _studyProgram, int _credits) {
        this.code = _code;
        this.studyProgram = _studyProgram;
        this.credits = _credits;
    }
 
    public String getCode() {
        return this.code;
    }

    public String getStudyProgram() {
        return this.studyProgram;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String _grade) {
        this.grade = _grade;
    }

    public String getName() {
        return null;
    }

    // cek duplikasi 
    public boolean equals(Object _obj) {
        if (_obj instanceof Course) {
            Course course = (Course) _obj;
            return this.code.equals(course.getCode());
        }
        return false;
    }

    
}