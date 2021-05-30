package com.leolo.restaurantapp.API;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class Comments {
    @SerializedName("comments")
    public List<comment> comments;

    public Comments() {
    }

    public static class comment {
        @SerializedName("_id")
        public String id;
        @SerializedName("username")
        public String username;
        @SerializedName("restaurant_name")
        public String restaurant_name;
        @SerializedName("content")
        public String content;
        @SerializedName("timestamp")
        public Object timestamp;

        public comment(String username, String restaurant_name, String content) {
            //Long tsLong = System.currentTimeMillis() / 1000;
            Calendar calendar = Calendar.getInstance();
            this.username = username;
            this.restaurant_name = restaurant_name;
            this.content = content;
            this.timestamp = calendar.getTimeInMillis();
        }

        public comment(String id, String username, String restaurant_name, String content) {
            Calendar calendar = Calendar.getInstance();
            Long tsLong = System.currentTimeMillis() / 1000;
            this.id = id;
            this.username = username;
            this.restaurant_name = restaurant_name;
            this.content = content;
            this.timestamp = calendar.getTimeInMillis();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
