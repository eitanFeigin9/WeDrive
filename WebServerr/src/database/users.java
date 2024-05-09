package database;

import entity.ServerClient;

import java.util.HashSet;

public class users {
    private HashSet<ServerClient> webUsers;

    public users(HashSet<ServerClient> webUsers) {
        this.webUsers = new HashSet<>();
    }
    public boolean addNewUser(String fullName, String email, String phoneNumber, String password){
        if(!checkUserExists(fullName)){
            this.webUsers.add(new ServerClient(fullName,email,phoneNumber,password));
            return true;
        }
        return false;
    }
    public boolean checkUserExists(String fullName){
        HashSet<String> usersName = new HashSet<>();
        for (ServerClient client : webUsers) {
            // Get the full name of the ServerClient object and add it to the list
            usersName.add(client.getFullName());
        }
        return usersName.contains(fullName);
    }
}
