package com.example.shiwantha.testone.Entity;

/**
 * Created by shiwantha on 12/17/16.
 */

public class GymObj {

    private String gymId;
    private String name;
    private double latitude;
    private double longitude;
    private String type;
    private String no;
    private String street;
    private String city;
    private String phone;
    private double price;
    private String hours;
    private String webSite;

    public GymObj(){

    }

    public String getGymId() {
        return gymId;
    }
    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double  getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double  getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }


    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getHours() {
        return hours;
    }
    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWebsite() {
        return webSite;
    }
    public void setWebsite(String webSite) {
        this.webSite = webSite;
    }
}