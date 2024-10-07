import java.sql.*;
import java.util.Scanner;

public class _04_TransactionHandlingDemo {
    // Database connection parameters
    private static final String url = "jdbc:mysql://localhost:3306/Business"; // JDBC URL for MySQL database
    private static final String user = "root"; // Database username
    private static final String password = "Rous123@.com"; // Database password

    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensures driver is loaded in memory
        } catch (ClassNotFoundException e) {
            // Print an error message if the driver is not found
            System.out.println(e.getMessage());
        }

        // Establish a connection to the database and perform a transaction
        try {
            // Establish a connection to the database using DriverManager
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false); // Start a transaction by disabling auto-commit

            // SQL queries for debit and credit operations
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE acc_num = ?";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE acc_num = ?";

            // Prepare statements for both operations
            PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
            PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);

            // Scanner to take user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the account number to debit:");
            int account_num = scanner.nextInt(); // Account number to debit
            System.out.println("Enter the credit account number:");
            int credit_account_num = scanner.nextInt(); // Account number to credit
            System.out.println("Enter the amount to transfer:");
            double amount = scanner.nextDouble(); // Amount to transfer

            // Set parameters for the debit and credit queries
            debitPreparedStatement.setDouble(1, amount); // Set the amount to debit
            debitPreparedStatement.setInt(2, account_num); // Set the debit account number
            creditPreparedStatement.setDouble(1, amount); // Set the amount to credit
            creditPreparedStatement.setInt(2, credit_account_num); // Set the credit account number

            // Check if the debit account has sufficient balance
            if (isSufficient(connection, account_num, amount)) {
                // Execute the debit and credit operations
                debitPreparedStatement.executeUpdate(); // Update the debit account
                creditPreparedStatement.executeUpdate(); // Update the credit account

                // Commit the transaction if both operations are successful
                connection.commit();
                System.out.println("Transaction Successful");
            } else {
                // Rollback the transaction if there are insufficient funds
                connection.rollback(); // Revert changes made in this transaction
                System.out.println("Transaction Failed: Insufficient funds");
            }

            // Close PreparedStatement and Connection to free up resources
            debitPreparedStatement.close();
            creditPreparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            // Print SQL error message if an exception occurs during database operations
            System.out.println(e.getMessage());
        }
    }

    // Method to check if the account has sufficient balance for the transaction
    public static boolean isSufficient(Connection connection, int acc_num, double amount) {
        try {
            // SQL query to select the balance of the specified account
            String query = "SELECT balance FROM accounts WHERE acc_num = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, acc_num); // Set the account number parameter
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

            // Check if a record is found
            if (resultSet.next()) {
                double currBal = resultSet.getDouble("balance"); // Get the current balance
                // Return true if the balance is sufficient, otherwise false
                return !(amount > currBal);
            }

        } catch (SQLException e) {
            // Print SQL error message if an exception occurs during balance check
            System.out.println(e.getMessage());
        }
        return false; // Return false if no record is found or an error occurs
    }
}
