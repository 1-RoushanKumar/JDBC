package _03_BatchProcessing;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class _01_BatchProcessingUsingStatement {
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
            // If the driver is not found, print the error message
            System.out.println(e.getMessage());
        }

        // Establish a connection to the database and perform batch insertion
        try {
            // Establish a connection to the database using DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);

            // Use Scanner to take user input
            Scanner scanner = new Scanner(System.in);

            // Create a Statement object to execute SQL queries
            Statement statement = connection.createStatement();

            // Continuously accept user input to insert multiple records in batches
            System.out.println("Press Enter to start::");
            while (true) {
                // Clear newline characters after previous inputs to avoid input issues
                scanner.nextLine(); // Consumes the leftover newline after reading previous input

                // Ask the user to enter student details (name, age, marks)
                System.out.println("Enter name:");
                String name = scanner.nextLine(); // Read student's name from user input

                System.out.println("Enter age:");
                int age = scanner.nextInt(); // Read student's age from user input

                System.out.println("Enter marks:");
                double marks = scanner.nextDouble(); // Read student's marks from user input

                // Create an SQL query to insert student data
                String query = String.format("INSERT INTO students(name, age, marks) VALUES ('%s', %d, %f)", name, age, marks);

                // Add the query to the batch to execute later
                statement.addBatch(query);

                // Ask the user whether they want to continue adding data
                System.out.println("Do you want to insert more data? (Y/N):");
                String action = scanner.next();

                // If the user enters "N", exit the loop and process the batch
                if (action.toUpperCase(Locale.ROOT).equals("N")) {
                    break;
                }
            }

            // Execute the batch of SQL queries and store the result (number of affected rows)
            int[] rowsAffected = statement.executeBatch();

            // Check if each query in the batch was executed successfully
            for (int result : rowsAffected) {
                // If any result is 0, it means the insertion failed for that particular query
                if (result == 0) {
                    System.out.println("Data insertion failed for one or more records!");
                }
            }

            // Close the Statement and Connection objects to prevent resource leaks
            statement.close(); // Close the Statement
            connection.close(); // Close the Connection

        } catch (SQLException e) {
            // If there is any SQL-related error during connection, query execution, or closing resources,
            // this block will catch the exception and print the error message.
            System.out.println(e.getMessage());
        }
    }
}
