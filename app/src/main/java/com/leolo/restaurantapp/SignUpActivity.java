package com.leolo.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.leolo.restaurantapp.API.APIClient;
import com.leolo.restaurantapp.API.APIInterface;
import com.leolo.restaurantapp.API.CreateUserResponse;
import com.leolo.restaurantapp.API.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView signIn;
    Button btnSignUp;
    APIInterface apiInterface;
    EditText passwordEditText, userNameEditText, emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiInterface = APIClient.getClient().create(APIInterface.class);

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
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                System.out.println("username password email"+username + password + email);
                if(username.equals("")){ ToastMessage("Please fill in username"); return;}
                if(password.equals("")){ ToastMessage("Please fill in password"); return;}
                if(email.equals("")){ ToastMessage("Please fill in email"); return;}

                User user = new User(username, email,password);
                Call<CreateUserResponse> call1 = apiInterface.createUser(user);
                call1.enqueue(new Callback<CreateUserResponse>() {
                    @Override
                    public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                        CreateUserResponse user1 = response.body();

                        ToastMessage(user1.message);
                        Intent i = new Intent(SignUpActivity.this,MainActivity.class) ;
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                        call.cancel();
                        ToastMessage(t.getMessage());
                    }
                });
            }
        });

    }

    public void ToastMessage(String message){
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
