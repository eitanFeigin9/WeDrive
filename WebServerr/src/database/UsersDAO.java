package database;


import entity.ServerClient;
import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class UsersDAO {

    private static final String JDBC_URL = "jdbc:mysql://aws-db-wedrive.c5seuugwg6qy.us-east-1.rds.amazonaws.com:3306/wedrive-aws-db";
    private static final String JDBC_USER = "sharon";
    private static final String JDBC_PASSWORD = "sharonaws9";

    static {
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Class.forName("com.mysql.cj.xdevapi.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean addNewUser(String fullName, String email, String phoneNumber, String password, String securityAnswer) {
        String checkNameSql = "SELECT COUNT(*) FROM Users WHERE fullName = ?";
        String insertUserSql = "INSERT INTO Users (fullName, email, phoneNumber, password, securityAnswer) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement checkNameStatement = connection.prepareStatement(checkNameSql);
             PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSql)) {

            checkNameStatement.setString(1, fullName);
            ResultSet resultSet = checkNameStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("A user with this full name already exists.");
                    return false;
                }
            }

            insertUserStatement.setString(1, fullName);
            insertUserStatement.setString(2, email);
            insertUserStatement.setString(3, phoneNumber);
            insertUserStatement.setString(4, password);
            insertUserStatement.setString(5, securityAnswer);

            insertUserStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isValidUser(String fullName, String password) {
        String sql = "SELECT * FROM Users WHERE fullName = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullName);
            statement.setString(2, password); // Consider hashing the password for security.

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a user with the provided credentials exists.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception appropriately
        }
    }

    public ServerClient getUserByFullName(String fullName) {
        // Implement method to retrieve user from the database
        String sql = "SELECT * FROM Users WHERE fullName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ServerClient(resultSet.getString("fullName"),resultSet.getString("email"),resultSet.getString("phoneNumber"), resultSet.getString("password"),resultSet.getString("securityAnswer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    public ServerClient getUserByEmail(String email) {
        // Implement method to retrieve user from the database
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ServerClient(resultSet.getString("fullName"),resultSet.getString("email"),resultSet.getString("phoneNumber"), resultSet.getString("password"),resultSet.getString("securityAnswer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }


}