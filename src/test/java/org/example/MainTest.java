package org.example;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class MainTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private Main main; // You might not need this, as Main contains only static methods

    private Item otherItem;
    private Item luxuryOtherItem;

    private Item bonusItem;
    private Item luxuryBonusItem;

    private Item standardItem;

    private Employee nonSalariedEmployee;
    private Employee salariedEmployee;
    private Customer regularCustomer;
    private Customer nonRegularCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize employees
        nonSalariedEmployee = new Employee(1, "Van", EmployeeType.NON_SALARIED);
        salariedEmployee = new Employee(2, "Phu", EmployeeType.SALARIED);

        // Initialize customers
        regularCustomer = new Customer(1, "Trang", CustomerType.REGULAR);
        nonRegularCustomer = new Customer(2, "Dung", CustomerType.NON_REGULAR);

        // Initialize items
        otherItem = new Item(1, "Other Item", ItemType.OTHER, 10000.0);
        luxuryOtherItem = new Item(2, "Luxury Other Item", ItemType.OTHER, 10001.0);

        bonusItem = new Item(5, "Bonus Item", ItemType.BONUS, 1000.0);
        luxuryBonusItem = new Item(3, " Luxury Bonus Item", ItemType.BONUS, 1001.0);

        standardItem = new Item(4, "Standard Item", ItemType.STANDARD, 1850.0);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testGetEmployeeById(int input) throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(input);
        when(mockResultSet.getString("name")).thenReturn("Van");
        when(mockResultSet.getString("employee_type")).thenReturn("NON_SALARIED");

        Employee actualEmployee = Main.getEmployeeById(mockConnection, input);

        assertEquals(input, actualEmployee.getId());
    }

    @Test
    void testGetEmployeeByIdReturnsNull() throws SQLException {
        int employeeId = 3;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Employee result = Main.getEmployeeById(mockConnection, employeeId);
        assertNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testGetItemById(int input) throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(input);
        when(mockResultSet.getString("name")).thenReturn("High-end computer");
        when(mockResultSet.getString("item_type")).thenReturn("OTHER");
        when(mockResultSet.getDouble("price")).thenReturn(10000.0);

        Item actualItem = Main.getItemById(mockConnection, input);

        assertEquals(input, actualItem.getId());
    }

    @Test
    void testGetItemByIdReturnsNull() throws SQLException {
        int itemId = 3;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Item result = Main.getItemById(mockConnection, itemId);
        assertNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testGetCustomerById(int input) throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(input);
        when(mockResultSet.getString("name")).thenReturn("Trang");
        when(mockResultSet.getString("customer_type")).thenReturn("REGULAR");

        Customer actualCustomer = Main.getCustomerById(mockConnection, input);

        assertEquals(input, actualCustomer.getId());
    }

    @Test
    public void testGetCustomerByIdReturnsNull() throws SQLException {
        int customerId = 3;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Customer result = Main.getCustomerById(mockConnection, customerId);
        assertNull(result);
    }

    // Test cases for calculateCommission
    @Test
    void testCalculateCommission1() {
        Transaction transaction = new Transaction(nonSalariedEmployee, standardItem, regularCustomer);

        double expectedCommission = 0.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission2() {
        Transaction transaction = new Transaction(nonSalariedEmployee, standardItem, nonRegularCustomer);

        double expectedCommission = 0.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission3() {
        Transaction transaction = new Transaction(nonSalariedEmployee, otherItem, nonRegularCustomer);

        double expectedCommission = 1000.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission4() {
        Transaction transaction = new Transaction(nonSalariedEmployee, luxuryOtherItem, nonRegularCustomer);

        double expectedCommission = 500.05;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission5() {
        Transaction transaction = new Transaction(nonSalariedEmployee, bonusItem, nonRegularCustomer);

        double expectedCommission = 100;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission6() {
        Transaction transaction = new Transaction(nonSalariedEmployee, luxuryBonusItem, nonRegularCustomer);

        double expectedCommission = 75;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission7() {
        Transaction transaction = new Transaction(salariedEmployee, bonusItem, nonRegularCustomer);

        double expectedCommission = 50.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission8() {
        Transaction transaction = new Transaction(salariedEmployee, luxuryBonusItem, nonRegularCustomer);

        double expectedCommission = 25.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission9() {
        Transaction transaction = new Transaction(nonSalariedEmployee,luxuryOtherItem, regularCustomer);

        double expectedCommission = 0.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission10() {
        Transaction transaction = new Transaction(salariedEmployee, otherItem, nonRegularCustomer);

        double expectedCommission = 1000.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission11() {
        Transaction transaction = new Transaction(salariedEmployee, luxuryOtherItem, nonRegularCustomer);

        double expectedCommission = 500.05;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testMain() throws SQLException {
        String input = "1\n1\n2\n"; // Inputs for employeeId, itemId, customerId
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Employee mock setup
            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(1).thenReturn(2);
            when(mockResultSet.getString("name")).thenReturn("Van").thenReturn("High-end computer").thenReturn("Dung");
            when(mockResultSet.getString("employee_type")).thenReturn("NON_SALARIED");
            when(mockResultSet.getString("item_type")).thenReturn("OTHER");
            when(mockResultSet.getDouble("price")).thenReturn(10000.0);
            when(mockResultSet.getString("customer_type")).thenReturn("NON_REGULAR");

            Main.main(null);

            String output = out.toString();
            String lastLine = output.substring(output.lastIndexOf("The calculated commission is: $")).trim();
            assertEquals("The calculated commission is: $1000.0", lastLine);
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }
}
