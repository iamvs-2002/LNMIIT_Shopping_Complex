package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.StationaryShop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    private AppCompatButton login;
    private EditText username, passwords;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        login = findViewById(R.id.STATIONARYSHOP_LOGIN_BTN);
        username = findViewById(R.id.STATIONARYSHOP_USERNAME);
        passwords = findViewById(R.id.STATIONARYSHOP_PASSWORD);
        progressBar = findViewById(R.id.STATIONARYSHOP_LOGIN_PROGRESS);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressBar(LoginActivity.this);
                login.setClickable(false);
                username.setClickable(false);
                passwords.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                String email = username.getText().toString();
                String password = passwords.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                    String mode = "s";
                                    if(email.equals("admin@gmail.com") && password.equals("admin123")){
                                        //login
                                        mode = "s";
                                    }
                                    else if(email.equals("root@gmail.com") && password.equals("root123")){
                                        //login
                                        mode = "e";

                                    }
                                    Intent loginIntent = new Intent(LoginActivity.this, StationaryShop.class);
                                    loginIntent.putExtra("mode",mode);
                                    startActivity(loginIntent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                    login.setClickable(true);
                                    username.setClickable(true);
                                    passwords.setClickable(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });
    }
}