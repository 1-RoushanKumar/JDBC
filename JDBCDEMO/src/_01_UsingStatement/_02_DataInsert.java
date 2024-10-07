package _01_UsingStatement;

import java.sql.*;

public class _02_DataInsert {
    // Database connection parameters
    private static final String url = "jdbc:mysql://localhost:3306/myDb"; // JDBC URL to connect to the MySQL database
    private static final String user = "root"; // Database username
    private static final String password = "Rous123@.com"; // Database password

    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            // Register the MySQL JDBC driver with the DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensures the driver is loaded into memory
        } catch (ClassNotFoundException e) {
            // If the driver is not found, throw a runtime exception with the error message
            throw new RuntimeException("Error: MySQL JDBC Driver not found - " + e.getMessage());
        }

        // Establish a connection to the database and insert data
        try {
            // Establish a connection to the database using the DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a Statement object to execute SQL queries
            Statement statement = connection.createStatement();

            // Define the SQL query to insert a new record into the 'students' table.
            // The values are hardcoded here: name='Rohan', age=24, marks=76.5.
            String query = String.format(
                    "INSERT INTO students(name, age, marks) VALUES ('%s', %d, %f)",
                    "Nisha", 24, 86.5
            );

            // Execute the SQL INSERT query using executeUpdate(), which returns the number of rows affected.
            // Since this is an INSERT query, executeUpdate() will return the number of rows inserted.
            int rowAffected = statement.executeUpdate(query);

            // Check if the row was inserted successfully by verifying the rows affected.
            if (rowAffected > 0) {
                System.out.println("Data inserted successfully"); // Data was inserted
            } else {
                System.out.println("Data not inserted successfully"); // No rows were inserted
            }

            // Close the Statement and Connection after use to prevent resource leaks
            statement.close(); // Close the Statement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // If there is any SQL-related error during connection, query execution, or closing resources,
            // this block will catch the exception and throw a runtime exception with the error message.
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }
}
//For checking, data is inserted or not; you can use retrieve method just we did before it.
//Other you can go MySql command line then check it.
