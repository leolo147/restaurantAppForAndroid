package com.leolo.restaurantapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.restaurants;
import com.leolo.restaurantapp.FirebaseDao.DaoRestaurant;
import com.leolo.restaurantapp.FirebaseModel.Restaurants;
import com.leolo.restaurantapp.adapter.AsiaFoodAdapter;
import com.leolo.restaurantapp.adapter.GlobalVariable;
import com.leolo.restaurantapp.adapter.PopularFoodAdapter;
import com.leolo.restaurantapp.model.AsiaFood;
import com.leolo.restaurantapp.model.PopularFood;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    AsiaFoodAdapter asiaFoodAdapter;
    GlobalVariable gv;
    DaoRestaurant daoRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv = (GlobalVariable)getActivity().getApplicationContext();

        daoRestaurant = new DaoRestaurant();

        Log.d("testleolo: ",gv.getToken()+"");

//        List<PopularFood> popularFoodList = new ArrayList<>();
//
//        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
//        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
//        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));
//        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
//        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
//        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));
//
//        setPopularRecycler(popularFoodList, view);


        List<AsiaFood> asiaFoodList = new ArrayList<>();


//        addRestaurant();

        daoRestaurant.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Restaurants restaurant = data.getValue(Restaurants.class);
                    Log.d("fail",restaurant.getRestaurant_name());
                    asiaFoodList.add(new AsiaFood(restaurant.getRestaurant_name(), restaurant.getDistrict(), restaurant.getPhone(), restaurant.getImgUrl(), restaurant.getFull_address(),data.getKey()));
                }
                setAsiaRecycler(asiaFoodList,view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        Log.d("paul",asiaFoodList.toString());


    }

//    private void setPopularRecycler(List<PopularFood> popularFoodList, View view) {
//
//        popularRecycler = view.findViewById(R.id.popular_recycler);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
//        popularRecycler.setLayoutManager(layoutManager);
//        popularFoodAdapter = new PopularFoodAdapter(getContext(), popularFoodList);
//        popularRecycler.setAdapter(popularFoodAdapter);
//
//    }

    private void setAsiaRecycler(List<AsiaFood> asiaFoodList, View view) {

        asiaRecycler = view.findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new AsiaFoodAdapter(getContext(), asiaFoodList);
        asiaRecycler.setAdapter(asiaFoodAdapter);

    }

    private void addRestaurant(){
        Restaurants restaurant = new Restaurants("18樓雞煲火鍋專門店","荃灣","荃灣眾安街55號大鴻輝(荃灣)中心18樓",
                23905390,"https://static5.orstatic.com/userphoto2/photo/Y/RLM/05G9T8522609DC5478BFA9px.jpg");
        daoRestaurant.add(restaurant).addOnSuccessListener(suc ->{
            Log.d("paul","successful");
        }).addOnFailureListener(er->{
            Log.d("paul",er.getMessage());
        });
    }
}
