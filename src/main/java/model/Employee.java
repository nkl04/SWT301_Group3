package model;

public class Employee {
    private int id;
    private String name;
    private EmployeeType employeeType;

    public Employee(int id, String name, EmployeeType employeeType) {
            this.id = id;
            this.name = name;
            this.employeeType = employeeType;
        }

        public int getId() {
            return id;
        }


        public EmployeeType getEmployeeType() {
            return employeeType;
        }
    }


