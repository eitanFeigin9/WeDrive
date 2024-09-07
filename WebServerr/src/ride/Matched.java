package ride;

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

    public void setDriverUserName(String driverUserName) {
        this.driverUserName = driverUserName;
    }

    public String getHitchhikerUserName() {
        return hitchhikerUserName;
    }

    public void setHitchhikerUserName(String hitchhikerUserName) {
        this.hitchhikerUserName = hitchhikerUserName;
    }
}
