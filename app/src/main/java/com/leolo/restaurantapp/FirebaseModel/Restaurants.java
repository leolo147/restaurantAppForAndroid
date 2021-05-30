package com.leolo.restaurantapp.FirebaseModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Restaurants implements Serializable {
    @Exclude
    private String key;
    private String restaurant_name;
    private String district;
    private String full_address;
    private int phone;
    private String imgUrl;

    public Restaurants(){

    }

    public Restaurants(String restaurant_name, String district, String full_address, int phone, String imgUrl) {
        this.restaurant_name = restaurant_name;
        this.district = district;
        this.full_address = full_address;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
