package academic.model;

public class CourseOpening<T>{
    private final T coursecode;
    private final String academicyear;
    private final String semester;
    private final String lecturelist;

    public CourseOpening(T coursecode, String academicyear, String semester, String lecturelist) {
        this.coursecode = coursecode;
        this.academicyear = academicyear;
        this.semester = semester;
        this.lecturelist = lecturelist;
    }

    public T getcoursecode() {
        return this.coursecode;
    }

    public String getAcademicyear() {
        return this.academicyear;
    }

    public String getSemester() {
        return this.semester;
    }

    public String getLecturalList() {
        return this.lecturelist;
    }



    public String toString() {
        return this.coursecode + "|" + this.academicyear + "|" + this.semester + "|" + this.lecturelist ;
    }
}
