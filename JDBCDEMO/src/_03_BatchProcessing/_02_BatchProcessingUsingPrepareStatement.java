package _03_BatchProcessing;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class _02_BatchProcessingUsingPrepareStatement {
    // Database connection parameters
    private static final String url = "jdbc:mysql://localhost:3306/myDb"; // JDBC URL for MySQL database
    private static final String user = "root"; // Database username
    private static final String password = "Rous123@.com"; // Database password

    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            // Registers the MySQL JDBC driver with the DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensures driver is loaded in memory
        } catch (ClassNotFoundException e) {
            // Print an error message if the driver is not found
            System.out.println(e.getMessage());
        }

        // Establish a connection to the database and perform batch insertion
        try {
            // Establish a connection to the database using DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Use Scanner to take user input
            Scanner scanner = new Scanner(System.in);

            // SQL query template to insert data into the 'students' table
            String query = "INSERT INTO students(name, age, marks) VALUES (?, ?, ?)";

            // Create a PreparedStatement object to execute parameterized queries
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Continuously accept user input for multiple student records
            System.out.println("Press Enter to start::");
            while (true) {
                // Consume any leftover newline character to avoid input skipping issues
                scanner.nextLine(); // Clears the buffer after integer input

                // Get the student's name from user input
                System.out.println("Enter name:");
                String name = scanner.nextLine(); // Read the student's name

                // Get the student's age from user input
                System.out.println("Enter age:");
                int age = scanner.nextInt(); // Read the student's age

                // Get the student's marks from user input
                System.out.println("Enter marks:");
                double marks = scanner.nextDouble(); // Read the student's marks

                // Set the input parameters in the PreparedStatement
                preparedStatement.setString(1, name); // Set the 1st parameter (name)
                preparedStatement.setInt(2, age); // Set the 2nd parameter (age)
                preparedStatement.setDouble(3, marks); // Set the 3rd parameter (marks)

                // Add the current insertion query to the batch
                preparedStatement.addBatch();

                // Ask the user if they want to continue or stop batch input
                System.out.println("Do you want to insert more data? (Y/N):");
                String action = scanner.next(); // Read the user's response

                // If the user enters "N", break the loop and execute the batch
                if (action.toUpperCase(Locale.ROOT).equals("N")) {
                    break;
                }
            }

            // Execute the batch of SQL queries and get the number of rows affected for each query
            int[] rowsAffected = preparedStatement.executeBatch();

            // Check if each query in the batch was successful
            for (int result : rowsAffected) {
                if (result == 0) {
                    // If result is 0, it indicates that insertion failed for that particular query
                    System.out.println("Data insertion failed for one or more records!");
                }
            }

            // Close the PreparedStatement and Connection objects to free up resources
            preparedStatement.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // Print the SQL error message if an exception occurs during database operations
            System.out.println(e.getMessage());
        }
    }
}
