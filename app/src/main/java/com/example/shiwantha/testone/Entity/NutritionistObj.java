package com.example.shiwantha.testone.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.shiwantha.testone.NutritionistNearbyActivity;

/**
 * Created by shiwantha on 12/16/16.
 */

public class NutritionistObj implements Parcelable {

        private String name;
        private String phone;
        private String no;
        private String street;
        private String city;
        private Boolean availability;
        private double rating;

        public NutritionistObj(){}

    protected NutritionistObj(Parcel in) {
        name = in.readString();
        phone = in.readString();
        no = in.readString();
        street = in.readString();
        city = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<NutritionistObj> CREATOR = new Creator<NutritionistObj>() {
        @Override
        public NutritionistObj createFromParcel(Parcel in) {
            return new NutritionistObj(in);
        }

        @Override
        public NutritionistObj[] newArray(int size) {
            return new NutritionistObj[size];
        }
    };

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

        public Boolean getAvailability() {
            return availability;
        }
        public void setAvailability(Boolean availability) {
            this.availability = availability;
        }


        public double getRating() {
            return rating;
        }
        public void setRating(double rating) {
          this.rating = rating;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(no);
        parcel.writeString(street);
        parcel.writeString(city);
        parcel.writeDouble(rating);
    }
}