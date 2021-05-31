package com.leolo.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leolo.restaurantapp.FirebaseModel.FdUser;
import com.leolo.restaurantapp.adapter.GlobalVariable;


public class UserInfoFragment extends Fragment {
    EditText username,email;
    Button logout;
    DatabaseReference reference;
    GlobalVariable gv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv = (GlobalVariable)getActivity().getApplicationContext();
        username = view.findViewById(R.id.emailEditText);
        email = view.findViewById(R.id.usernameEditText);
        logout = view.findViewById(R.id.btnLogout);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(gv.getToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FdUser userProfile = snapshot.getValue(FdUser.class);
                if(userProfile != null){
                    username.setText(userProfile.getUsername());
                    email.setText(userProfile.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                gv.setUsername("");
                gv.setToken("");
                gv.setLogin(false);
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });



    }


}
