package com.example.shiwantha.testone.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shiwantha on 12/17/16.
 */

public class GymObj implements Parcelable{

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
    private String weekDayHours;
    private String saturdayHours;
    private String sundayHours;

    public GymObj(){

    }

    protected GymObj(Parcel in) {
        gymId = in.readString();
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        type = in.readString();
        no = in.readString();
        street = in.readString();
        city = in.readString();
        phone = in.readString();
        price = in.readDouble();
        hours = in.readString();
        webSite = in.readString();
        sundayHours=in.readString();
        weekDayHours=in.readString();
        saturdayHours=in.readString();
    }

    public static final Creator<GymObj> CREATOR = new Creator<GymObj>() {
        @Override
        public GymObj createFromParcel(Parcel in) {
            return new GymObj(in);
        }

        @Override
        public GymObj[] newArray(int size) {
            return new GymObj[size];
        }
    };

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


    public String getWebsite() {
        return webSite;
    }
    public void setWebsite(String webSite) {
        this.webSite = webSite;
    }

    public String getWeekDayHours() {
        return weekDayHours;
    }
    public void setWeekDayHours(String weekDayHours) {
        this.weekDayHours = weekDayHours;
    }

    public String getSaturdayHours() {
        return saturdayHours;
    }
    public void setSaturdayHours(String saturdayHours) {
        this.saturdayHours = saturdayHours;
    }public String getSundayHours() {
        return sundayHours;
    }
    public void setSundayHours(String sundayHours) {
        this.sundayHours = sundayHours;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gymId);
        parcel.writeString(name);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(type);
        parcel.writeString(no);
        parcel.writeString(street);
        parcel.writeString(city);
        parcel.writeString(phone);
        parcel.writeDouble(price);
        parcel.writeString(hours);
        parcel.writeString(webSite);
    }
}