package model;
public class Employee {
    
   
    private int id;
    private String name;
    private String email;
    private String department;
    
    private double salary;
    private String role;

   
    public Employee(int id, String name, String email, String department, double salary, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.role = role;
    }

    // Getters: These act as handles so our JSP webpage can pull the data out later
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public String getRole() { return role; }
}
