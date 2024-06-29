package model;

public class Transaction {
    private Employee employee;
    private Item item;
    private Customer customer;

    public Transaction(Employee employee, Item item, Customer customer) {
        this.employee = employee;
        this.item = item;
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }


    public Item getItem() {
        return item;
    }


    public Customer getCustomer() {
        return customer;
    }

}
