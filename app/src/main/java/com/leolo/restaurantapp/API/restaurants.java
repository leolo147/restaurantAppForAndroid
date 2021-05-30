package com.leolo.restaurantapp.API;

import com.google.gson.annotations.SerializedName;

import java.nio.Buffer;
import java.util.List;

public class restaurants {
    @SerializedName("restaurants")
    public List<rerestaurant> restaurants;


    public class rerestaurant {
        @SerializedName("_id")
        public String id;
        @SerializedName("restaurant_name")
        public String restaurant_name;
        @SerializedName("district")
        public String district;
        @SerializedName("full_address")
        public String full_address;
        @SerializedName("phone")
        public Integer phone;
        @SerializedName("imgUrl")
        public String imgUrl;
    }
}
