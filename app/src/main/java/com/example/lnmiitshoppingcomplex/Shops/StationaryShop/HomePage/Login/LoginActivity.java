package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.Login;

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

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    private AppCompatButton login;
    private EditText username, password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        login = findViewById(R.id.STATIONARYSHOP_LOGIN_BTN);
        username = findViewById(R.id.STATIONARYSHOP_USERNAME);
        password = findViewById(R.id.STATIONARYSHOP_PASSWORD);
        progressBar = findViewById(R.id.STATIONARYSHOP_LOGIN_PROGRESS);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = new ProgressBar(LoginActivity.this);
                login.setClickable(false);
                username.setClickable(false);
                password.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("admin") && pass.equals("admin")){
                    //login
                    Intent loginIntent = new Intent(LoginActivity.this, StationaryShop.class);
                    loginIntent.putExtra("mode","s");
                    startActivity(loginIntent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    login.setClickable(true);
                    username.setClickable(true);
                    password.setClickable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}