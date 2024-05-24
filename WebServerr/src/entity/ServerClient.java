package entity;

import event.EventData;

import java.util.HashMap;
import java.util.HashSet;

public class ServerClient {
    private  String fullName;
    private  String email;
    private  String phoneNumber;
    private  String password;
    private String securityAnswer;
    private HashMap<String, EventData> events;

    public ServerClient(String fullName, String email, String phoneNumber, String password, String securityAnswer) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.events = new HashMap<>();
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

    public boolean addNewEvent(String eventName, String eventDate, String eventKind, HashSet<String> guestList, String location){

        if(!checkEventExists(eventName)){
            events.put(eventName, new EventData(eventName,eventDate,eventKind,guestList,location));
            return true;
        }
        return false;
    }
    public HashMap<String, EventData> getEvents() { return events; }

    public boolean checkEventExists(String eventName){
        return events.containsKey(eventName);
    }

    public EventData getEventByName(String eventName) { return events.get(eventName); }
}
