package database;

import entity.ServerClient;
import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private static HashMap<String, ServerClient> webUsers;
    private boolean isFirstUser;
    private static Map<String, List<HitchhikerRide>> hitchhikersRideByEvents ;
    private static Map<String, List<DriverRide>>driversRideByEvents;
private static HashMap<String, EventData> eventsMap;


    public Users() {
        Users.webUsers = new HashMap<>();
        isFirstUser =false;
        hitchhikersRideByEvents = new HashMap<>();
        driversRideByEvents= new HashMap<>();
        eventsMap=new HashMap<>();
    }

    public static HashMap<String, EventData> getEventsMap() {
        return eventsMap;
    }
    public static void addEventToMap(EventData newEvent)
    {
        eventsMap.put(newEvent.getEventName(),newEvent);
    }   public static void removeEventToMap(String eventName)
    {
        eventsMap.remove(eventName);
    }

    public static void addNewDriversRideByEvents(String eventName, DriverRide driverRide) {
        driversRideByEvents.putIfAbsent(eventName, new ArrayList<>());
        driversRideByEvents.get(eventName).add(driverRide);
    }
    public static void addNewHitchhikersRideByEvents(String eventName,HitchhikerRide hitchhikerRide) {
        hitchhikersRideByEvents.putIfAbsent(eventName, new ArrayList<>());
        hitchhikersRideByEvents.get(eventName).add(hitchhikerRide);
    }

    public static Map<String, List<HitchhikerRide>> getHitchhikersRideByEvents() {
        return hitchhikersRideByEvents;
    }
    public static List<HitchhikerRide> getHitchhikersRideBySpecificEvent(String eventName) {
        return hitchhikersRideByEvents.get(eventName);
    }
    public static List<DriverRide> getDriversRideBySpecificEvent(String eventName) {
        return driversRideByEvents.get(eventName);
    }
    public static DriverRide findSpecificDriver(String driverName, String eventName){
        for(DriverRide curr:driversRideByEvents.get(eventName)){
            if (curr.getDriverName().equals(driverName))
                return curr;
        }
        return null;
    }
    public static void removeSpecificDriverRide(String driverName, String eventName){
        for(DriverRide curr:driversRideByEvents.get(eventName)){
            if(curr.getDriverName().equals(driverName)){
            driversRideByEvents.get(eventName).remove(curr);
        return;}}
    }
    public static void removeSpecificHitchhikerRide(String hitchhikerName, String eventName){
        for(HitchhikerRide curr:hitchhikersRideByEvents.get(eventName)){
            if(curr.getHitchhikerUserName().equals(hitchhikerName)){
                hitchhikersRideByEvents.get(eventName).remove(curr);
                return;}}
    }
    public static Map<String, List<DriverRide>> getDriversRideByEvents() {
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

    public static HashMap<String,ServerClient> getWebUsers() {
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
    public static ServerClient getUserByFullName(String fullName) {
        for (Map.Entry<String, ServerClient> entry : webUsers.entrySet()) {
            ServerClient client = entry.getValue();
            if (client.getFullName().equals(fullName)) {
                return client;
            }
        }
        return null;
    }
    public static ServerClient getUserByUserName(String userName) { return webUsers.get(userName); }

}
