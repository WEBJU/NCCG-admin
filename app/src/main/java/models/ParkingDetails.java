package models;

public class ParkingDetails {
//    private String
    private double lat;
    private  double lon;
    private String cost;
    private String security_details;
    private  String operating_hours;
    private  String other;

    public ParkingDetails() {
    }

    public ParkingDetails(double lat, double lon, String cost, String security_details, String operating_hours, String other) {
        this.lat = lat;
        this.lon = lon;
        this.cost = cost;
        this.security_details = security_details;
        this.operating_hours = operating_hours;
        this.other = other;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSecurity_details() {
        return security_details;
    }

    public void setSecurity_details(String security_details) {
        this.security_details = security_details;
    }

    public String getOperating_hours() {
        return operating_hours;
    }

    public void setOperating_hours(String operating_hours) {
        this.operating_hours = operating_hours;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
