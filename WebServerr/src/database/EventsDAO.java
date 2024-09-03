package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void addNewEvent(String eventName, String eventDate, String eventKind, String guestList, String location, String fileName, String latitude, String longitude) {
        String sql = "INSERT INTO Events (eventName, eventDate, eventKind, guestList, location, fileName, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, eventName);
            statement.setString(2, eventDate);
            statement.setString(3, eventKind);
            statement.setString(4, guestList);
            statement.setString(5, location);
            statement.setString(6, fileName);
            statement.setString(7, latitude);
            statement.setString(8, longitude);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

