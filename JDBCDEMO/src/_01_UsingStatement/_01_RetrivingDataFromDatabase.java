package _01_UsingStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class _01_RetrivingDataFromDatabase {
    // Database connection parameters
    private static final String url = "jdbc:mysql://localhost:3306/myDb"; // JDBC URL to connect to the MySQL database
    private static final String username = "root"; // Database username
    private static final String password = "Rous123@.com"; // Database password

    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            // This line registers the MySQL JDBC driver with the DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensures the MySQL JDBC driver is available at runtime
        } catch (ClassNotFoundException e) {
            // If the driver class is not found, it will throw an exception.
            // Typically, this would happen if the MySQL JDBC driver is not added to the classpath.
            System.out.println("Error: MySQL JDBC Driver not found - " + e.getMessage()); // Prints the error message
        }

        // Establish a connection to the database and execute a query
        try {
            // Establish a connection to the database using the JDBC URL, username, and password.
            // This will throw a SQLException if the connection cannot be made (e.g., wrong credentials, URL, etc.)
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a Statement object to execute SQL queries.
            // The Statement is used to send simple SQL statements to the database.
            Statement statement = connection.createStatement();

            // Define the SQL query to retrieve all records from the 'students' table.
            // This SQL statement will select all columns and rows from the 'students' table.
            String query = "SELECT * FROM students";

            // Execute the SQL query using the statement object.
            // The result of the query is stored in a ResultSet object, which holds the data retrieved from the database.
            ResultSet resultSet = statement.executeQuery(query);

            // Loop through the ResultSet to retrieve each row of data.
            // resultSet.next() moves the cursor to the next row and returns false when no more rows exist.
            while (resultSet.next()) {
                // Retrieve data from each column for the current row.
                int id = resultSet.getInt("id"); // Get the value of the 'id' column (assumed to be of type int)
                String name = resultSet.getString("name"); // Get the value of the 'name' column (of type String)
                int age = resultSet.getInt("age"); // Get the value of the 'age' column (assumed to be of type int)
                double marks = resultSet.getDouble("marks"); // Get the value of the 'marks' column (assumed to be of type double)

                // Print the retrieved data to the console in a formatted way.
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Marks: " + marks);
            }

            // Close the resources (Connection, Statement, and ResultSet) after use to prevent resource leaks.
            resultSet.close(); // Closing the ResultSet
            statement.close(); // Closing the Statement
            connection.close(); // Closing the Connection

        } catch (SQLException e) {
            // If there is any SQL-related error during connection, query execution, or closing resources,
            // this block will catch the exception and print the relevant error message.
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
