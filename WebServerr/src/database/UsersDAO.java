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

    public boolean addNewUser(String fullName, String userName, String email, String phoneNumber, String password, String securityAnswer) {
        String checkNameSql = "SELECT COUNT(*) FROM Clients WHERE userName = ?";
        String insertUserSql = "INSERT INTO Clients (fullName, userName,email, phoneNumber, password, securityAnswer) VALUES (?, ?, ?,?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement checkNameStatement = connection.prepareStatement(checkNameSql);
             PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSql)) {

            checkNameStatement.setString(1, userName);
            ResultSet resultSet = checkNameStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("A user with this Username already exists.");
                    return false;
                }
            }

            insertUserStatement.setString(1, fullName);
            insertUserStatement.setString(2, userName);
            insertUserStatement.setString(3, email);
            insertUserStatement.setString(4, phoneNumber);
            insertUserStatement.setString(5, password);
            insertUserStatement.setString(6, securityAnswer);
            insertUserStatement.executeUpdate();
            Users.addNewUser(fullName,userName,email,phoneNumber,password,securityAnswer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public ServerClient getUserByFullName(String userName) {

        // Implement method to retrieve user from the database
        String sql = "SELECT * FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ServerClient(resultSet.getString("fullName"),resultSet.getString("userName"),resultSet.getString("email"),resultSet.getString("phoneNumber"), resultSet.getString("password"),resultSet.getString("securityAnswer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found


    }

    public static ServerClient getUserByUserName(String userName) {
        // Implement method to retrieve user from the database
        String sql = "SELECT * FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ServerClient(resultSet.getString("fullName"), resultSet.getString("userName"), resultSet.getString("email"), resultSet.getString("phoneNumber"), resultSet.getString("password"), resultSet.getString("securityAnswer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    public HashSet<String> getAllUsernames() {
        String sql = "SELECT userName FROM Clients";
        HashSet<String> usernames = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                usernames.add(resultSet.getString("userName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }
    public String getFullName(String userName) {
        String sql = "SELECT fullName FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("fullName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEmail(String userName) {
        String sql = "SELECT email FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneNumber(String userName) {
        String sql = "SELECT phoneNumber FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("phoneNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPassword(String userName) {
        String sql = "SELECT password FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSecurityAnswer(String userName) {
        String sql = "SELECT securityAnswer FROM Clients WHERE userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("securityAnswer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public HashMap<String, EventData> getOwnedEvents(String userName) {
        HashMap<String, EventData> events = new HashMap<>();
        String sql = "SELECT * FROM Events WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String eventName = resultSet.getString("eventName");
                String eventDate = resultSet.getString("eventDate");
                String eventKind = resultSet.getString("eventKind");
                HashSet<String> guestList = new HashSet<>(Arrays.asList(resultSet.getString("guestList").split(",")));
                String location = resultSet.getString("location");
                String fileName = resultSet.getString("fileName");
                double latitude = resultSet.getDouble("latitude");
                double longitude = resultSet.getDouble("longitude");
                String qrCodeFilePath = resultSet.getString("qrCodeFilePath");
                String invitationLink = resultSet.getString("invitationLink");

                EventData event = new EventData(eventName, userName, eventDate, eventKind, guestList, location, fileName, latitude, longitude,qrCodeFilePath,invitationLink);
                events.put(eventName, event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

}