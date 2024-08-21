package academic.model;

public class orang {
    protected String name;
    protected String id;
    
    public orang(String _name, String _id) {
        this.name = _name;
        this.id = _id;
    }
    
    public String getName() {
        return this.name;
    }  

    public String getId() {
        return this.id;
    }
}
