package com.leolo.restaurantapp.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Favorites {
    @SerializedName("favorites")
    public List<favorite> favorites;


    public class favorite {
        @SerializedName("_id")
        public String id;
        @SerializedName("username")
        public String username;
        @SerializedName("restaurant_name")
        public String restaurant_name;

    }
}
