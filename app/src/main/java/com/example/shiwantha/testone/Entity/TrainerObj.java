package com.example.shiwantha.testone.Entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by MSI on 12/16/2016.
 */

public class TrainerObj implements Parcelable {
    private String name;
    private String location;
    private String services;
    private String facilityOrHouseCalls;
    private String phone;
    private int price;
    private String certification;
    private boolean insured;
    private int rating;
    private String gender;

    public TrainerObj(){}

    protected TrainerObj(Parcel in) {
        name = in.readString();
        location = in.readString();
        services = in.readString();
        facilityOrHouseCalls = in.readString();
        phone = in.readString();
        price = in.readInt();
        certification = in.readString();
        insured = in.readByte() != 0;
        rating = in.readInt();
        gender = in.readString();
    }

    public static final Creator<TrainerObj> CREATOR = new Creator<TrainerObj>() {
        @Override
        public TrainerObj createFromParcel(Parcel in) {
            return new TrainerObj(in);
        }

        @Override
        public TrainerObj[] newArray(int size) {
            return new TrainerObj[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFacilityOrHouseCalls() {
        return facilityOrHouseCalls;
    }

    public void setFacilityOrHouseCalls(String facilityOrHouseCalls) {
        this.facilityOrHouseCalls = facilityOrHouseCalls;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public boolean getInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(services);
        parcel.writeString(facilityOrHouseCalls);
        parcel.writeString(phone);
        parcel.writeInt(price);
        parcel.writeString(certification);
        parcel.writeByte((byte) (insured ? 1 : 0));
        parcel.writeInt(rating);
        parcel.writeString(gender);
    }
}
