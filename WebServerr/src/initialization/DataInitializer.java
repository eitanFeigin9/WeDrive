package initialization;

import database.Users;
import database.UsersDAO;
import entity.ServerClient;
import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.util.HashMap;
import java.util.HashSet;

public class DataInitializer {

    public static void initialize() {
        Users users = new Users();
        UsersDAO usersDAO = new UsersDAO();

        // Fetch all users from the database
        HashMap<String, ServerClient> userMap = new HashMap<>();

        // Example for fetching and setting up users
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
            for (EventData event : ownedEvents.values()) {
                client.addOwnedEvent(event);
            }

            // Add client to user map
            userMap.put(username, client);
        }

        // Set the static webUsers map
        Users.setWebUsers(userMap);
    }
}
