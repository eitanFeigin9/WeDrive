package event;

import java.util.HashSet;

public class EventData {
    private String eventName;
    private String eventOwnerUserName;
    private String eventDate;
    private String eventKind;
    private HashSet<String> guestList;
    private String location;
    private String fileName;
    private double latitude;
    private double longitude;

    public EventData(String eventName,String eventOwnerUserName ,String eventDate, String eventKind, HashSet<String> guestList, String location,String fileName, double latitude, double longitude) {
        this.eventName = eventName;
        this.eventOwnerUserName=eventOwnerUserName;
        this.eventDate = eventDate;
        this.eventKind = eventKind;
        this.guestList = guestList;
        this.location = location;
        this.fileName = fileName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventKind() {
        return eventKind;
    }

    public String getEventOwnerUserName() {
        return eventOwnerUserName;
    }

    public void setEventOwnerUserName(String eventOwnerUserName) {
        this.eventOwnerUserName = eventOwnerUserName;
    }

    public HashSet<String> getGuestList() {
        return guestList;
    }

    public String getLocation() {
        return location;
    }

    public String getFileName() { return fileName; }
    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
