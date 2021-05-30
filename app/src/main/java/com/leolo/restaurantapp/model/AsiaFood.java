package com.leolo.restaurantapp.model;

import java.io.Serializable;

public class AsiaFood implements Serializable {

    String restaurantName;
    String district;
    String full_address;
    String id;

    public AsiaFood(String restaurantName, String district, Integer phone, String imgUrl, String full_address,String id) {
        this.restaurantName = restaurantName;
        this.district = district;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.full_address = full_address;
        this.id = id;
    }



    Integer phone;
    String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
