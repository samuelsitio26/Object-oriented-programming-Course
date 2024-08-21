package academic.model;

/**
 * @author 12S22032 Samuel Sitio
 */
 
public class Lecturer  extends orang { 
    // private final String departement;
    private final String initial;
    private final String email;
    private final String studyProgram;
 
    public Lecturer(String _id, String _name, String _initial, String _email, String _studyProgram/*,  String _departement*/) {
        super(_name, _id);
        // this.departement = _departement;
        this.initial = _initial;
        this.email = _email;
        this.studyProgram = _studyProgram;
    }
 
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    // public String getDepartement() {
    //     return this.departement;
    // }

    public String getInitial() {
        return this.initial;
    }

    public String getEmail() {
        return this.email;
    }

    public String getStudyProgram() {
        return this.studyProgram;
    }


    // for (Lecturer lecturer : lecturers) {
    //     System.out.println(lecturer.getId() + "|" + lecturer.getName() + "|" + lecturer.getInitial() + "|"
    //             + lecturer.getEmail() + "|" + lecturer.getStudyProgram());
    // }
    @Override
    public String toString() {
        return this.id + "|" + this.name + "|" + this.initial + "|" + this.email + "|" + this.studyProgram;
    }    
 }