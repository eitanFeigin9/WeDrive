package entity;

import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.util.HashMap;
import java.util.HashSet;

public class ServerClient {
    private  String fullName;
    private  String email;
    private  String phoneNumber;
    private  String password;
    private String securityAnswer;
    private HashMap<String, EventData> ownedEvents;
    private HashMap<String, DriverRide> drivingEvents;
    private HashMap<String,HitchhikerRide> hitchhikingEvents;

    public ServerClient(String fullName, String email, String phoneNumber, String password, String securityAnswer) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.ownedEvents = new HashMap<>();
        this.drivingEvents = new HashMap<>();
        this.hitchhikingEvents = new HashMap<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() { return securityAnswer; }

    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }

    public boolean addNewOwnedEvent(String eventName, String eventDate, String eventKind, HashSet<String> guestList, String location, String fileName){

        if(!checkOwnedEventExists(eventName)){
            ownedEvents.put(eventName, new EventData(eventName,eventDate,eventKind,guestList,location, fileName));
            return true;
        }
        return false;
    }

    public void deleteOwnedEvent(String eventName){
        ownedEvents.remove(eventName);
    }
    public HashMap<String, EventData> getOwnedEvents() { return ownedEvents; }

    public boolean checkOwnedEventExists(String eventName){
        return ownedEvents.containsKey(eventName);
    }

    public EventData getOwnedEventByName(String eventName) { return ownedEvents.get(eventName); }

    public HashMap<String, DriverRide> getDrivingEvents() { return drivingEvents; }

    public HashMap<String, HitchhikerRide> getHitchhikingEvents() { return hitchhikingEvents; }
    public boolean checkDrivingEventExists(String eventName){
        return drivingEvents.containsKey(eventName);
    }
    public boolean addNewDrivingEvent(String eventName, int maxCapacity, String pickupCity, int fuelReturnsPerPerson){

        if(!checkDrivingEventExists(eventName)){
            drivingEvents.put(eventName, new DriverRide(eventName,maxCapacity,pickupCity,fuelReturnsPerPerson));
            return true;
        }
        return false;
    }

    public void deleteDrivingEvent(String eventName){
        drivingEvents.remove(eventName);
    }
    public DriverRide getDrivingEventByName(String eventName) { return drivingEvents.get(eventName); }

    public boolean checkHitchhikingEventExists(String eventName){ return hitchhikingEvents.containsKey(eventName); }
    public boolean addNewHitchhikingEvent(String eventName, String pickupCity, int fuelMoney){

        if(!checkHitchhikingEventExists(eventName)){
            hitchhikingEvents.put(eventName, new HitchhikerRide(eventName,pickupCity,fuelMoney));
            return true;
        }
        return false;
    }
    public void deleteHitchhikingEvent(String eventName){
        hitchhikingEvents.remove(eventName);
    }
    public HitchhikerRide getHitchhikingEventByName(String eventName) { return hitchhikingEvents.get(eventName); }
}
