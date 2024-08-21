package academic.model;

public class Record {

    public record Student(String id, String name, String tahun, String programStudy) {
        @Override 
        public String toString() {
            return this.id + "|" + this.name + "|" + this.tahun + "|" + this.programStudy;
        }
    }

    public record Lecturer(String id, String name, String initial, String email, String studyProgram) {
        @Override
        public String toString() {
            return this.id + "|" + this.name + "|" + this.initial + "|" + this.email + "|" + this.studyProgram;
        }
    }

    public record Course(String code, String studyProgram, int credits, Grade grade) {
        @Override public String toString() {
            return this.code + "|" + this.studyProgram + "|" + this.credits + "|" + this.grade;
        }
    }

    public record Enrollment<T, S>(T code, S nim, String tahun, String even) { 
        @Override public String toString() {
            return this.code + "|" + this.nim + "|" + this.tahun + "|" + this.even;
        }
    }

    public record CourseOpening<T>(T coursecode, String academicyear, String semester, String lecturelist) {
        @Override public String toString() {
            return this.coursecode + "|" + this.academicyear + "|" + this.semester + "|" + this.lecturelist;
        }
    }
}
