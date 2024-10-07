package _02_UsingPreparedStatement;

import java.sql.*;

public class _02_InsertingData {
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

            // Define the SQL query with placeholders (`?`) for the values to be inserted
            String query = "INSERT INTO students(name, age, marks) VALUES(?,?,?)";

            // Create a PreparedStatement object to execute the query with the provided values
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the values for the placeholders in the SQL query
            preparedStatement.setString(1, "Vishal");  // Sets the value for the first placeholder (name)
            preparedStatement.setInt(2, 25);          // Sets the value for the second placeholder (age)
            preparedStatement.setDouble(3, 73.7);     // Sets the value for the third placeholder (marks)

            // Execute the SQL INSERT query using executeUpdate(), which returns the number of rows affected
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the data was inserted successfully by verifying the rows affected
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully"); // Data was inserted
            } else {
                System.out.println("Data insert failed"); // No rows were inserted
            }

            // Close the PreparedStatement and Connection after use to prevent resource leaks
            preparedStatement.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // If there is any SQL-related error during connection, query execution, or closing resources,
            // this block will catch the exception and throw a runtime exception with the error message.
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }
}
