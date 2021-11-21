package com.example.lnmiitshoppingcomplex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.StationaryShop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView shopListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        List<ShopModel> shops = new ArrayList<>();
        ShopModel s1 = new ShopModel("Grocery Shop","08:00","22:00","Ramu Bhaiya",R.color.Black);
        shops= Arrays.asList(s1,s1,s1,s1,s1,s1,s1);

        shopListView = findViewById(R.id.SHOP_LIST_VIEW);
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

                    case 2 : Toast.makeText(MainActivity.this, "Shop3", Toast.LENGTH_SHORT).show();
                        break;

                    case 3 : Toast.makeText(MainActivity.this, "Shop4", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, StationaryShop.class));
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
}