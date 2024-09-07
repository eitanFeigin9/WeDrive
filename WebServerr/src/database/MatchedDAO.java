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
    hitchhikerRide.setFreeForPickup(true);
    Users.getUserByUserName(driverName).getDrivingEventByName(eventName).addSeat();
    Users.getUserByUserName(driverName).getDrivingEventByName(eventName).setMatch(false);
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


