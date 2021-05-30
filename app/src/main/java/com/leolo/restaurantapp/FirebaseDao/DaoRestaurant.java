package com.leolo.restaurantapp.FirebaseDao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leolo.restaurantapp.FirebaseModel.Restaurants;

public class DaoRestaurant {

    private DatabaseReference databaseReference;

    public DaoRestaurant(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Restaurants.class.getSimpleName());
    }

    public Task<Void> add (Restaurants restaurant){
        return databaseReference.push().setValue(restaurant);
    }

    public Query get(){
        return databaseReference.orderByKey();
    }

}
