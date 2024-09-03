package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HitchhikerRideDAO {

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

    public void addNewHitchhikerRide(String eventName, String pickupCity, int fuelMoney, Boolean isFreeForPickup,
                                     String driverName, String driverPhone, String latitude, String longitude) {
        String sql = "INSERT INTO HitchhikerRide (eventName, pickupCity, fuelMoney, isFreeForPickup, driverName, " +
                "driverPhone, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, eventName);
            statement.setString(2, pickupCity);
            statement.setInt(3, fuelMoney);
            statement.setBoolean(4, isFreeForPickup);
            statement.setString(5, driverName);
            statement.setString(6, driverPhone);
            statement.setString(7, latitude);
            statement.setString(8, longitude);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

