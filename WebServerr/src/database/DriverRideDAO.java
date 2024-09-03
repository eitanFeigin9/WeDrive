package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DriverRideDAO {

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

    public void addNewDriverRide(String eventName, String eventAddress, String eventLatitude, String eventLongitude,
                                 int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker, int currNumOfHitchhikers,
                                 int totalFuelReturns, String latitude, String longitude, double maxPickupDistance) {
        String sql = "INSERT INTO DriverRide (eventName, eventAddress, eventLatitude, eventLongitude, maxCapacity, " +
                "pickupCity, fuelReturnsPerHitchhiker, currNumOfHitchhikers, totalFuelReturns, latitude, longitude, maxPickupDistance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, eventName);
            statement.setString(2, eventAddress);
            statement.setString(3, eventLatitude);
            statement.setString(4, eventLongitude);
            statement.setInt(5, maxCapacity);
            statement.setString(6, pickupCity);
            statement.setInt(7, fuelReturnsPerHitchhiker);
            statement.setInt(8, currNumOfHitchhikers);
            statement.setInt(9, totalFuelReturns);
            statement.setString(10, latitude);
            statement.setString(11, longitude);
            statement.setDouble(12, maxPickupDistance);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

