package _01_UsingStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class _04_DeleteData {
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

        // Establish a connection to the database and delete data
        try {
            // Establish a connection to the database using the DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a Statement object to execute SQL queries
            Statement statement = connection.createStatement();

            // Define the SQL query to delete a student record with a specific 'id'.
            // The 'id' of the student to delete is hardcoded as 2 in this case.
            String query = "DELETE FROM students WHERE id = 4";

            // Execute the SQL DELETE query using executeUpdate(), which returns the number of rows affected.
            // Since this is a DELETE query, executeUpdate() will return the number of rows deleted.
            int rowAffected = statement.executeUpdate(query);

            // Check if the row was deleted successfully by verifying the rows affected.
            if (rowAffected > 0) {
                System.out.println("Data deleted successfully"); // Data was deleted
            } else {
                System.out.println("Data not deleted successfully"); // No rows were deleted
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
