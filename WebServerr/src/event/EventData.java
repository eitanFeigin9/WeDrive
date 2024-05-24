package event;

import java.util.HashSet;

public class EventData {
    private String eventName;
    private String eventDate;
    private String eventKind;
    private HashSet<String> guestList; //needs to be HashSet
    private String location;

    public EventData(String eventName, String eventDate, String eventKind, HashSet<String> guestList, String location) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventKind = eventKind;
        this.guestList = guestList;
        this.location = location;
    }
}
