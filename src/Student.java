// The Student class represents a student with an ID, name, and associated module marks
public class Student {
    private String id;       // Unique identifier for the student
    private String name;     // Name of the student
    private Module module;   // Module marks associated with the student

    // Constructor to initialize the student object with ID, name, and module marks
    public Student(String id, String name, Module module) {
        this.id = id;        // Assign the student ID
        this.name = name;    // Assign the student name
        this.module = module; // Assign the module marks
    }

    // Getter method to retrieve the student ID
    public String getId() {
        return id;
    }

    // Getter method to retrieve the student name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the module marks
    public Module getModule() {
        return module;
    }

    // Setter method to assign or update the module marks
    public void setModule(Module module) {
        this.module = module;
    }
}
