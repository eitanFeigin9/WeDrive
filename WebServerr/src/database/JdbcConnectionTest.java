package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcConnectionTest {

    private static final String JDBC_URL = "jdbc:mysql://aws-db-wedrive.c5seuugwg6qy.us-east-1.rds.amazonaws.com:3306/wedrive-aws-db";
    private static final String JDBC_USER = "sharon";
    private static final String JDBC_PASSWORD = "sharonaws9";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load MySQL JDBC Driver (optional for newer versions of JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Connection to the database established successfully.");

            // Create a Statement object
            statement = connection.createStatement();

            // Execute a query
            String query = "SELECT * FROM Users"; // Replace with a valid table name
            resultSet = statement.executeQuery(query);

            // Process the results
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("fullName")); // Replace 'column1' with a valid column name
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources in the reverse order of opening
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}