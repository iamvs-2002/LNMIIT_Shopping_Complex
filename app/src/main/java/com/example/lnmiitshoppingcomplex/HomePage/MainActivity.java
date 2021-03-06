package com.example.lnmiitshoppingcomplex.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.Login.LoginActivity;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.StationaryShop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView shopListView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // set the app name on the toolbar
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        // list of all the shops
        List<ShopModel> shops = new ArrayList<>();
        ShopModel s1 = new ShopModel("Grocery and Fruit shop","08:00","22:00","Shopkeeper Name", Color.parseColor("#FF6347"));
        ShopModel s2 = new ShopModel("Barber shop","08:00","22:00","Shopkeeper Name", Color.parseColor("#FF018786"));
        ShopModel s3 = new ShopModel("Stationary and Photostat shop", "08:00", "22:00","Shopkeeper Name", Color.parseColor("#C71585"));
        ShopModel s4 = new ShopModel("Snacks and Tea Shop","08:00","22:00","Shopkeeper Name", Color.parseColor("#228B22"));
        ShopModel s5 = new ShopModel("Amul Dairy and Beverages shop","08:00","22:00","Shopkeeper Name", Color.parseColor("#BA55D3"));
        ShopModel s6 = new ShopModel("Laundry Shop","08:00","22:00","Shopkeeper Name", Color.parseColor("#00008B"));
        ShopModel s7 = new ShopModel("Restaurant","08:00","22:00","Shopkeeper Name", Color.parseColor("#8A2BE2"));

        shops= Arrays.asList(s1,s2,s3,s4,s5,s6,s7);

        shopListView = findViewById(R.id.SHOP_LIST_VIEW);
        // showing these shops in the recycler view using adapter
        ShopAdapter adapter = new ShopAdapter(this,shops);
        shopListView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ShopAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch(position) {
                    case 0 : Toast.makeText(MainActivity.this, "Shop1", Toast.LENGTH_SHORT).show();
                        break;

                    case 1 : Toast.makeText(MainActivity.this, "Shop2", Toast.LENGTH_SHORT).show();
                        break;

                    case 2 : //Toast.makeText(MainActivity.this, "Shop3", Toast.LENGTH_SHORT).show();
                        // Stationary and PhotoStat Shop
                        // checking if the user has already logged in
                        FirebaseUser currentUser = mAuth.getCurrentUser();

                        String m = null;
                        if(currentUser != null){
                            String mode = currentUser.getEmail();
                            if(mode.equals("admin@gmail.com"))
                                m = "s"; // shopkeeper
                            else if(mode.equals("root@gmail.com"))
                                m = "e"; // employee

                        }
                        Intent loginIntent = new Intent(getApplicationContext(), StationaryShop.class);
                        loginIntent.putExtra("mode",m);
                        startActivity(loginIntent);
                        break;

                    case 3 : Toast.makeText(MainActivity.this, "Shop4", Toast.LENGTH_SHORT).show();
                        break;

                    case 4 : Toast.makeText(MainActivity.this, "Shop5", Toast.LENGTH_SHORT).show();
                        break;

                    case 5 : Toast.makeText(MainActivity.this, "Shop6", Toast.LENGTH_SHORT).show();
                        break;

                    case 6 : Toast.makeText(MainActivity.this, "Shop7", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        shopListView.setAdapter(adapter);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.refresh:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}