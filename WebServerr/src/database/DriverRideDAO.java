package database;

import entity.ServerClient;
import ride.DriverRide;

import java.sql.*;
import java.util.HashMap;

import static utils.ServletUtils.calculateDistance;

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

    public boolean addNewDriverRide(String eventName, String eventAddress, String eventLatitude, String eventLongitude,
                                 int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker, int currNumOfHitchhikers,
                                 int totalFuelReturns, String latitude, String longitude, double maxPickupDistance, String driverName) {
        String selectSql = "SELECT id FROM Users WHERE fullName = ?";
        String insertSql = "INSERT INTO DriverRide (eventName, eventAddress, eventLatitude, eventLongitude, maxCapacity, " +
                "pickupCity, fuelReturnsPerHitchhiker, currNumOfHitchhikers, totalFuelReturns, latitude, longitude, maxPickupDistance, driverID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            // Retrieve driverId using driverName
            selectStatement.setString(1, driverName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int driverId = resultSet.getInt("id");

                // Insert the new driver ride into the DriverRide table
                insertStatement.setString(1, eventName);
                insertStatement.setString(2, eventAddress);
                insertStatement.setString(3, eventLatitude);
                insertStatement.setString(4, eventLongitude);
                insertStatement.setInt(5, maxCapacity);
                insertStatement.setString(6, pickupCity);
                insertStatement.setInt(7, fuelReturnsPerHitchhiker);
                insertStatement.setInt(8, currNumOfHitchhikers);
                insertStatement.setInt(9, totalFuelReturns);
                insertStatement.setString(10, latitude);
                insertStatement.setString(11, longitude);
                insertStatement.setDouble(12, maxPickupDistance);
                insertStatement.setInt(13, driverId); // Set the driverId

                insertStatement.executeUpdate();
            } else {
                // Handle case where driverName is not found
                System.out.println("Driver not found.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public HashMap<String, DriverRide> getAllDrivingRidesForUser(String fullName) {
        HashMap<String, DriverRide> drivingEvents = new HashMap<>();

        String getUserIDSql = "SELECT id FROM Users WHERE fullName = ?";
        String getRidesSql = "SELECT * FROM DriverRide WHERE driverID = ?";

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
                    DriverRide driverRide = new DriverRide( ridesResultSet.getString("eventName"), ridesResultSet.getString("eventAddress"), ridesResultSet.getInt("maxCapacity"),ridesResultSet.getString("pickupCity"),ridesResultSet.getInt("fuelReturnsPerHitchhiker"), Double.parseDouble(ridesResultSet.getString("latitude")), Double.parseDouble(ridesResultSet.getString("longitude")), ridesResultSet.getDouble("maxPickupDistance"),Double.parseDouble(ridesResultSet.getString("eventLatitude")),Double.parseDouble(ridesResultSet.getString("eventLongitude")));
                    drivingEvents.put(driverRide.getEventName(), driverRide);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivingEvents;
    }

    public DriverRide getDriverRideForUser(String driverName, String eventName) {
        DriverRide driverRide = null;

        String getUserIDSql = "SELECT id FROM Users WHERE fullName = ?";
        String getRideSql = "SELECT * FROM DriverRide WHERE driverID = ? AND eventName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement getUserIDStatement = connection.prepareStatement(getUserIDSql);
             PreparedStatement getRideStatement = connection.prepareStatement(getRideSql)) {

            // Get user ID based on the full name (driverName)
            getUserIDStatement.setString(1, driverName);
            ResultSet userResultSet = getUserIDStatement.executeQuery();

            if (userResultSet.next()) {
                int userId = userResultSet.getInt("id");

                // Get the specific driver ride for the given event name
                getRideStatement.setInt(1, userId);
                getRideStatement.setString(2, eventName);
                ResultSet rideResultSet = getRideStatement.executeQuery();

                if (rideResultSet.next()) {
                    driverRide = new DriverRide(
                            rideResultSet.getString("eventName"),
                            rideResultSet.getString("eventAddress"),
                            rideResultSet.getInt("maxCapacity"),
                            rideResultSet.getString("pickupCity"),
                            rideResultSet.getInt("fuelReturnsPerHitchhiker"),
                            rideResultSet.getDouble("latitude"),
                            rideResultSet.getDouble("longitude"),
                            rideResultSet.getDouble("maxPickupDistance"),
                            rideResultSet.getDouble("eventLatitude"),
                            rideResultSet.getDouble("eventLongitude")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return driverRide;
    }

    public boolean matchHitchhikersToDriver(String driverName,String driverPhone, DriverRide newRide, String eventName, Double driverLatitude, Double driverLongitude, Double maxPickupDistance) {
        String selectHitchhikersSql = "SELECT h.name, h.phone, hr.latitude, hr.longitude, hr.fuelMoney, hr.id " +
                "FROM HitchhikerRide hr " +
                "JOIN HitchhikerDetails h ON hr.hitchhikerID = h.id " +
                "WHERE hr.eventName = ? AND hr.isFreeForPickup = 1";
        String updateHitchhikerSql = "UPDATE HitchhikerRide SET isFreeForPickup = 0, driverName = ?, driverPhone = ? WHERE id = ?";
        String updateDriverRideSql = "UPDATE DriverRide SET currNumOfHitchhikers = currNumOfHitchhikers + 1, totalFuelReturns = totalFuelReturns + ? WHERE eventName = ? AND driverID = (SELECT id FROM Users WHERE fullName = ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectHitchhikersSql);
             PreparedStatement updateHitchhikerStatement = connection.prepareStatement(updateHitchhikerSql);
             PreparedStatement updateDriverRideStatement = connection.prepareStatement(updateDriverRideSql)) {

            // Select hitchhikers who are free for pickup for the given event
            selectStatement.setString(1, eventName);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String hitchhikerName = resultSet.getString("name");
                String hitchhikerPhone = resultSet.getString("phone");
                double hitchhikerLatitude = Double.parseDouble(resultSet.getString("latitude"));
                double hitchhikerLongitude = Double.parseDouble(resultSet.getString("longitude"));
                double fuelMoney = resultSet.getDouble("fuelMoney");
                int hitchhikerID = resultSet.getInt("id");

                // Calculate distance from driver to hitchhiker
                double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);

                // Check if the hitchhiker is within the pickup distance and if they can afford the fuel returns
                if (distance <= maxPickupDistance && fuelMoney >= newRide.getFuelReturnsPerHitchhiker()) {
                    // Update hitchhiker status to not free for pickup
                    updateHitchhikerStatement.setString(1, driverName);
                    updateHitchhikerStatement.setString(2, driverPhone);
                    updateHitchhikerStatement.setInt(3, hitchhikerID);
                    updateHitchhikerStatement.executeUpdate();

                    // Update driver ride to include the hitchhiker
                    updateDriverRideStatement.setDouble(1, fuelMoney);
                    updateDriverRideStatement.setString(2, eventName);
                    updateDriverRideStatement.setString(3, driverName);
                    updateDriverRideStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
        return true; // Return true if matching was successful
    }
}

