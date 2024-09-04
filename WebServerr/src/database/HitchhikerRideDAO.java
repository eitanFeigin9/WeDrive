package database;

import ride.HitchhikerRide;

import java.sql.*;
import java.util.HashMap;

import static utils.ServletUtils.calculateDistance;

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
                                     String driverName, String driverPhone, String latitude, String longitude, String hitchhikerName) {
        String selectSql = "SELECT id FROM Users WHERE fullName = ?";
        String insertSql = "INSERT INTO HitchhikerRide (eventName, pickupCity, fuelMoney, isFreeForPickup, driverName, " +
                "driverPhone, latitude, longitude, hitchhikerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            // Retrieve hitchhikerId using hitchhikerName
            selectStatement.setString(1, hitchhikerName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int hitchhikerId = resultSet.getInt("id");

                // Insert the new hitchhiker ride into the HitchhikerRide table
                insertStatement.setString(1, eventName);
                insertStatement.setString(2, pickupCity);
                insertStatement.setInt(3, fuelMoney);
                insertStatement.setBoolean(4, isFreeForPickup);
                insertStatement.setString(5, driverName);
                insertStatement.setString(6, driverPhone);
                insertStatement.setString(7, latitude);
                insertStatement.setString(8, longitude);
                insertStatement.setInt(9, hitchhikerId);

                insertStatement.executeUpdate();
            } else {
                // Handle case where hitchhikerName is not found
                System.out.println("Hitchhiker not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, HitchhikerRide> getAllHitchhikerRidesForUser(String fullName) {
        HashMap<String, HitchhikerRide> hitchhikingEvents = new HashMap<>();

        String getUserIDSql = "SELECT id FROM Users WHERE fullName = ?";
        String getRidesSql = "SELECT * FROM HitchhikerRide WHERE hitchhikerID = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getUserIDStatement = connection.prepareStatement(getUserIDSql);
             PreparedStatement getRidesStatement = connection.prepareStatement(getRidesSql)) {

            // Get user ID based on email
            getUserIDStatement.setString(1, fullName);
            ResultSet userResultSet = getUserIDStatement.executeQuery();

            if (userResultSet.next()) {
                int userId = userResultSet.getInt("id");

                // Get all events for the user
                getRidesStatement.setInt(1, userId);
                ResultSet ridesResultSet = getRidesStatement.executeQuery();

                while (ridesResultSet.next()) {
                    HitchhikerRide hitchhikerRide = new HitchhikerRide( ridesResultSet.getString("eventName"), ridesResultSet.getString("pickupCity"), ridesResultSet.getInt("fuelMoney"), Double.parseDouble(ridesResultSet.getString("latitude")), Double.parseDouble(ridesResultSet.getString("longitude")));
                    hitchhikingEvents.put(hitchhikerRide.getEventName(), hitchhikerRide);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hitchhikingEvents;
    }

    public boolean matchDriverToHitchhiker(HitchhikerRide newRide, String hitchhikerName, double hitchhikerLatitude, double hitchhikerLongitude) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            // Fetch the event name
            String eventName = newRide.getEventName();

            // Fetch potential drivers from the database
            String sql = "SELECT * FROM DriverRide WHERE eventName = ? AND currNumOfHitchhikers < maxCapacity";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, eventName);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    double driverLatitude = Double.parseDouble(rs.getString("latitude"));
                    double driverLongitude = Double.parseDouble(rs.getString("longitude"));
                    double maxPickupDistance = rs.getDouble("maxPickupDistance");
                    int fuelReturnsPerHitchhiker = rs.getInt("fuelReturnsPerHitchhiker");

                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);

                    if (distance <= maxPickupDistance && newRide.getFuelMoney() >= fuelReturnsPerHitchhiker) {
                        // Update driver and hitchhiker information in the database
                        String updateDriverSql = "UPDATE DriverRide SET currNumOfHitchhikers = currNumOfHitchhikers + 1, totalFuelReturns = totalFuelReturns + ? WHERE id = ?";
                        try (PreparedStatement updateDriverPs = connection.prepareStatement(updateDriverSql)) {
                            updateDriverPs.setInt(1, newRide.getFuelMoney());
                            updateDriverPs.setInt(2, rs.getInt("id"));
                            updateDriverPs.executeUpdate();
                        }

                        String updateHitchhikerSQL = "UPDATE HitchhikerRide SET driverName = ?, driverPhone = ?, isFreeForPickup = ? WHERE eventName = ? AND hitchhikerID = (SELECT id FROM Users WHERE fullName = ?)";

                        try (PreparedStatement updateHitchhikerPs = connection.prepareStatement(updateHitchhikerSQL)) {
                            updateHitchhikerPs.setString(1, newRide.getDriverName());
                            updateHitchhikerPs.setString(2, newRide.getDriverPhone());
                            updateHitchhikerPs.setBoolean(3, false);
                            updateHitchhikerPs.setString(4, newRide.getEventName());
                            updateHitchhikerPs.setString(5, hitchhikerName); // assuming userName corresponds to the hitchhiker

                            updateHitchhikerPs.executeUpdate();
                        }

                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

