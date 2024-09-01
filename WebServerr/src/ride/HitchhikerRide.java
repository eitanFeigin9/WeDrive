package ride;

public class HitchhikerRide {
    private String eventName;
    private String pickupCity;
    private int fuelMoney;
    private Boolean isFreeForPickup;
    private String driverName;
    private String driverPhone;
    private double latitude;  // New attribute for latitude
    private double longitude; // New attribute for longitude

    public HitchhikerRide(String eventName, String pickupCity, int fuelMoney, double latitude, double longitude) {
        this.eventName = eventName;
        this.pickupCity = pickupCity;
        this.fuelMoney = fuelMoney;
        this.isFreeForPickup = true;
        this.driverName = "";
        this.driverPhone = "";
        this.latitude = latitude;
        this.longitude = longitude;
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
    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
