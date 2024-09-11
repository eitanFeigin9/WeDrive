package ride;


import database.Users;
import javafx.scene.Parent;

import java.util.*;

public class DriverRide {
    private String eventName;
    private String driverName;
    private String eventAddress;
    private Double eventLatitude;
    private Double eventLongitude;
    private int maxCapacity;
    private int freeCapacity;

    private String sourceLocation;
    private double sourceLatitude;
    private double sourceLongitude;
    private int fuelReturnsPerHitchhiker;
    private boolean isMatch;
    private static Map<String, HitchhikerRide> currentHitchhikers ;//Username of the hitchhiker

    private double maxPickupDistance;


    public DriverRide(String eventName, String driverName,String eventAddress, Double eventLatitude, Double eventLongitude, int maxCapacity, String sourceLocation, double sourceLatitude, double sourceLongitude, int fuelReturnsPerHitchhiker, double maxPickupDistance) {
        this.eventName = eventName;
        this.driverName=driverName;
        this.eventAddress = eventAddress;
        this.eventLatitude = eventLatitude;
        this.eventLongitude = eventLongitude;
        this.maxCapacity = maxCapacity;
        this.sourceLocation = sourceLocation;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker;
        this.maxPickupDistance = maxPickupDistance;
        this.currentHitchhikers = new LinkedHashMap<>();
        this.freeCapacity=maxCapacity;
        this.isMatch=false;
    }

    public Map<String, HitchhikerRide> getHitchhikersForThisRide(){return currentHitchhikers;}
    public String getDriverFullName()
    {
        return Users.getUserByUserName(driverName).getFullName();
    }
    public String getDriverPhoneNumber()
    {
        return Users.getUserByUserName(driverName).getPhoneNumber();
    }
    public boolean isEmptySeat(){
    return freeCapacity > 0;
}

    public static Map<String, HitchhikerRide> getCurrentHitchhikers() {
        return currentHitchhikers;
    }

    public void addNewHitchhikersRideByEvents(HitchhikerRide hitchhikerRide) {
        currentHitchhikers.put(Users.getUserByUserName(hitchhikerRide.getHitchhikerUserName()).getFullName(), hitchhikerRide);
        this.isMatch=true;
        takeSeat();
    }

    public int getFreeCapacity() {
        return freeCapacity;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public double getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(double sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public double getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(double sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }

    public String getEventName() { return eventName; }
    public int getMaxCapacity() { return maxCapacity; }

    public String getEventAddress() { return eventAddress; }


    public int getFuelReturnsPerHitchhiker() { return fuelReturnsPerHitchhiker; }

    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity;
    if (currentHitchhikers.size()==maxCapacity){isMatch=false;}
        freeCapacity= maxCapacity-currentHitchhikers.size();
    }

    public void setFuelReturnsPerHitchhiker(int fuelReturnsPerHitchhiker) { this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker; }
    public void takeSeat(){
    freeCapacity=freeCapacity-1;
}
    public void addSeat(){
        freeCapacity=freeCapacity+1;
    }

    public double getMaxPickupDistance() { return maxPickupDistance; }

    public void setMaxPickupDistance(double maxPickupDistance) { this.maxPickupDistance = maxPickupDistance; }

    public boolean removeHitchhiker(String hitchhikerName){

        if(currentHitchhikers.containsKey(hitchhikerName)){
            currentHitchhikers.remove(hitchhikerName);
            if(currentHitchhikers.isEmpty())
            {
                isMatch=false;
            }
            return true;
        }
        return false;
    }
    public boolean removeHitchhikerByFullName(String hitchhikerName){

        if(currentHitchhikers.containsKey(Users.getUserByUserName(hitchhikerName).getFullName())){
            currentHitchhikers.remove(Users.getUserByUserName(hitchhikerName).getFullName());
            if(currentHitchhikers.isEmpty())
            {
                isMatch=false;
            }
            return true;
        }
        return false;
    }

}
