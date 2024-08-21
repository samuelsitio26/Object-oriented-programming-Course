package academic.model;

public class Human {
    protected final String name;
    protected final String id;
    
    public Human(String _name, String _id) {
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
