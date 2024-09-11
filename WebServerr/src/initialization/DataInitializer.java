package initialization;

import database.*;
import entity.ServerClient;
import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;
import ride.Matched;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DataInitializer {

    public static void initialize() {
        Users users = new Users();
        UsersDAO usersDAO = new UsersDAO();
        DriverRideDAO driverRideDAO = new DriverRideDAO();
        HitchhikerRideDAO hitchhikerRideDAO = new HitchhikerRideDAO();
        MatchedDAO matchedDAO = new MatchedDAO(); // Create an instance of MatchedDAO

        HashMap<String, ServerClient> userMap = new HashMap<>();

        for (String username : usersDAO.getAllUsernames()) {
            // Load user data from database
            String fullName = usersDAO.getFullName(username);
            String email = usersDAO.getEmail(username);
            String phoneNumber = usersDAO.getPhoneNumber(username);
            String password = usersDAO.getPassword(username);
            String securityAnswer = usersDAO.getSecurityAnswer(username);

            ServerClient client = new ServerClient(fullName, username, email, phoneNumber, password, securityAnswer);

            // Load events for this user
            HashMap<String, EventData> ownedEvents = usersDAO.getOwnedEvents(username);
            List<DriverRide> driverRides = driverRideDAO.getAllDriverRidesByUserName(username);
            List<HitchhikerRide> hitchhikerRides = hitchhikerRideDAO.getAllHitchhikerRidesByUserName(username);
            for (EventData event : ownedEvents.values()) {
                Users.addEventToMap(event);
                client.addOwnedEvent(event);
            }
            for (DriverRide driverRide : driverRides) {
                Users.addNewDriversRideByEvents(driverRide.getEventName(), driverRide);
                client.addDrivingRide(driverRide);
            }
            for (HitchhikerRide hitchhikerRide : hitchhikerRides) {
                Users.addNewHitchhikersRideByEvents(hitchhikerRide.getEventName(), hitchhikerRide);
                client.addHitchhikerRide(hitchhikerRide);
            }
            // Add client to user map
            userMap.put(username, client);
        }

        // Set the static webUsers map
        Users.setWebUsers(userMap);

        // Initialize Matched Rides
        List<Matched> allMatches = matchedDAO.getAllMatched();
        for (Matched match : allMatches) {
            Users.addMatchedRide(match.getEventName(), match.getDriverUserName(), match.getHitchhikerUserName());
            String driverUserName = match.getDriverUserName();
            String hitchhikerUserName = match.getHitchhikerUserName();
            String currEventName = match.getEventName();
            ServerClient currDriver = Users.getUserByUserName(driverUserName);
            DriverRide currDriverRide = currDriver.getDrivingEventByName(currEventName);
            ServerClient currHitchhiker = Users.getUserByUserName(hitchhikerUserName);
            HitchhikerRide currHitchhikerRide = currHitchhiker.getHitchhikingEventByName(currEventName);
            if (currDriverRide != null && currHitchhikerRide != null) {
                currDriverRide.setMatch(true); //this driverRide is in the match table - he has a match
                currDriverRide.addNewHitchhikersRideByEvents(currHitchhikerRide); //add the hitchhiker to the driver
                currHitchhikerRide.setDriverName(currDriverRide.getDriverName());
                currHitchhikerRide.setFreeForPickup(false);
            }
        }
    }
}
