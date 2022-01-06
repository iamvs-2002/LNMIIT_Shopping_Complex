package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.PhotoStat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PhotoStatActivity extends AppCompatActivity {

    Toolbar toolbar;
    String mode;
    ExtendedFloatingActionButton setting_fab;
    TextView sendDoctoEmai_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stat);

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("PhotoStat");
        setSupportActionBar(toolbar);

        mode = getIntent().getStringExtra("mode");

        setting_fab = findViewById(R.id.settingsEfab);
        sendDoctoEmai_TV = findViewById(R.id.sendDocumentToEmail);

        if(mode!=null && mode.equals("s")) //shopkeeper
        {
            setting_fab.setVisibility(View.VISIBLE);
        }
        else if(mode!=null && mode.equals("e")) //employee
        {
            setting_fab.setVisibility(View.VISIBLE);
        }
        else
        {
            setting_fab.setVisibility(View.INVISIBLE);
        }

        sendDoctoEmai_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Kindly attach the documents.", Toast.LENGTH_SHORT).show();

                String email = "iamvs2002@gmail.com";
                String subject = "Documents for print";
                String body = "Kindly print these documents. I will collect them soon.";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send using..."));
            }
        });
    }
}