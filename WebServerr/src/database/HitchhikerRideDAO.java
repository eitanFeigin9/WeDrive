package database;

import entity.ServerClient;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    // Insert a new record into the HitchhikerRide table
    public boolean insert(String hitchhikerUserName, String eventName, String pickupLocation,
                          String pickupLatitude, String pickupLongitude, Integer fuelMoney, Boolean isFreeForPickup) {
        if ((Users.getUserByUserName(hitchhikerUserName).getHitchhikingEvents() != null)) {
            if (Users.getUserByUserName(hitchhikerUserName).getHitchhikingEvents().containsKey(eventName)) {
                System.out.println("Hitchhiker already exists with primary key: " + hitchhikerUserName + ", " + eventName);
                return false;
            }
        }

        String sql = "INSERT INTO HitchhikerRide (hitchhikerUserName, eventName, pickupLocation, pickupLatitude, pickupLongitude, fuelMoney, isFreeForPickup) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hitchhikerUserName);
            pstmt.setString(2, eventName);
            pstmt.setString(3, pickupLocation);
            pstmt.setString(4, pickupLatitude);
            pstmt.setString(5, pickupLongitude);
            pstmt.setObject(6, fuelMoney);
            pstmt.setObject(7, isFreeForPickup);
            pstmt.executeUpdate();
            ServerClient hitchhiker = Users.getUserByUserName(hitchhikerUserName);
            hitchhiker.addNewHitchhikingEvent(hitchhikerUserName, eventName, pickupLocation, fuelMoney, Double.parseDouble(pickupLatitude), Double.parseDouble(pickupLongitude));

            System.out.println("Record inserted successfully.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public boolean matchDriverToHitchhiker(String hitchhikerName, String eventName) {
        boolean isMatchFound = false;
        if (!Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getFreeForPickup()) {
            return false;
        }
        String updateHitchhikerRideSQL = "UPDATE HitchhikerRide SET isFreeForPickup = 0 WHERE eventName = ? AND hitchhikerUserName = ?";
        String insertMatchedSQL = "INSERT INTO Matched (driverUserName, hitchhikerUserName, eventName) VALUES (?, ?, ?)";
        double hitchhikerLatitude = Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getLatitude();
        double hitchhikerLongitude = Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getLongitude();
        double fuelMoney = Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getFuelMoney();
          if(Users.getDriversRideBySpecificEvent(eventName)==null)
              return false;
            for (DriverRide driverRide : Users.getDriversRideBySpecificEvent(eventName)) {
                if (Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).getFreeForPickup()) {
                    if (driverRide.isEmptySeat()) {
                    String driverName = driverRide.getDriverName();
                    double driverLatitude = driverRide.getSourceLatitude();
                    double driverLongitude = driverRide.getSourceLongitude();
                    double maxPickupDistance = driverRide.getMaxPickupDistance();
                    double fuelReturnAsked = driverRide.getFuelReturnsPerHitchhiker();
                    // Calculate distance from driver to hitchhiker
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    // Check if the hitchhiker is within the pickup distance and if they can afford the fuel returns
                    if (distance <= maxPickupDistance && fuelMoney >= fuelReturnAsked) {

                        try (Connection connection = java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                            Users.getUserByUserName(driverName).getDrivingEventByName(eventName).addNewHitchhikersRideByEvents(Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName));
                            // Update hitchhiker status to not free for pickup
                            Users.getUserByUserName(hitchhikerName).getHitchhikingEventByName(eventName).findARide(driverName);
                            // Insert into Matched table
                            isMatchFound = true;
                            PreparedStatement insertStmt = connection.prepareStatement(insertMatchedSQL);
                            insertStmt.setString(1, driverName);
                            insertStmt.setString(2, hitchhikerName);
                            insertStmt.setString(3, eventName);
                            insertStmt.executeUpdate();
                            PreparedStatement updateStmt = connection.prepareStatement(updateHitchhikerRideSQL);
                            updateStmt.setString(1, eventName);
                            updateStmt.setString(2, hitchhikerName);
                            updateStmt.executeUpdate();
                            isMatchFound = true;


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
        }
        return isMatchFound;
    }
    public static void deleteHitchhikerRidesByEventName(String eventName) {
        String deleteQuery = "DELETE FROM HitchhikerRide WHERE eventName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, eventName);
            int rowsDeleted = preparedStatement.executeUpdate();

            System.out.println("Deleted " + rowsDeleted + " rows for event: " + eventName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<HitchhikerRide> getAllHitchhikerRidesByUserName(String userName) {
        List<HitchhikerRide> hitchhikerRides = new ArrayList<>();
        String query = "SELECT * FROM HitchhikerRide WHERE hitchhikerUserName = ?"; // SQL query with WHERE clause

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userName); // Set the parameter for hitchhikerUserName
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String hitchhikerUserName = rs.getString("hitchhikerUserName");
                String eventName = rs.getString("eventName");
                String pickupLocation = rs.getString("pickupLocation");
                double pickupLatitude = rs.getDouble("pickupLatitude");
                double pickupLongitude = rs.getDouble("pickupLongitude");
                int fuelMoney = rs.getInt("fuelMoney");
                boolean isFreeForPickup = rs.getBoolean("isFreeForPickup");

                HitchhikerRide hitchhikerRide = new HitchhikerRide(hitchhikerUserName, eventName, pickupLocation, fuelMoney, pickupLatitude, pickupLongitude, isFreeForPickup);
                hitchhikerRides.add(hitchhikerRide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hitchhikerRides;
    }

}


