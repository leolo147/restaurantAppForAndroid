package com.leolo.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.FirebaseModel.FdUser;
import com.leolo.restaurantapp.adapter.GlobalVariable;

public class LoginFragment extends Fragment {

    TextView signUp;
    Button btnLogin;
    APIInterface apiInterface;
    EditText userNameEditText, passwordEditText;
    GlobalVariable gv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        gv = (GlobalVariable)getActivity().getApplicationContext();

        signUp = view.findViewById(R.id.sign_up);
        btnLogin = view.findViewById(R.id.btnLogin);

        userNameEditText = view.findViewById(R.id.userNameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SignUpActivity.class) ;
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
//                if(username.equals("")){ ToastMessage("Please fill in username"); return;}
//                if(password.equals("")){ ToastMessage("Please fill in password"); return;}

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.child("leolo159").exists()){
                            Log.e("fa","exist");
                        }else{
                            Log.e("fa","fuck");
                        }

                        FdUser fdUser = snapshot.child("leolo159").getValue(FdUser.class);
                        if(fdUser.getPassword().equals(password)){
                            Intent i = new Intent(getActivity(),MainActivity.class) ;
                            startActivity(i);
                        }
                        Log.e("getfuck",snapshot.child("leolo159").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



//                Login login = new Login(username, password);
//                Call<LoginResponse> call1 = apiInterface.loginUser(login);
//                call1.enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        LoginResponse user = response.body();
//                        gv.setLogin(true);
//                        gv.setToken(user.accessToken);
//                        gv.setUsername(user.username);
//                        Intent i = new Intent(getActivity(),MainActivity.class) ;
//                        startActivity(i);
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        call.cancel();
//                    }
//                });
            }
        });
    }
    public void ToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
