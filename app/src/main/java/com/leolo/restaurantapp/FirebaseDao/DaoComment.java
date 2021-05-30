package com.leolo.restaurantapp.FirebaseDao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leolo.restaurantapp.FirebaseModel.Comment;
import com.leolo.restaurantapp.FirebaseModel.Favorite;

public class DaoComment {
    private DatabaseReference databaseReference;

    public DaoComment(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Comment.class.getSimpleName());
    }

    public Task<Void> add(Comment comment){
        return databaseReference.push().setValue(comment);
    }

    public Query getByRestaurantName(String restaurant_name){
        return databaseReference.orderByChild("restaurant_name").equalTo(restaurant_name);
    }


    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }
}
