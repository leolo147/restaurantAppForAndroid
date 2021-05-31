package com.leolo.restaurantapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.leolo.restaurantapp.FirebaseDao.DaoRestaurant;
import com.leolo.restaurantapp.FirebaseModel.Restaurants;
import com.leolo.restaurantapp.model.AsiaFood;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment {
    private GoogleMap mMap;
    private Geocoder geocoder;
    private DaoRestaurant daoRestaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        geocoder = new Geocoder(getContext());
        daoRestaurant = new DaoRestaurant();
        //init view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //init map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                daoRestaurant.get().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()){
                            Restaurants restaurant = data.getValue(Restaurants.class);
                            addMarker(mMap,restaurant.getFull_address(),restaurant.getRestaurant_name());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                addMarker(mMap,"荃灣海壩街22號華達樓地下F號舖","初見 Shoken");
//                addMarker(mMap,"荃灣青山公路荃灣段398號愉景新城1樓1056D&1058號舖","Chef's Stage Kitchen (愉景新城)");
//                addMarker(mMap,"荃灣眾安街55號大鴻輝(荃灣)中心18樓","18樓雞煲火鍋專門店");

//                try {
//                    List<Address> addresses= geocoder.getFromLocationName("荃灣海壩街22號華達樓地下F號舖",1);
//                    Address address = addresses.get(0);
//                    LatLng place = new LatLng(address.getLatitude(),address.getLongitude());
//                    MarkerOptions markerOptions = new MarkerOptions()
//                            .position(place)
//                            .title(address.getLocality());
//                    mMap.addMarker(markerOptions);
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,16));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        MarkerOptions markerOptions = new MarkerOptions();
//
//                        markerOptions.position(latLng);
//
//                        markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
//
//                        googleMap.clear();
//
//                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                 latLng,10
//                         ));
//
//                         googleMap.addMarker(markerOptions);
//
//                    }
//                });
            }
        });

        return view;
    }

    private void addMarker(GoogleMap mMap, String location,String restaurant_name){
        List<Address> addresses= null;
        try {
            addresses = geocoder.getFromLocationName(location,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        LatLng place = new LatLng(address.getLatitude(),address.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(place)
                .title(restaurant_name);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,16));

    }


}
