package com.leolo.restaurantapp.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("/api/auth/signup")
    Call<CreateUserResponse> createUser(@Body User user);

    @POST("api/auth/signin")
    Call<LoginResponse> loginUser(@Body Login login);

    @POST("/api/auth/favorite")
    Call<FavoriteResponseWithId> addFavorite(@Body FavoritesResquestModel favoritesResquestModel,@Header("x-access-token") String token);

    @POST("/api/auth/comment")
    Call<FavoriteResponseWithId> addComment(@Body Comments.comment comment, @Header("x-access-token") String token);

    @GET("/api/auth/restaurant")
    Call<restaurants> getAllRestaurant();

    @GET("/api/auth/favorite/{username}")
    Call<Favorites> getUserFavorites(@Header("x-access-token") String token, @Path("username") String username);

    @GET("/api/auth/comment/{restaurant_name}")
    Call<Comments> getUserComments(@Path("restaurant_name") String restaurant_name);

    @DELETE("/api/auth/favorite/{id}")
    Call<CreateUserResponse> removeFavorite(@Header("x-access-token") String token,@Path("id") String id);

    @DELETE("/api/auth/comment/{id}")
    Call<CreateUserResponse> removeComment(@Header("x-access-token") String token,@Path("id") String id);


}
