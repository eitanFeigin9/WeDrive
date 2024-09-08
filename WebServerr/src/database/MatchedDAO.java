package database;
import ride.HitchhikerRide;

import java.sql.*;

public class MatchedDAO {
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
public void deleteMatch(String eventName,String driverName,String hitchhiker){
    removeMatchDB(eventName,hitchhiker);
    removeMatchFromDriverAndHitchhiker(eventName, driverName, hitchhiker);
    Connection connection = null;
    PreparedStatement deleteStmt = null;

    try {
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

        String deleteSQL = "DELETE FROM Matched WHERE eventName = ? AND driverUserName = ? AND hitchhikerUserName = ?";
        deleteStmt = connection.prepareStatement(deleteSQL);
        deleteStmt.setString(1, eventName);
        deleteStmt.setString(2, driverName);
        deleteStmt.setString(3, hitchhiker);

        int rowsDeleted = deleteStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Match deleted successfully.");
        } else {
            System.out.println("No match found with the specified criteria.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources
        try {
            if (deleteStmt != null) deleteStmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public void removeMatchFromDriverAndHitchhiker(String eventName,String driverName,String hitchhiker){
    HitchhikerRide hitchhikerRide = Users.getUserByUserName(hitchhiker).getHitchhikingEventByName(eventName);
    hitchhikerRide.removeMatch();
    Users.getUserByUserName(driverName).getDrivingEventByName(eventName).addSeat();
    //Users.getUserByUserName(driverName).getDrivingEventByName(eventName).removeMatch(hitchhiker);
    //Users.getUserByUserName(driverName).getDrivingEventByName(eventName).setMatch(false);
    }
    public void removeMatchDB(String eventName,String hitchhiker){
        String updateQuery = "UPDATE HitchhikerRide SET isFreeForPickup = 1 WHERE eventName = ? AND hitchhikerUserName = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, eventName);
            pstmt.setString(2, hitchhiker);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }


/*
 String updateHitchhikerRideSQL = "UPDATE HitchhikerRide SET isFreeForPickup = 0 WHERE eventName = ? AND hitchhikerUserName = ?";
            String insertMatchedSQL = "INSERT INTO Matched (driverUserName, hitchhikerUserName, eventName) VALUES (?, ?, ?)";
                boolean isMatchFound=false;
                double driverLatitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLatitude();
                double driverLongitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLongitude();
                double maxPickupDistance = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getMaxPickupDistance();
                double fuelReturnAsked = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getFuelReturnsPerHitchhiker();
                //Pass on each hitchhiker from the first one
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
 */