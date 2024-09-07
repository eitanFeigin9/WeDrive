package ride;


import java.util.HashMap;

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
    private HashMap<String, HitchhikerRide> currentHitchhikers;

    private double maxPickupDistance;

   /* public DriverRide(String eventName,String eventAddress, int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker, double latitude, double longitude, double maxPickupDistance, double eventLatitude, double eventLongitude) {
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

    */

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
        this.currentHitchhikers = new HashMap<>();
        this.freeCapacity=maxCapacity;
        this.isMatch=false;
    }
public boolean isEmptySeat(){
    return freeCapacity > 0;
}
    public void setEventLatitude(Double eventLatitude) {
        this.eventLatitude = eventLatitude;
    }

    public void setEventLongitude(Double eventLongitude) {
        this.eventLongitude = eventLongitude;
    }

    public HashMap<String, HitchhikerRide> getCurrentHitchhikers() {
        return currentHitchhikers;
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

    public void setEventAddress(String eventAddress) { this.eventAddress = eventAddress; }

    public int getFuelReturnsPerHitchhiker() { return fuelReturnsPerHitchhiker; }

    public double getEventLatitude() { return eventLatitude; }

    public double getEventLongitude() { return eventLongitude; }

    public void setEventLatitude(double eventLatitude) { this.eventLatitude = eventLatitude;}

    public void setEventLongitude(double eventLongitude) { this.eventLongitude = eventLongitude;}

    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public void setFuelReturnsPerHitchhiker(int fuelReturnsPerHitchhiker) { this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker; }
public void takeSeat(){
    freeCapacity=freeCapacity-1;
}
    public void addSeat(){
        freeCapacity=freeCapacity+1;
    }






    public double getMaxPickupDistance() { return maxPickupDistance; }

    public void setMaxPickupDistance(double maxPickupDistance) { this.maxPickupDistance = maxPickupDistance; }

    //public void addToTotalFuelReturns(int fuelMoney) { this.totalFuelReturns += fuelMoney; }

  /*  public void lowerTotalFuelReturns(int fuelMoney) {
        if (this.totalFuelReturns >= fuelMoney) {
            this.totalFuelReturns -= fuelMoney;
        }
    }

   */

 /*   public HashMap<String, HitchhikerDetails> getCurrentHitchhikers() {
        return currentHitchhikers;
    }

  */

 /*   public boolean addNewHitchhiker(String name, String phone, String address, double latitude, double longitude){

        if(!currentHitchhikers.containsKey(name)){
            HitchhikerDetails hitchhikerDetails = new HitchhikerDetails(name, phone, address, latitude, longitude);
            currentHitchhikers.put(name, hitchhikerDetails);
            currNumOfHitchhikers++;
            return true;
        }
        return false;
    }*/
    public boolean removeHitchhiker(String hitchhikerName){

        if(currentHitchhikers.containsKey(hitchhikerName)){
            currentHitchhikers.remove(hitchhikerName);
            addSeat();
            return true;
        }
        return false;
    }
     /*
    public boolean isTherePlace() {
        if (maxCapacity > currNumOfHitchhikers) {
            return true;
        }
        return false;
    }

    public void lowerNumberOfCurrHitchhikers() {
        this.currNumOfHitchhikers--;
    }

  */

}
