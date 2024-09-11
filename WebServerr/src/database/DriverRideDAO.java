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

    public boolean insertDriverRide(String driverUserName, String eventName, String eventAddress, String eventLatitude, String eventLongitude,
                                    int maxCapacity, String sourceLocation, String sourceLatitude, String sourceLongitude,
                                    int fuelReturnsPerHitchhiker, double maxPickupDistance) {
        String checkQuery = "SELECT * FROM DriverRide WHERE driverUserName = ? AND eventName = ?";
        String insertQuery = "INSERT INTO DriverRide (driverUserName, eventName, eventAddress, eventLatitude, eventLongitude, maxCapacity, " +
                "sourceLocation, sourceLatitude, sourceLongitude, fuelReturnsPerHitchhiker, maxPickupDistance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

                if (Users.getUserByUserName(driverUserName).getDrivingEvents() != null) {
                    if (Users.getUserByUserName(driverUserName).getDrivingEvents().containsKey(eventName)) {
                        return false;
                    }
                }
            }

            // Insert new driver into DriverRide table
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, driverUserName);
                insertStmt.setString(2, eventName);
                insertStmt.setString(3, eventAddress);
                insertStmt.setString(4, eventLatitude);
                insertStmt.setString(5, eventLongitude);
                insertStmt.setInt(6, maxCapacity);
                insertStmt.setString(7, sourceLocation);
                insertStmt.setString(8, sourceLatitude);
                insertStmt.setString(9, sourceLongitude);
                insertStmt.setInt(10, fuelReturnsPerHitchhiker);
                insertStmt.setDouble(11, maxPickupDistance);
                insertStmt.executeUpdate();
                ServerClient driver = Users.getUserByUserName(driverUserName);
                //adding the ride to Users as eventName-Ride and to the user rides
                driver.addNewDrivingEvent(eventName, eventAddress, eventLatitude, eventLongitude, maxCapacity, sourceLocation, sourceLatitude, sourceLongitude, fuelReturnsPerHitchhiker, maxPickupDistance);
                return true; // Insertion successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insertion failed
    }
    public boolean matchHitchhikersToDriver(String driverName, String eventName) {
            String updateHitchhikerRideSQL = "UPDATE HitchhikerRide SET isFreeForPickup = 0 WHERE eventName = ? AND hitchhikerUserName = ?";
            String insertMatchedSQL = "INSERT INTO Matched (driverUserName, hitchhikerUserName, eventName) VALUES (?, ?, ?)";
                boolean isMatchFound=false;
                double driverLatitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLatitude();
                double driverLongitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLongitude();
                double maxPickupDistance = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getMaxPickupDistance();
                double fuelReturnAsked = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getFuelReturnsPerHitchhiker();
                //Pass on each hitchhiker from the first one
        if(Users.getHitchhikersRideBySpecificEvent(eventName)==null)
            return false;
                for (HitchhikerRide hitchhikerRide : Users.getHitchhikersRideBySpecificEvent(eventName)) {
                    if(Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getFreeCapacity()<1)
                    {
                        return isMatchFound;
                    }
                    String hitchhikerUserName = hitchhikerRide.getHitchhikerUserName();
                    double hitchhikerLatitude = hitchhikerRide.getLatitude();
                    double hitchhikerLongitude = hitchhikerRide.getLongitude();
                    double fuelMoney = hitchhikerRide.getFuelMoney();
                    // Calculate distance from driver to hitchhiker
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);
                    // Check if the hitchhiker is within the pickup distance and if they can afford the fuel returns
                    if (distance <= maxPickupDistance && fuelMoney >= fuelReturnAsked && hitchhikerRide.getFreeForPickup()) {
                        hitchhikerRide.findARide(driverName);//Update hitchhiker ride details.
                        try (Connection connection = java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                            //Users.getUserByUserName(driverName).getHitchhikingEvents().put(eventName,hitchhikerRide);
                            Users.getUserByUserName(driverName).getDrivingEventByName(eventName).addNewHitchhikersRideByEvents(hitchhikerRide);
                        PreparedStatement updateStmt = connection.prepareStatement(updateHitchhikerRideSQL);
                        updateStmt.setString(1, eventName);
                        updateStmt.setString(2, hitchhikerUserName);
                        updateStmt.executeUpdate();
                        // Insert into Matched table
                        PreparedStatement insertStmt = connection.prepareStatement(insertMatchedSQL);
                        insertStmt.setString(1, driverName);
                        insertStmt.setString(2, hitchhikerUserName);
                        insertStmt.setString(3, eventName);
                        insertStmt.executeUpdate();
                        isMatchFound=true;
                    } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

            return isMatchFound; // Return false in case of an exception

    }

    public static void deleteDriverRidesByEventName(String eventName) {
        String deleteQuery = "DELETE FROM DriverRide WHERE eventName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, eventName);
            int rowsDeleted = preparedStatement.executeUpdate();

            System.out.println("Deleted " + rowsDeleted + " rows for event: " + eventName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DriverRide> getAllDriverRidesByUserName(String userName) {
        List<DriverRide> driverRides = new ArrayList<>();
        String query = "SELECT * FROM DriverRide WHERE driverUserName = ?"; // SQL query with WHERE clause

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userName); // Set the parameter for driverUserName
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String driverUserName = rs.getString("driverUserName");
                String eventName = rs.getString("eventName");
                String eventAddress = rs.getString("eventAddress");
                Double eventLatitude = rs.getDouble("eventLatitude");
                Double eventLongitude = rs.getDouble("eventLongitude");
                int maxCapacity = rs.getInt("maxCapacity");
                String sourceLocation = rs.getString("sourceLocation");
                double sourceLatitude = rs.getDouble("sourceLatitude");
                double sourceLongitude = rs.getDouble("sourceLongitude");
                int fuelReturnsPerHitchhiker = rs.getInt("fuelReturnsPerHitchhiker");
                double maxPickupDistance = rs.getDouble("maxPickupDistance");

                DriverRide driverRide = new DriverRide(eventName, driverUserName, eventAddress, eventLatitude, eventLongitude,
                        maxCapacity, sourceLocation, sourceLatitude, sourceLongitude,
                        fuelReturnsPerHitchhiker, maxPickupDistance);
                driverRides.add(driverRide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driverRides;
    }

}



