package com.leolo.restaurantapp.API;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("id")
    public String id;
    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;
    @SerializedName("accessToken")
    public String accessToken;
}
