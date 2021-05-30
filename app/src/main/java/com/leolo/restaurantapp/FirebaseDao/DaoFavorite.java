package com.leolo.restaurantapp.FirebaseDao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leolo.restaurantapp.FirebaseModel.Favorite;
import com.leolo.restaurantapp.adapter.GlobalVariable;

public class DaoFavorite {
    private DatabaseReference databaseReference;

    public DaoFavorite(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Favorite.class.getSimpleName());
    }

    public Task<Void> add (Favorite favorite){
        return databaseReference.push().setValue(favorite);
    }

    public Query getByUsername(String username){
        return databaseReference.orderByChild("username").equalTo(username);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

}
