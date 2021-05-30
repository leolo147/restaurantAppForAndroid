package com.leolo.restaurantapp.FirebaseModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Favorite implements Serializable {

    @Exclude
    private String key;
    private String username;
    private String restaurant_name;

    private Favorite(){

    }

    public Favorite(String username, String restaurant_name) {
        this.username = username;
        this.restaurant_name = restaurant_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }
}
