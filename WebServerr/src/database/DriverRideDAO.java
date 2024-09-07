package database;

import entity.ServerClient;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.sql.*;
import java.util.HashMap;
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
            // Check if the driver and event combination already exists
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
               /* checkStmt.setString(1, driverUserName);
                checkStmt.setString(2, eventName);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Driver and event combination already exists
                    return false;
                }

                */
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
                driver.addNewDrivingEvent(eventName, eventAddress, eventLatitude, eventLongitude, maxCapacity, sourceLocation, sourceLatitude, sourceLongitude, fuelReturnsPerHitchhiker, maxPickupDistance);

                return true; // Insertion successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insertion failed
    }

  /*  public boolean addNewDriverRide(String eventName, String eventAddress, String eventLatitude, String eventLongitude,
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

   */
    /*public HashMap<String, DriverRide> getAllDrivingRidesForUser(String fullName) {
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

     */

    /*public DriverRide getDriverRideForUser(String driverName, String eventName) {
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
*/
    public boolean matchHitchhikersToDriver(String driverName, String eventName) {
        int freeCapacity = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getFreeCapacity();
        if (freeCapacity > 0) {
            String updateHitchhikerRideSQL = "UPDATE HitchhikerRide SET isFreeForPickup = 0 WHERE eventName = ? AND hitchhikerUserName = ?";
            String insertMatchedSQL = "INSERT INTO Matched (driverUserName, hitchhikerUserName, eventName) VALUES (?, ?, ?)";
                double driverLatitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLatitude();
                double driverLongitude = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getSourceLongitude();
                double maxPickupDistance = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getMaxPickupDistance();
                double fuelReturnAsked = Users.getUserByUserName(driverName).getDrivingEventByName(eventName).getFuelReturnsPerHitchhiker();
                for (Map.Entry<String, HitchhikerRide> entry : Users.getHitchhikersRideByEvents().entrySet()) {
                    HitchhikerRide hitchhikerRide = entry.getValue();
                    String hitchhikerUserName = hitchhikerRide.getHitchhikerUserName();
                    //String hitchhikerPhone = resultSet.getString("phone");
                    double hitchhikerLatitude = hitchhikerRide.getLatitude();
                    double hitchhikerLongitude = hitchhikerRide.getLongitude();
                    double fuelMoney = hitchhikerRide.getFuelMoney();
                    // Calculate distance from driver to hitchhiker
                    double distance = calculateDistance(driverLatitude, driverLongitude, hitchhikerLatitude, hitchhikerLongitude);

                    // Check if the hitchhiker is within the pickup distance and if they can afford the fuel returns
                    if (distance <= maxPickupDistance && fuelMoney >= fuelReturnAsked && hitchhikerRide.getFreeForPickup()) {
// Update hitchhiker status to not free for pickup
                        hitchhikerRide.setFreeForPickup(false);
                        try (Connection connection = java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

                            Users.getUserByUserName(driverName).getHitchhikingEvents().put(eventName,hitchhikerRide);
                        Users.getUserByUserName(driverName).getDrivingEventByName(eventName).setMatch(true);
                        Users.getUserByUserName(driverName).getDrivingEventByName(eventName).takeSeat();
                        PreparedStatement updateStmt = connection.prepareStatement(updateHitchhikerRideSQL);
                        updateStmt.setString(1, eventName);
                        updateStmt.setString(2, hitchhikerUserName);
                        int rowsUpdated = updateStmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("HitchhikerRide table updated successfully.");
                        } else {
                            System.out.println("No matching records found in HitchhikerRide table.");
                        }

                        // Insert into Matched table
                        PreparedStatement insertStmt = connection.prepareStatement(insertMatchedSQL);
                        insertStmt.setString(1, driverName);
                        insertStmt.setString(2, hitchhikerUserName);
                        insertStmt.setString(3, eventName);
                        int rowsInserted = insertStmt.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Matched table inserted successfully.");
                            return true;

                        } else {
                            System.out.println("Failed to insert into Matched table.");
                        }
       /* String selectHitchhikersSql = "SELECT h.name, h.phone, hr.latitude, hr.longitude, hr.fuelMoney, hr.id " +
                "FROM HitchhikerRide hr " +
                "JOIN HitchhikerDetails h ON hr.hitchhikerID = h.id " +
                "WHERE hr.eventName = ? AND hr.isFreeForPickup = 1";
        String updateHitchhikerSql = "UPDATE HitchhikerRide SET isFreeForPickup = 0, driverName = ?, driverPhone = ? WHERE id = ?";
        String updateDriverRideSql = "UPDATE DriverRide SET currNumOfHitchhikers = currNumOfHitchhikers + 1, totalFuelReturns = totalFuelReturns + ? WHERE eventName = ? AND driverID = (SELECT id FROM Users WHERE fullName = ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectHitchhikersSql);
             PreparedStatement updateHitchhikerStatement = connection.prepareStatement(updateHitchhikerSql);
             PreparedStatement updateDriverRideStatement = connection.prepareStatement(updateDriverRideSql))
             {
        */

                        // Select hitchhikers who are free for pickup for the given event
                        //  selectStatement.setString(1, eventName);
                        //ResultSet resultSet = selectStatement.executeQuery();

                        //  while (resultSet.next()) {

                    } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        //  updateHitchhikerStatement.setString(1, driverName);
                    //updateHitchhikerStatement.setString(2, driverPhone);
                    //updateHitchhikerStatement.setInt(3, hitchhikerID);
                    //updateHitchhikerStatement.executeUpdate();

                    // Update driver ride to include the hitchhiker
                        // Return true if matching was successful

                    }
                    return false; // Return false in case of an exception

                }
        }
            return false; // Return false in case of an exception

    }
}



