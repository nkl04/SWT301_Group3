package model;

public class Customer {
    private int id;
    private String name;
    private CustomerType customerType;




    public Customer(int id, String name, CustomerType customerType) {
        this.id = id;
        this.name = name;
        this.customerType = customerType;
    }

    public int getId() {
        return id;
    }


    public CustomerType getCustomerType() {
        return customerType;
    }
}
