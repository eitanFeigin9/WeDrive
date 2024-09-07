package database;

import entity.ServerClient;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.util.HashMap;

public class Users {
    private static HashMap<String, ServerClient> webUsers;
    private boolean isFirstUser;
    private static HashMap<String, HitchhikerRide>hitchhikersRideByEvents;
    private static HashMap<String, DriverRide>driversRideByEvents;



    public Users() {
        Users.webUsers = new HashMap<>();
        isFirstUser =false;
        hitchhikersRideByEvents = new HashMap<>();
        driversRideByEvents= new HashMap<>();
    }

    public static HashMap<String, HitchhikerRide> getHitchhikersRideByEvents() {
        return hitchhikersRideByEvents;
    }

    public static HashMap<String, DriverRide> getDriversRideByEvents() {
        return driversRideByEvents;
    }

    public static boolean addNewUser(String fullName, String userName, String email, String phoneNumber, String password, String securityAnswer){

        if(!checkUserExists(userName)){
            ServerClient newUser = new ServerClient(fullName,userName,email,phoneNumber,password,securityAnswer);
            webUsers.put(userName, newUser);
            return true;
        }
        return false;
    }

    public static boolean isValidUser(String userName,String password){
        ServerClient user = webUsers.get(userName);
        return user.getPassword().equals(password);
    }
    public static boolean checkUserExists(String userName){
        if(webUsers!=null) {
            return webUsers.containsKey(userName);
        }
        else return false;
    }

    public HashMap<String,ServerClient> getWebUsers() {
        return webUsers;
    }

    public static void setWebUsers(HashMap<String, ServerClient> webUsers) {
        Users.webUsers = webUsers;
    }

    public synchronized boolean isFirstUser() {
        return isFirstUser;
    }

    public synchronized void setFirstUser(boolean firstUser) {
        isFirstUser = firstUser;
    }
    public static ServerClient getUserByFullName(String fullName) { return webUsers.get(fullName); }
    public static ServerClient getUserByUserName(String userName) { return webUsers.get(userName); }

}
