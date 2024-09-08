package ride;

public class HitchhikerRide {
    private String hitchhikerUserName;
    private String eventName;
    private String pickupLocation;
    private int fuelMoney;
    private Boolean isFreeForPickup;
    private String driverName;
    private String area;
    private double latitude;  // New attribute for latitude
    private double longitude; // New attribute for longitude

    public HitchhikerRide(String hitchhikerUserName,String eventName, String pickupLocation, int fuelMoney, double latitude, double longitude) {
        this.hitchhikerUserName=hitchhikerUserName;
        this.eventName = eventName;
        this.pickupLocation = pickupLocation;
        this.fuelMoney = fuelMoney;
        this.isFreeForPickup = true;
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = identifyRegion(longitude,latitude);
        this.driverName=null;
    }
public void removeMatch(){
    this.isFreeForPickup = true;
    this.driverName=null;

}
    public String getArea() {
        return area;
    }

public boolean findARide(String driverName){
        if(isFreeForPickup){
            isFreeForPickup=false;
            this.driverName=driverName;
            return true;
        }
        return false;
}
    public static String identifyRegion(double longitude, double latitude) {
        if (isInUpperGalilee(longitude, latitude)) {
            return "Upper Galilee";
        } else if (isInLowerGalilee(longitude, latitude)) {
            return "Lower Galilee";
        } else if (isInSharon(longitude, latitude)) {
            return "Sharon";
        } else if (isInShfela(longitude, latitude)) {
            return "Shfela";
        } else if (isInNegev(longitude, latitude)) {
            return "Negev";
        } else if (isInArava(longitude, latitude)) {
            return "Arava";
        } else if (isInJudeaAndSamaria(longitude, latitude)) {
            return "Judea and Samaria";
        } else {
            return "Unknown Region";
        }
    }

    private static boolean isInUpperGalilee(double longitude, double latitude) {
        return (longitude >= 35.20 && longitude <= 35.80) && (latitude >= 33.00 && latitude <= 33.50);
    }

    private static boolean isInLowerGalilee(double longitude, double latitude) {
        return (longitude >= 35.00 && longitude <= 35.50) && (latitude >= 32.50 && latitude <= 33.00);
    }

    private static boolean isInSharon(double longitude, double latitude) {
        return (longitude >= 34.50 && longitude <= 35.00) && (latitude >= 32.00 && latitude <= 32.50);
    }

    private static boolean isInShfela(double longitude, double latitude) {
        return (longitude >= 34.50 && longitude <= 35.50) && (latitude >= 31.50 && latitude <= 32.00);
    }

    private static boolean isInNegev(double longitude, double latitude) {
        return (longitude >= 34.50 && longitude <= 35.50) && (latitude >= 30.00 && latitude <= 31.50);
    }

    private static boolean isInArava(double longitude, double latitude) {
        return (longitude >= 35.00 && longitude <= 35.50) && (latitude >= 29.00 && latitude <= 30.00);
    }

    private static boolean isInJudeaAndSamaria(double longitude, double latitude) {
        return (longitude >= 35.00 && longitude <= 36.00) && (latitude >= 31.50 && latitude <= 32.50);
    }

    public String getEventName() { return eventName; }
    public int getFuelMoney() { return fuelMoney; }

    public Boolean getFreeForPickup() { return isFreeForPickup; }

    public String getDriverName() { return driverName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setFuelMoney(int fuelMoney) { this.fuelMoney = fuelMoney; }
    public void setFreeForPickup(Boolean freeForPickup) { isFreeForPickup = freeForPickup; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getHitchhikerUserName() {
        return hitchhikerUserName;
    }

    public void setHitchhikerUserName(String hitchhikerUserName) {
        this.hitchhikerUserName = hitchhikerUserName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
}
