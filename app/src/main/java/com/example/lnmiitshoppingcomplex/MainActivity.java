package com.example.lnmiitshoppingcomplex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        List<Shop> shops = new ArrayList<>();
        Shop s1 = new Shop("Grocery Shop","08:00","22:00","Ramu Bhaiya",R.color.Black);
        shops= Arrays.asList(s1,s1,s1,s1,s1,s1,s1);

        shopListView = findViewById(R.id.SHOP_LIST_VIEW);
        ShopAdapter adapter = new ShopAdapter(this,shops);
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        shopListView.setAdapter(adapter);
    }
}