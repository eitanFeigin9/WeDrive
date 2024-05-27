package ride;

public class HitchhikerRide {
    private String eventName;
    private String pickupCity;
    private int fuelMoney;

    public HitchhikerRide(String eventName, String pickupCity, int fuelMoney) {
        this.eventName = eventName;
        this.pickupCity = pickupCity;
        this.fuelMoney = fuelMoney;
    }

    public String getEventName() { return eventName; }
    public String getPickupCity() { return pickupCity; }
    public int getFuelMoney() { return fuelMoney; }

    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setPickupCity(String pickupCity) { this.pickupCity = pickupCity; }
    public void setFuelMoney(int fuelMoney) { this.fuelMoney = fuelMoney; }
}
