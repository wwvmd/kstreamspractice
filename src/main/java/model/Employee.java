package model;

public class Employee {

    private String gpn;
    private String name;
    private String rank;
    private String function;


    public Employee(String gpn, String name, String rank, String function) {
        this.gpn = gpn;
        this.name = name;
        this.rank = rank;
        this.function = function;
    }
//{"name":"Joe Bloggs","rank":"Director","gpn":"456","function":"Sales Analyst"}
    @Override
    public String toString() {
        return "Employee{" +
                "gpn='" + gpn + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", function='" + function + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
