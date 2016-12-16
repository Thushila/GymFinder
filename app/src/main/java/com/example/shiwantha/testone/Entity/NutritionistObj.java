package com.example.shiwantha.testone.Entity;

/**
 * Created by shiwantha on 12/16/16.
 */

public class NutritionistObj {

        private String name;
        private String phone;
        private String no;
        private String street;
        private String city;
        private Boolean availability;
        private double rating;


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
}