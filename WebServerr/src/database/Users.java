package database;

import entity.ServerClient;

import java.util.HashSet;

public class Users {
    private static HashSet<ServerClient> webUsers;

    public Users(HashSet<ServerClient> webUsers) {
        Users.webUsers = new HashSet<>();
    }
    public static boolean addNewUser(String fullName, String email, String phoneNumber, String password){

        if(!checkUserExists(fullName)){
            webUsers.add(new ServerClient(fullName,email,phoneNumber,password));
            //לבדןק שזה באמת מספר ולא סתם
            return true;
        }
        return false;
    }
    public static boolean checkUserExists(String fullName){
        HashSet<String> usersName = new HashSet<>();
        for (ServerClient client : webUsers) {
            // Get the full name of the ServerClient object and add it to the list
            usersName.add(client.getFullName());
        }
        return usersName.contains(fullName);
    }

    public static HashSet<ServerClient> getWebUsers() {
        return webUsers;
    }
}
