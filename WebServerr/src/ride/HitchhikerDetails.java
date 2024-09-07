package ride;

import database.Users;

public class HitchhikerDetails {
    private String name;
    private String phone;
    private String address;
    private double latitude;
    private double longitude;

    public HitchhikerDetails(String name, String phone, String address, double latitude, double longitude) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

