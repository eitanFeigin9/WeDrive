package database;

import entity.ServerClient;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.sql.*;
import java.util.HashMap;
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
            pstmt.setObject(6, fuelMoney); // Use setObject to handle null values for Integer
            pstmt.setObject(7, isFreeForPickup); // Use setObject to handle null values for Boolean
            pstmt.executeUpdate();
            ServerClient hitchhiker = Users.getUserByUserName(hitchhikerUserName);
            hitchhiker.addNewHitchhikingEvent(hitchhikerUserName, eventName, pickupLocation, fuelMoney, Double.parseDouble(pickupLatitude), Double.parseDouble(pickupLongitude));

            System.out.println("Record inserted successfully.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

/*
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


 */
    /*
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


     */

    public boolean matchDriverToHitchhiker(String hitchhikerName, String eventName) {
        //String updateHitchhikerRideSQL = "UPDATE HitchhikerRide SET isFreeForPickup = 0 WHERE eventName = ? AND hitchhikerUserName = ?";
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
}


