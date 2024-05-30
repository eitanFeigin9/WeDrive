package ride;

public class HitchhikerRide {
    private String eventName;
    private String pickupCity;
    private int fuelMoney;
    private Boolean isFreeForPickup;
    private String driverName;
    private String driverPhone;

    public HitchhikerRide(String eventName, String pickupCity, int fuelMoney) {
        this.eventName = eventName;
        this.pickupCity = pickupCity;
        this.fuelMoney = fuelMoney;
        this.isFreeForPickup = true;
        this.driverName = "";
        this.driverPhone = "";
    }

    public String getEventName() { return eventName; }
    public String getPickupCity() { return pickupCity; }
    public int getFuelMoney() { return fuelMoney; }

    public Boolean getFreeForPickup() { return isFreeForPickup; }

    public String getDriverName() { return driverName; }
    public String getDriverPhone() { return driverPhone; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setPickupCity(String pickupCity) { this.pickupCity = pickupCity; }
    public void setFuelMoney(int fuelMoney) { this.fuelMoney = fuelMoney; }
    public void setFreeForPickup(Boolean freeForPickup) { isFreeForPickup = freeForPickup; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }
}
