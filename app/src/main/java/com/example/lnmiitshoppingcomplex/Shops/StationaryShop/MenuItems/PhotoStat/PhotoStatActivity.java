package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.PhotoStat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class PhotoStatActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Toolbar toolbar;
    String mode;
    TextView sendDoctoEmai_TV;
    EditText bnw, c;
    Button saverate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stat);

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("PhotoStat");
        setSupportActionBar(toolbar);

        mode = getIntent().getStringExtra("mode");

        sendDoctoEmai_TV = findViewById(R.id.sendDocumentToEmail);
        bnw = findViewById(R.id.blackandwhite_EditText);
        c = findViewById(R.id.colored_EditText);
        saverate = findViewById(R.id.save_rate_btn);

        loadRates();

        if(mode!=null && mode.equals("s")) //shopkeeper
        {
            bnw.setFocusable(true);
            bnw.setFocusableInTouchMode(true);
            bnw.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            bnw.setEnabled(true);
            c.setFocusable(true);
            c.setFocusableInTouchMode(true);
            c.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            c.setEnabled(true);
            saverate.setVisibility(View.VISIBLE);

        }
        else if(mode!=null && mode.equals("e")) //employee
        {
            bnw.setFocusable(true);
            bnw.setFocusableInTouchMode(true);
            bnw.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            bnw.setEnabled(true);
            c.setFocusable(true);
            c.setFocusableInTouchMode(true);
            c.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            c.setEnabled(true);
            saverate.setVisibility(View.VISIBLE);
        }
        else
        {
            bnw.setFocusable(false);
            bnw.setFocusableInTouchMode(false);
            bnw.setInputType(InputType.TYPE_NULL);
            bnw.setEnabled(false);
            c.setFocusable(false);
            c.setFocusableInTouchMode(false);
            c.setInputType(InputType.TYPE_NULL);
            c.setEnabled(false);
            saverate.setVisibility(View.INVISIBLE);
        }

        saverate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bnwrate = bnw.getText().toString();
                String crate = c.getText().toString();

                if (bnwrate.equals("") || crate.equals("")) {
                    Toast.makeText(PhotoStatActivity.this, "Rates cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("bnwrate", bnwrate);
                    map.put("crate", crate);
                    db.collection("setting").document("settings")
                        .set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Changes saved Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error! (Saving Photostat rates)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    finish();
                }
            }
        });

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

    private void loadRates() {
        db.collection("setting").document("settings").get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot != null) {
                        bnw.setText(documentSnapshot.get("bnwrate").toString());
                        c.setText(documentSnapshot.get("crate").toString());
                    }
                }
            });
    }
}