package ride;

public class DriverRide {
    private String eventName;
    private int maxCapacity;
    private String pickupCity;
    private int fuelReturnsPerHitchhiker;

    private int currNumOfHitchhikers;
    private int totalFuelReturns;

    public DriverRide(String eventName, int maxCapacity, String pickupCity, int fuelReturnsPerHitchhiker) {
        this.eventName = eventName;
        this.maxCapacity = maxCapacity;
        this.pickupCity = pickupCity;
        this.fuelReturnsPerHitchhiker = fuelReturnsPerHitchhiker;
        currNumOfHitchhikers = 0;
        totalFuelReturns = 0;
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
}
