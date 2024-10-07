package _02_UsingPreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class _03_UpdatingData {
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

            // Define the SQL query with placeholders (`?`) for the values to be updated
            String query = "UPDATE students SET marks = ? WHERE id = ?";

            // Create a PreparedStatement object to execute the query with the provided values
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the values for the placeholders in the SQL query
            preparedStatement.setDouble(1, 70.5);  // Sets the value for the first placeholder (marks)
            preparedStatement.setInt(2, 5);        // Sets the value for the second placeholder (id)

            // Execute the SQL UPDATE query using executeUpdate(), which returns the number of rows affected
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the data was updated successfully by verifying the rows affected
            if (rowsAffected > 0) {
                System.out.println("Data updated successfully"); // Data was updated
            } else {
                System.out.println("Data update failed"); // No rows were updated
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
