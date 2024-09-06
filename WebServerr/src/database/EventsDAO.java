package database;

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

    public boolean addNewEvent(String eventName, String eventDate, String eventKind, String guestList, String location,
                            String fileName, String latitude, String longitude, String eventOwnerUserName) {

       /* String selectSql = "SELECT id FROM Users WHERE fullName = ?";
        String insertSql = "INSERT INTO Events (eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude, OwnerID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        */
        String selectSql = "SELECT userName FROM Clients WHERE userName = ?";
        String insertSql = "INSERT INTO Events (eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude, userName) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            // Retrieve eventOwnerId using eventOwnerUserName
            selectStatement.setString(1, eventOwnerUserName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String eventOwnerUsername = resultSet.getString("userName");

                // Insert the new event into the Events table
                insertStatement.setString(1, eventName);
                insertStatement.setString(2, eventDate);
                insertStatement.setString(3, eventKind);
                insertStatement.setString(4, guestList);
                insertStatement.setString(5, location);
                insertStatement.setString(6, fileName);
                insertStatement.setString(7, latitude);
                insertStatement.setString(8, longitude);
                insertStatement.setString(9, eventOwnerUsername);

                insertStatement.executeUpdate();
            } else {
                // Handle case where eventOwnerUserName is not found
                System.out.println("Event owner not found.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public HashMap<String, EventData> getAllOwnedEventsForUser(String userName) {
        HashMap<String, EventData> ownedEvents = new HashMap<>();

        String getEventsSql = "SELECT * FROM Events WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getEventsStatement = connection.prepareStatement(getEventsSql)) {

            // Set the userName for the query
            getEventsStatement.setString(1, userName);
            ResultSet eventResultSet = getEventsStatement.executeQuery();

            // Process the result set and construct EventData objects
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
                        Double.parseDouble(eventResultSet.getString("latitude")),
                        Double.parseDouble(eventResultSet.getString("longitude"))
                );

                // Add event to the map
                ownedEvents.put(event.getEventName(), event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedEvents;
        /*   HashMap<String, EventData> ownedEvents = new HashMap<>();

        String getUserIDSql = "SELECT userName FROM Clients WHERE userName = ?";
        String getEventsSql = "SELECT * FROM Events WHERE userName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getUserIDStatement = connection.prepareStatement(getUserIDSql);
             PreparedStatement getEventsStatement = connection.prepareStatement(getEventsSql)) {

            // Get user ID based on email
            getUserIDStatement.setString(1, fullName);
            ResultSet userResultSet = getUserIDStatement.executeQuery();

            if (userResultSet.next()) {
                int userName = userResultSet.getInt("userName");

                // Get all events for the user
                getEventsStatement.setInt(1, userName);
                ResultSet eventResultSet = getEventsStatement.executeQuery();

                while (eventResultSet.next()) {
                    String guestListString = eventResultSet.getString("guestList");
                    HashSet<String> guestList = new HashSet<>(Arrays.asList(guestListString.split(",")));

                    EventData event = new EventData( eventResultSet.getString("eventName"), fullName,eventResultSet.getString("eventDate"), eventResultSet.getString("eventKind"), guestList, eventResultSet.getString("location"), eventResultSet.getString("fileName"), Double.parseDouble(eventResultSet.getString("latitude")), Double.parseDouble(eventResultSet.getString("longitude")));
                    ownedEvents.put(event.getEventName(), event);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedEvents;

      */
    }

    public EventData getEventByOwnerAndName(String eventOwnerUserName, String eventName) {
        EventData event = null;

        String getUserIDSql = "SELECT userName FROM Clients WHERE userName = ?";
        String getEventSql = "SELECT * FROM Events WHERE userName = ? AND eventName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getUserIDStatement = connection.prepareStatement(getUserIDSql);
             PreparedStatement getEventStatement = connection.prepareStatement(getEventSql)) {

            // Get user ID based on the event owner's full name
            getUserIDStatement.setString(1, eventOwnerUserName);
            ResultSet userResultSet = getUserIDStatement.executeQuery();

            if (userResultSet.next()) {
                int userName = userResultSet.getInt("userName");

                // Get the specific event for the user
                getEventStatement.setInt(1, userName);
                getEventStatement.setString(2, eventName);
                ResultSet eventResultSet = getEventStatement.executeQuery();

                if (eventResultSet.next()) {
                    String guestListString = eventResultSet.getString("guestList");
                    HashSet<String> guestList = new HashSet<>(Arrays.asList(guestListString.split(",")));

                    event = new EventData(
                            eventResultSet.getString("eventName"),
                            eventOwnerUserName,
                            eventResultSet.getString("eventDate"),
                            eventResultSet.getString("eventKind"),
                            guestList,
                            eventResultSet.getString("location"),
                            eventResultSet.getString("fileName"),
                            Double.parseDouble(eventResultSet.getString("latitude")),
                            Double.parseDouble(eventResultSet.getString("longitude"))
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

}

