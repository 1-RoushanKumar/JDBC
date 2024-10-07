package _02_UsingPreparedStatement;

import java.sql.*;

public class _04_DeletingData {
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

            // Define the SQL query with a placeholder (`?`) for the value to be used in the deletion
            String query = "DELETE FROM students WHERE id = ?";

            // Create a PreparedStatement object to execute the query with the provided value
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the value for the placeholder in the SQL query
            preparedStatement.setInt(1, 5);  // Sets the value for the first placeholder (id)

            // Execute the SQL DELETE query using executeUpdate(), which returns the number of rows affected
            int result = preparedStatement.executeUpdate();

            // Check if the data was deleted successfully by verifying the rows affected
            if (result > 0) {
                System.out.println("Data deleted successfully"); // Data was deleted
            } else {
                System.out.println("Data not deleted"); // No rows were deleted
            }

            // Close the PreparedStatement and Connection after use to prevent resource leaks
            preparedStatement.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // If there is any SQL-related error during connection, query execution, or closing resources,
            // this block will catch the exception and print the error message.
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
