package org.example;

import model.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlserver://localhost\\\\SQLEXPRESS:1433;databaseName=SwellStore_DB; trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "12345678";


    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                System.out.println("Connected to the database!");
            }


        Scanner scanner = new Scanner(System.in);


            System.out.println("Select Employee by ID:");
            int employeeId = scanner.nextInt();
            Employee employee = getEmployeeById(conn, employeeId);

            System.out.println("Select Item by ID:");
            int itemId = scanner.nextInt();
            Item item = getItemById(conn, itemId);

            System.out.println("Select Customer by ID:");
            int customerId = scanner.nextInt();
            Customer customer = getCustomerById(conn, customerId);

            Transaction transaction = new Transaction(employee, item, customer);
            double commission = calculateCommission(transaction);

            System.out.println("The calculated commission is: $" + commission);

    }

    static Employee getEmployeeById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getInt("id"), rs.getString("name"), EmployeeType.valueOf(rs.getString("employee_type")));
            }
        }
        return null;
    }

    static Item getItemById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM item WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Item(rs.getInt("id"), rs.getString("name"), ItemType.valueOf(rs.getString("item_type")), rs.getDouble("price"));
            }
        }
        return null;
    }

    static Customer getCustomerById(Connection conn, int id) throws SQLException {
        String query = "SELECT * FROM customer WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("id"), rs.getString("name"), CustomerType.valueOf(rs.getString("customer_type")));
            }
        }
        return null;
    }

    public static double calculateCommission(Transaction transaction) {
        double price = transaction.getItem().getPrice();
        ItemType itemType = transaction.getItem().getItemType();
        EmployeeType employeeType = transaction.getEmployee().getEmployeeType();
        CustomerType customerType = transaction.getCustomer().getCustomerType();

        if (itemType == ItemType.STANDARD){
            return 0 ;
        }
        if (customerType == CustomerType.REGULAR){
            return 0 ;
        }
        if (employeeType == EmployeeType.NON_SALARIED) {
            if (itemType == ItemType.BONUS) {
                if(price <= 1000){
                    return price * 0.1;
                }
                else
                    return 75;
            }
            else { // (itemType == ItemType.OTHER)
                if(price <= 10000){
                    return price * 0.1;
                }else {
                    return price * 0.05;
                }
            }
        } else {  //(employeeType == EmployeeType.SALARIED)
            if (itemType == ItemType.OTHER) {
                if(price <= 10000){
                    return price * 0.1;
                }else {
                    return price * 0.05;
                }
            } else { //(itemType == ItemType.BONUS)
                if(price <= 1000){
                    return price * 0.05;
                }else {
                    return 25;
                }
            }
        }
    }
}