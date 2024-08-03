package event;

import java.util.HashSet;

public class EventData {
    private String eventName;
    private String eventDate;
    private String eventKind;
    private HashSet<String> guestList;
    private String location;

    private String fileName;


    public EventData(String eventName, String eventDate, String eventKind, HashSet<String> guestList, String location,String fileName) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventKind = eventKind;
        this.guestList = guestList;
        this.location = location;
        this.fileName = fileName;
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

    public HashSet<String> getGuestList() {
        return guestList;
    }

    public String getLocation() {
        return location;
    }

    public String getFileName() { return fileName; }
}
