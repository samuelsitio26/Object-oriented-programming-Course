package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
public class Course {
    private final String code;
    private final String name;
    private final int credits;
    private final String studyProgram;

    public Course(String code, String name, int credits, String studyProgram) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.studyProgram = studyProgram;
    }
    
    @Override
    public String toString() {
        return "" + code + "|" + name + "|" + credits + "|" + studyProgram;
    }   

}