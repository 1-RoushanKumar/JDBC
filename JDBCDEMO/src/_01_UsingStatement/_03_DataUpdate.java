package _01_UsingStatement;

import java.sql.*;

public class _03_DataUpdate {
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

        // Establish a connection to the database and update data
        try {
            // Establish a connection to the database using the DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a Statement object to execute SQL queries
            Statement statement = connection.createStatement();

            // Define the SQL query to update the 'marks' column for a specific student (identified by 'id').
            // The 'id' of the student to update is hardcoded as 2, and their 'marks' are updated to 69.5.
            String query = String.format(
                    "UPDATE students SET marks = %f WHERE id = %d",
                    89.5, 4
            );

            // Execute the SQL UPDATE query using executeUpdate(), which returns the number of rows affected.
            // Since this is an UPDATE query, executeUpdate() will return the number of rows updated.
            int rowAffected = statement.executeUpdate(query);

            // Check if any rows were updated successfully by verifying the rows affected.
            if (rowAffected > 0) {
                System.out.println("Data updated successfully"); // Data was updated
            } else {
                System.out.println("Data not updated successfully"); // No rows were updated
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
