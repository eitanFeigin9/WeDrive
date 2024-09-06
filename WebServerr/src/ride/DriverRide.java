package ride;


import java.util.HashMap;

public class DriverRide {
    private String eventName;
    private String eventAddress;
    private Double eventLatitude;
    private Double eventLongitude;
    private int maxCapacity;
    private String pickupCity;
    private int fuelReturnsPerHitchhiker;
    private int currNumOfHitchhikers;
    private int totalFuelReturns;
    private HashMap<String, HitchhikerDetails> currentHitchhikers;
    private double latitude;
    private double longitude;
    private double maxPickupDistance;

    public DriverRide(String eventName,String eventAddress, int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker, double latitude, double longitude, double maxPickupDistance, double eventLatitude, double eventLongitude) {
        this.eventName = eventName;
        this.eventAddress = eventAddress;
        this.maxCapacity = maxCapacity;
        this.pickupCity = pickupCity;
        this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker;
        this.currNumOfHitchhikers = 0;
        this.totalFuelReturns = 0;
        this.currentHitchhikers = new HashMap<>();
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxPickupDistance = maxPickupDistance;
        this.eventLatitude = eventLatitude;
        this.eventLongitude = eventLongitude;
    }

    public String getEventName() { return eventName; }
    public int getMaxCapacity() { return maxCapacity; }

    public String getEventAddress() { return eventAddress; }

    public void setEventAddress(String eventAddress) { this.eventAddress = eventAddress; }

    public String getPickupCity() { return pickupCity; }
    public int getFuelReturnsPerHitchhiker() { return fuelReturnsPerHitchhiker; }

    public double getEventLatitude() { return eventLatitude; }

    public double getEventLongitude() { return eventLongitude; }

    public void setEventLatitude(double eventLatitude) { this.eventLatitude = eventLatitude;}

    public void setEventLongitude(double eventLongitude) { this.eventLongitude = eventLongitude;}

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

    public HashMap<String, HitchhikerDetails> getCurrentHitchhikers() {
        return currentHitchhikers;
    }

    public boolean addNewHitchhiker(String name, String phone, String address, double latitude, double longitude){

        if(!currentHitchhikers.containsKey(name)){
            HitchhikerDetails hitchhikerDetails = new HitchhikerDetails(name, phone, address, latitude, longitude);
            currentHitchhikers.put(name, hitchhikerDetails);
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
