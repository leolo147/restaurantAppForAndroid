package com.leolo.restaurantapp.API;

import com.google.gson.annotations.SerializedName;

public class FavoritesResquestModel {

        @SerializedName("username")
        public String username;
        @SerializedName("restaurant_name")
        public String restaurant_name;

    public FavoritesResquestModel(String username, String restaurant_name) {
        this.username = username;
        this.restaurant_name = restaurant_name;
    }
}
