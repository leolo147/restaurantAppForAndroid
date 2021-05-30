package com.leolo.restaurantapp.FirebaseModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;

public class Comment implements Serializable {
    @Exclude
    private String key;
    private String username;
    private String restaurant_name;
    private Object timestamp;
    private String content;

    public Comment(){

    }

    public Comment(String username, String restaurant_name, String content) {
        Calendar calendar = Calendar.getInstance();
        this.username = username;
        this.restaurant_name = restaurant_name;
        this.timestamp = timestamp;
        this.content = content;
        this.timestamp = calendar.getTimeInMillis();
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

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
