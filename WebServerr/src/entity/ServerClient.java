package entity;

import database.EventsDAO;
import database.Users;
import event.EventData;
import ride.DriverRide;
import ride.HitchhikerRide;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ServerClient {
    private  String fullName;
    private  String userName;

    private  String email;
    private  String phoneNumber;
    private  String password;
    private String securityAnswer;
    private HashMap<String, EventData> ownedEvents;
    private HashMap<String, DriverRide> drivingEvents;
    private HashMap<String,HitchhikerRide> hitchhikingEvents;
    //private Map<String, List<HitchhikerRide>> hitchhikersPerEvent; //EventName, list ofHitchhikerRides

    public ServerClient(String fullName,String userName ,String email, String phoneNumber, String password, String securityAnswer) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.ownedEvents = new HashMap<>();
        this.drivingEvents = new HashMap<>();
        this.hitchhikingEvents = new HashMap<>();
       // this.hitchhikersPerEvent= new HashMap<>();
        restoreOwnedEventsFromDB();
    }
    //public void addingNewHitchhikerForEventRide(String eventName,String)
    public void restoreOwnedEventsFromDB(){
// Instantiate the EventsDAO to fetch events from the database
        EventsDAO eventsDAO = new EventsDAO();

        // Use the DAO to fetch all owned events for this client (by userName)
        HashMap<String, EventData> restoredEvents = eventsDAO.getAllOwnedEventsForUser(this.userName);

        // If events are retrieved, add them to the ownedEvents map
        if (restoredEvents != null && !restoredEvents.isEmpty()) {
            this.ownedEvents.putAll(restoredEvents);
        } else {
            System.out.println("No events found for user: " + this.userName);
        }
    }
    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean ifEventExists(String eventName){
        return !checkOwnedEventExists(eventName);
    }
    public boolean addNewOwnedEvent(String eventName, String eventOwner, String eventDate, String eventKind,
                                    HashSet<String> guestList, String location, String fileName,
                                    double latitude, double longitude, String qrCodeFilePath, String invitationLink) {
        // Create a new EventData object with the updated parameters including QR code and invitation link
        EventData newEvent = new EventData(eventName, eventOwner, eventDate, eventKind, guestList, location,
                fileName, latitude, longitude, qrCodeFilePath, invitationLink);

        // Check if the event with the same name already exists in owned events
        if (!checkOwnedEventExists(eventName)) {
            ownedEvents.put(eventName, newEvent);
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
    public boolean addNewDrivingEvent( String eventName, String eventAddress, String eventLatitude, String eventLongitude,
                                      int maxCapacity, String sourceLocation, String sourceLatitude, String sourceLongitude,
                                      int fuelReturnsPerHitchhiker, double maxPickupDistance){

        if(!checkDrivingEventExists(eventName)){
            DriverRide newRide=new DriverRide(eventName,this.userName,eventAddress,Double.parseDouble(eventLatitude),Double.parseDouble(eventLongitude),maxCapacity,sourceLocation,Double.parseDouble(sourceLatitude), Double.parseDouble(sourceLongitude), fuelReturnsPerHitchhiker,maxPickupDistance);
            drivingEvents.put(eventName,newRide);
            Users.addNewDriversRideByEvents(eventName,newRide);
            return true;
        }
        return false;
    }

    public void deleteDrivingEvent(String eventName){
        drivingEvents.remove(eventName);
    }
    public DriverRide getDrivingEventByName(String eventName) { return drivingEvents.get(eventName); }

    public boolean checkHitchhikingEventExists(String eventName){ return hitchhikingEvents.containsKey(eventName); }
    public boolean addNewHitchhikingEvent(String hitchhikerUserName ,String eventName, String pickupLocation, int fuelMoney, double latitude, double longitude){

        if(!checkHitchhikingEventExists(eventName)){
            HitchhikerRide newRide = new HitchhikerRide(hitchhikerUserName,eventName,pickupLocation,fuelMoney, latitude, longitude);
            hitchhikingEvents.put(eventName,newRide );
            Users.addNewHitchhikersRideByEvents(eventName,newRide);

            return true;
        }
        return false;
    }
    public void deleteHitchhikingEvent(String eventName){
        hitchhikingEvents.remove(eventName);
    }
    public HitchhikerRide getHitchhikingEventByName(String eventName) { return hitchhikingEvents.get(eventName); }
    public boolean addOwnedEvent(EventData event) {
        // Check if the event already exists
        if (!checkOwnedEventExists(event.getEventName())) {
            // If it doesn't exist, add the event to the ownedEvents map
            ownedEvents.put(event.getEventName(), event);
            return true; // Successfully added
        }
        return false; // Event already exists
    }
   /* public boolean addDrivingEvent(EventData event) {
        // Check if the event already exists
        if (!drivingEvents.containsKey(event.getEventName())) {
            // If it doesn't exist, add the event to the ownedEvents map
            drivingEvents.put(event.getEventName(), event);
            return true; // Successfully added
        }
        return false; // Event already exists
    }

    */

}
