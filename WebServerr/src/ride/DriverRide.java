package ride;

import event.EventData;

import java.util.HashMap;
import java.util.HashSet;

public class DriverRide {
    private String eventName;
    private int maxCapacity;
    private String pickupCity;
    private int fuelReturnsPerHitchhiker;
    private int currNumOfHitchhikers;
    private int totalFuelReturns;
    private HashMap<String, String> currentHitchhikers;

    private double latitude;  // New attribute for latitude
    private double longitude; // New attribute for longitude
    private double maxPickupDistance;

    public DriverRide(String eventName, int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker, double latitude, double longitude, double maxPickupDistance) {
        this.eventName = eventName;
        this.maxCapacity = maxCapacity;
        this.pickupCity = pickupCity;
        this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker;
        this.currNumOfHitchhikers = 0;
        this.totalFuelReturns = 0;
        this.currentHitchhikers = new HashMap<>();
        this.latitude = latitude;   // Initialize latitude
        this.longitude = longitude;  // Initialize longitude
        this.maxPickupDistance = maxPickupDistance;
    }

    public String getEventName() { return eventName; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getPickupCity() { return pickupCity; }
    public int getFuelReturnsPerHitchhiker() { return fuelReturnsPerHitchhiker; }

    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setPickupCity(String pickupCity) { this.pickupCity = pickupCity; }
    public void setFuelReturnsPerHitchhiker(int fuelReturnsPerHitchhiker) { this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker; }

    public int getCurrNumOfHitchhikers() { return currNumOfHitchhikers; }

    public int getTotalFuelReturns() { return totalFuelReturns; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getMaxPickupDistance() { return maxPickupDistance; }

    public void setMaxPickupDistance(double maxPickupDistance) { this.maxPickupDistance = maxPickupDistance; }

    public void addToTotalFuelReturns(int fuelMoney) { this.totalFuelReturns += fuelMoney; }

    public void lowerTotalFuelReturns(int fuelMoney) {
        if (this.totalFuelReturns >= fuelMoney) {
            this.totalFuelReturns -= fuelMoney;
        }
    }

    public HashMap<String, String> getCurrentHitchhikers() {
        return currentHitchhikers;
    }

    public boolean addNewHitchhiker(String hitchhikerName, String hitchhikerPhone){

        if(!currentHitchhikers.containsKey(hitchhikerName)){
            currentHitchhikers.put(hitchhikerName, hitchhikerPhone);
            currNumOfHitchhikers++;
            return true;
        }
        return false;
    }
    public boolean removeHitchhiker(String hitchhikerName){

        if(currentHitchhikers.containsKey(hitchhikerName)){
            currentHitchhikers.remove(hitchhikerName);
            currNumOfHitchhikers--;
            return true;
        }
        return false;
    }
    public boolean isTherePlace() {
        if (maxCapacity > currNumOfHitchhikers) {
            return true;
        }
        return false;
    }

    public void lowerNumberOfCurrHitchhikers() {
        this.currNumOfHitchhikers--;
    }

}
