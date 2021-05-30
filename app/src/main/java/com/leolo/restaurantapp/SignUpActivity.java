package com.leolo.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.FirebaseModel.FdUser;


public class SignUpActivity extends AppCompatActivity {

    TextView signIn;
    Button btnSignUp;
    APIInterface apiInterface;
    EditText passwordEditText, userNameEditText, emailEditText;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        mAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.sign_up);
        btnSignUp = findViewById(R.id.btnSignUp);
        passwordEditText = findViewById(R.id.passwordEditText);
        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);


        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                    if (passwordEditText.getText().toString().trim().length() < 8)
                        passwordEditText.setError("at least 8 char");
                    else
                        passwordEditText.setError(null);

            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                        .commit();
            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    public void ToastMessage(String message){
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private  void registerUser(){
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        System.out.println("username password email"+username + password + email);
        if(username.equals("")){ ToastMessage("Please fill in username"); return;}
        if(password.equals("")){ ToastMessage("Please fill in password"); return;}
        if(email.equals("")){ ToastMessage("Please fill in email"); return;}

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FdUser user = new FdUser(username,email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                ToastMessage("User has been registered successfully");
                            }else{
                                ToastMessage("Failed to register! Try again!");
                            }
                        }
                    });

                }else{
                    ToastMessage("Failed to register! Try again!");
                }
            }
        });
    }
}
