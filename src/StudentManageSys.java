import java.io.*;
import java.util.*;

public class StudentManageSys {
    private static final int TOT_STUDENTS = 100;
    private static Student[] students = new Student[TOT_STUDENTS];
    private static int studentCount = 0;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getValidChoice();
            scan.nextLine();

            switch (choice) {
                case 1:
                    availableSeats();
                    break;
                case 2:
                    studentRegistration();
                    break;
                case 3:
                    studentDeletion();
                    break;
                case 4:
                    findStudent();
                    break;
                case 5:
                    storeStuDetails();
                    break;
                case 6:
                    loadStuDetails();
                    break;
                case 7:
                    studentsView();
                    break;
                case 8:
                    extraOptions();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!! Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nWelcome to the Student Management System!!!");
        System.out.println("1. Check the number of available Seats");
        System.out.println("2. Register a new Student");
        System.out.println("3. Delete a Student");
        System.out.println("4. Find a Student");
        System.out.println("5. Store Student Details");
        System.out.println("6. Load Student Details");
        System.out.println("7. View the list of Students");
        System.out.println("8. Additional Options");
        System.out.println("0. Exit");
    }

    private static int getValidChoice() {
        int choice = -1;
        while (choice < 0 || choice > 8) {
            try {
                System.out.print("Please enter your choice: ");
                choice = scan.nextInt();
                if (choice < 0 || choice > 8) {
                    System.out.println("Invalid choice! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scan.nextLine();
            }
        }
        return choice;
    }

    private static void availableSeats() {
        System.out.println("Number of Available Seats: " + (TOT_STUDENTS - studentCount));
    }

    private static void studentRegistration() {
        if (studentCount >= TOT_STUDENTS) {
            System.out.println("No Seats Available to Register New Students.");
            return;
        }

        while (true) {
            System.out.print("Enter ID for Registration (or '0' to exit) - ex: w1234567: ");
            String id = scan.nextLine();
            if (id.equals("0")) {
                System.out.println("Exiting registration process...");
                return;
            }

            if (id.length() != 8 || !id.startsWith("w")) {
                System.out.println("Error: Student ID must be eight characters long.");
                continue;
            }

            if (findStudentById(id) != null) {
                System.out.println("Error: Student ID already exists.");
                continue;
            }

            System.out.print("Enter Student Name for Registration (or '0' to stop): ");
            String name = scan.nextLine();
            if (name.equals("0")) {
                System.out.println("Exiting registration process...");
                return;
            }

            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                System.out.println("Error: Name cannot be empty and must contain only alphabetic characters.");
                continue;
            }

            students[studentCount] = new Student(id, name, null);
            studentCount++;
            System.out.println("New Student with ID: " + id + " and Name: " + name + " has been registered successfully.");
            return;
        }
    }

    private static void studentDeletion() {
        if (studentCount == 0) {
            System.out.println("No Students Available to Delete.");
            return;
        }

        System.out.println("List of Students:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println((i + 1) + ". ID: " + students[i].getId() + ", Name: " + students[i].getName());
        }

        System.out.print("Enter the number of the student to delete (or type 'exit' to stop): ");
        String input = scan.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting deletion process...");
            return;
        }

        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= studentCount) {
                System.out.println("Invalid number. Exiting deletion process...");
                return;
            }

            String id = students[index].getId();
            students[index] = students[studentCount - 1];
            students[studentCount - 1] = null;
            studentCount--;

