// The Module class represents the marks obtained by a student in three modules
public class Module {
    private int mark1; // Marks for the first module
    private int mark2; // Marks for the second module
    private int mark3; // Marks for the third module

    // Constructor to initialize the module marks
    public Module(int mark1, int mark2, int mark3) {
        this.mark1 = mark1; // Assign marks for the first module
        this.mark2 = mark2; // Assign marks for the second module
        this.mark3 = mark3; // Assign marks for the third module
    }

    // Getter method to retrieve marks for the first module
    public int getMark1() {
        return mark1;
    }

    // Getter method to retrieve marks for the second module
    public int getMark2() {
        return mark2;
    }

    // Getter method to retrieve marks for the third module
    public int getMark3() {
        return mark3;
    }

    // Method to calculate the total marks obtained in all three modules
    public int calculateTotal() {
        return mark1 + mark2 + mark3;
    }

    // Method to calculate the average marks obtained across all three modules
    public double calculateAverage() {
        return calculateTotal() / 3.0;
    }

    // Method to determine the grade based on the average marks
    public String calculateGrade() {
        double average = calculateAverage(); // Calculate the average marks
        if (average >= 80) { // Distinction for average 80 or above
            return "Distinction";
        } else if (average >= 70) { // Merit for average 70 or above but below 80
            return "Merit";
        } else if (average >= 40) { // Pass for average 40 or above but below 70
            return "Pass";
        } else { // Fail for average below 40
            return "Fail";
        }
    }
}
