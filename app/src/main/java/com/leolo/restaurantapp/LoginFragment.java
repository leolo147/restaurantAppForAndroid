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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    private String userID;

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
        mAuth = FirebaseAuth.getInstance();

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
                userLogin();
            }
        });
    }
    public void ToastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void userLogin(){
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals("")){ ToastMessage("Please fill in username"); return;}
        if(password.equals("")){ ToastMessage("Please fill in password"); return;}

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();

                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FdUser userProfile = snapshot.getValue(FdUser.class);
                            if(userProfile != null){
                                gv.setUsername(userProfile.getUsername());
                                gv.setToken(userID);
                                gv.setLogin(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Intent i = new Intent(getActivity(),MainActivity.class) ;
                    startActivity(i);

                    Log.e("Login info",""+gv.getUsername()+" "+gv.getToken());
                }else{
                    ToastMessage("Failed to login! Please check your credentials");
                }
            }
        });

    }
}
