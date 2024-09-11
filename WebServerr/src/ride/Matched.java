package ride;

import database.EventsDAO;
import database.Users;
import event.EventData;

import java.util.Map;

public class Matched {
    private String eventName;
    private String driverUserName;
    private String hitchhikerUserName;

    public Matched(String eventName, String driverUserName, String hitchhikerUserName) {
        this.eventName = eventName;
        this.driverUserName = driverUserName;
        this.hitchhikerUserName = hitchhikerUserName;

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDriverUserName() {
        return driverUserName;
    }

    public String getHitchhikerUserName() {
        return hitchhikerUserName;
    }

}
