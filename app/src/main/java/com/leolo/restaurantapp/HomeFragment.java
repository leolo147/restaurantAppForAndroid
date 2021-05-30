package com.leolo.restaurantapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.restaurants;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv = (GlobalVariable)getActivity().getApplicationContext();

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

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<restaurants> call = apiInterface.getAllRestaurant();
        call.enqueue(new Callback<restaurants>() {
            @Override
            public void onResponse(Call<restaurants> call, Response<restaurants> response) {
                for(restaurants.rerestaurant rerestaurant : response.body().restaurants) {
                    Log.d("TAG123",rerestaurant.restaurant_name+"");
                    asiaFoodList.add(new AsiaFood(rerestaurant.restaurant_name, rerestaurant.district, rerestaurant.phone, rerestaurant.imgUrl, rerestaurant.full_address, rerestaurant.id));
                }
                setAsiaRecycler(asiaFoodList,view);
            }

            @Override
            public void onFailure(Call<restaurants> call, Throwable t) {
                Log.d("fail","leolo123"+ t.getMessage());
                call.cancel();
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
}
