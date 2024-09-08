package database;

import entity.ServerClient;
import event.EventData;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class EventsDAO {

    private static final String JDBC_URL = "jdbc:mysql://aws-db-wedrive.c5seuugwg6qy.us-east-1.rds.amazonaws.com:3306/wedrive-aws-db";
    private static final String JDBC_USER = "sharon";
    private static final String JDBC_PASSWORD = "sharonaws9";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new event to the database
    public boolean addNewEvent(String eventName, String eventDate, String eventKind, String guestList, String location,
                               String fileName, double latitude, double longitude, String eventOwnerUserName,
                               String qrCodeFilePath, String invitationLink) {
        String selectSql = "SELECT userName FROM Clients WHERE userName = ?";
        String insertSql = "INSERT INTO Events (eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude, userName, qrCodeFilePath, invitationLink) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            selectStatement.setString(1, eventOwnerUserName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String eventOwnerUsername = resultSet.getString("userName");
                setEventParameters(insertStatement, eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude, eventOwnerUsername, qrCodeFilePath, invitationLink);
                insertStatement.executeUpdate();

            } else {
                System.out.println("Event owner not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Utility method to set values in PreparedStatement
    private void setEventParameters(PreparedStatement statement, String eventName, String eventDate, String eventKind,
                                    String guestList, String location, String fileName, double latitude, double longitude,
                                    String eventOwnerUserName, String qrCodeFilePath, String invitationLink) throws SQLException {
        statement.setString(1, eventName);
        statement.setString(2, eventDate);
        statement.setString(3, eventKind);
        statement.setString(4, guestList);
        statement.setString(5, location);
        statement.setString(6, fileName);
        statement.setDouble(7, latitude);
        statement.setDouble(8, longitude);
        statement.setString(9, eventOwnerUserName);
        statement.setString(10, qrCodeFilePath);
        statement.setString(11, invitationLink);
    }

    // Method to retrieve all events owned by a specific user
    public HashMap<String, EventData> getAllOwnedEventsForUser(String userName) {
        HashMap<String, EventData> ownedEvents = new HashMap<>();
        String getEventsSql = "SELECT * FROM Events WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getEventsStatement = connection.prepareStatement(getEventsSql)) {

            getEventsStatement.setString(1, userName);
            ResultSet eventResultSet = getEventsStatement.executeQuery();

            while (eventResultSet.next()) {
                String guestListString = eventResultSet.getString("guestList");
                HashSet<String> guestList = new HashSet<>(Arrays.asList(guestListString.split(",")));

                EventData event = new EventData(
                        eventResultSet.getString("eventName"),
                        userName,
                        eventResultSet.getString("eventDate"),
                        eventResultSet.getString("eventKind"),
                        guestList,
                        eventResultSet.getString("location"),
                        eventResultSet.getString("fileName"),
                        eventResultSet.getDouble("latitude"),
                        eventResultSet.getDouble("longitude"),
                        eventResultSet.getString("qrCodeFilePath"),
                        eventResultSet.getString("invitationLink")
                );
                Users.addEventToMap(event);
                ownedEvents.put(event.getEventName(), event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ownedEvents;
    }

    // Method to update an event in the database
    public boolean updateEvent(String eventOldName, String eventName, String eventDate, String eventKind,
                               String guestList, String location, String fileName, double latitude, double longitude,
                               String eventOwnerUserName, String qrCodeFilePath, String invitationLink) {
        String updateSql = "UPDATE Events SET eventName = ?, eventDate = ?, eventKind = ?, guestList = ?, " +
                "location = ?, fileName = ?, latitude = ?, longitude = ?, qrCodeFilePath = ?, invitationLink = ? " +
                "WHERE eventName = ? AND userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            setEventParameters(updateStatement, eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude, eventOwnerUserName, qrCodeFilePath, invitationLink);
            updateStatement.setString(12, eventOldName);
            updateStatement.setString(13, eventOwnerUserName);

            int rowsUpdated = updateStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete an event from the database
    public boolean deleteEvent(String eventName, String userName) {
        // Remove the event from the client's local map
        ServerClient client = Users.getUserByUserName(userName);
        client.getOwnedEvents().remove(eventName);
        Users.removeEventToMap(eventName);

        String deleteSql = "DELETE FROM Events WHERE eventName = ? AND userName = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {

            deleteStatement.setString(1, eventName);
            deleteStatement.setString(2, userName);

            int rowsAffected = deleteStatement.executeUpdate();
            return rowsAffected > 0; // Return true if an event was successfully deleted

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