            System.out.println("Student with ID: " + id + " has been deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Exiting deletion process...");
        }
    }

    private static void findStudent() {
        System.out.print("Enter the student ID of the student to find: ");
        String id = scan.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            System.out.println("Student with ID: " + id + " and Name: " + student.getName() + " has been found successfully.");
        } else {
            System.out.println("Student with ID: " + id + " not found.");
        }
    }

    private static Student findStudentById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    private static void storeStuDetails() {
        try (PrintWriter writer = new PrintWriter("students.txt")) {
            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];
                Module module = student.getModule();
                writer.print(student.getId() + "," + student.getName());
                if (module != null) {
                    writer.print("," + module.getMark1() + "," + module.getMark2() + "," + module.getMark3());
                }
                writer.println();
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("Student details could not be stored due to an error." + e.getMessage());
        }
    }

    private static void loadStuDetails() {
        try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
            studentCount = 0;
            while (fileScanner.hasNextLine() && studentCount <TOT_STUDENTS) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2 || parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    Module module = null;
                    if (parts.length == 5) {
                        int mark1 = Integer.parseInt(parts[2]);
                        int mark2 = Integer.parseInt(parts[3]);
                        int mark3 = Integer.parseInt(parts[4]);
                        module = new Module(mark1, mark2, mark3);
                    }
                    students[studentCount] = new Student(id, name, module);
                    studentCount++;
                }
            }
            System.out.println("Student details loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading the file." + e.getMessage());
        }
    }

    private static void studentsView() {
        if (studentCount > 0) { // Check if there are students available
            Student[] sortedStudents = Arrays.copyOf(students, studentCount);
            Arrays.sort(sortedStudents, Comparator.comparing(Student::getName));
            for (Student student : sortedStudents) {
                System.out.println("ID: " + student.getId() + ", Name: " + student.getName());
            }
        } else {
            System.out.println("No students available to view.");
        }
    }


    private static void extraOptions() {
        System.out.println("a. Enter module marks");
        System.out.println("b. Generate system summary");
        System.out.println("c. Generate complete report");
        System.out.println("d. Exit");
        System.out.print("Enter your choice: ");
        String choice = scan.nextLine();
        switch (choice) {
            case "a":
                studentResults();
                break;
            case "b":
                generateSystemSummary();
                break;
            case "c":
                generateCompleteReport();
                break;
            case "d":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!! Please try again.");
        }
    }

    private static void studentResults() {
        boolean continueEnter = true;
        while (continueEnter) {
            System.out.print("Enter Student ID: ");
            String id = scan.nextLine();
            Student student = findStudentById(id);

            if (student == null) {
                System.out.println("Student with ID: " + id + " not found.");
                return;
            } else {

                int mark1 = getValidMark("Module 1");
                int mark2 = getValidMark("Module 2");
                int mark3 = getValidMark("Module 3");

                student.setModule(new Module(mark1, mark2, mark3));
                System.out.println("Marks for Student ID: " + id + " have been updated.");

                storeStuDetails();
            }

            System.out.print("Do you want to enter marks for another Student -(yes/no) : ");
            String choice = scan.nextLine();
            if (choice.equalsIgnoreCase("no")) {
                continueEnter = false;
            }
        }
    }


    private static int getValidMark(String moduleName) {
        int mark;
        while (true) {
            System.out.print("Enter marks for " + moduleName + " : ");
            mark = scan.nextInt();
            if (mark >= 1 && mark <= 100) {
                break;
            }
            System.out.println("Invalid mark! Please enter a mark between 1 and 100.");
        }
        scan.nextLine();
        return mark;
    }

    private static void generateSystemSummary() {
        int totalRegistrations = studentCount;
        int passedModule1 = 0;
        int passedModule2 = 0;
        int passedModule3 = 0;

        for (int i = 0; i < studentCount; i++) {
            Module module = students[i].getModule();
            if (module != null) {
                if (module.getMark1() > 40) passedModule1++;
                if (module.getMark2() > 40) passedModule2++;
                if (module.getMark3() > 40) passedModule3++;
            }
        }

        System.out.println("Total student registrations: " + totalRegistrations);
        System.out.println("Total students scored more than 40 marks in Module 1: " + passedModule1);
        System.out.println("Total students scored more than 40 marks in Module 2: " + passedModule2);
        System.out.println("Total students scored more than 40 marks in Module 3: " + passedModule3);
    }

    private static void generateCompleteReport() {
        bubbleSortByAverage(students, studentCount);

        System.out.println("Complete report of students:");
        System.out.printf("%-10s %-20s %-10s %-10s %-10s %-10s %-10s %-10s%n", "Student ID", "Student Name", "Module 1", "Module 2", "Module 3", "Total", "Average", "Grade");

        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            Module module = student.getModule();
            int mark1, mark2, mark3;
            double average;
            String grade;

            if (module != null) {
                mark1 = module.getMark1();
                mark2 = module.getMark2();
                mark3 = module.getMark3();
                average = module.calculateAverage();
                grade = module.calculateGrade();
            } else {
                mark1 = 0;
                mark2 = 0;
                mark3 = 0;
                average = 0;
                grade = "N/A";
            }

            int total = mark1 + mark2 + mark3;

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s", student.getId()));
            sb.append(String.format("%-20s", student.getName()));
            sb.append(String.format("%-10d", mark1));
            sb.append(String.format("%-10d", mark2));
            sb.append(String.format("%-10d", mark3));
            sb.append(String.format("%-12d", total));
            sb.append(String.format("%-12.2f", average));
            sb.append(String.format("%-10s", grade));

            System.out.println(sb.toString());
        }
    }

    private static void bubbleSortByAverage(Student[] students, int count) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < count - 1; i++) {
                if (students[i].getModule() != null && students[i + 1].getModule() != null &&
                        students[i].getModule().calculateAverage() < students[i + 1].getModule().calculateAverage()) {
                    Student temp = students[i];
                    students[i] = students[i + 1];
                    students[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }
}
