package _02_UsingPreparedStatement;

import java.sql.*;

public class _01_RetrievingData {
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

        // Establish a connection to the database and retrieve data
        try {
            // Establish a connection to the database using the DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Define the SQL query with a placeholder (`?`) for the `id`
            String query = "SELECT * FROM students WHERE id = ?";

            // Create a PreparedStatement object to execute the query with a parameter
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the value of the first parameter (index 1) to 1, representing the student `id`
            preparedStatement.setInt(1, 1);

            // Execute the query and store the result in a ResultSet object
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the query returned a result
            if (resultSet.next()) {
                // If a record is found, retrieve and print the data
                System.out.println("Name: " + resultSet.getString("name")); // Get the 'name' column value
                System.out.println("Marks: " + resultSet.getString("marks")); // Get the 'marks' column value
            }

            // Close the PreparedStatement and Connection after use to prevent resource leaks
            preparedStatement.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // If there is an SQL error, print the error message
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
