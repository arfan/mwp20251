package id.co.kmbmicro.employeeapp;

public class EmployeeModel {
    Integer id;
    String name, email;

    public EmployeeModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public EmployeeModel(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
}