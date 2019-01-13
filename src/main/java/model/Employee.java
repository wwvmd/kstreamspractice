package model;

public class Employee {

    private String employeeId;
    private String name;
    private String rank;
    private String function;


    public Employee(String employeeId, String name, String rank, String function) {
        this.employeeId = employeeId;
        this.name = name;
        this.rank = rank;
        this.function = function;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", function='" + function + '\'' +
                '}';
    }
}
