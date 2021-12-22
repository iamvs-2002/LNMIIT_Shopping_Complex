package com.example.lnmiitshoppingcomplex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class AddItem extends AppCompatActivity {

    ImageView addMenuItemImg;
    ExtendedFloatingActionButton saveItemEfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addMenuItemImg = findViewById(R.id.addMenuItemImg);
        addMenuItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Update Item Image", Toast.LENGTH_SHORT).show();
            }
        });

        saveItemEfab = findViewById(R.id.saveItemEfab);
        saveItemEfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Item Added to Database", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}