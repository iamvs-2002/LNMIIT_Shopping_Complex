package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.AddItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.StationaryShop;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddItem extends AppCompatActivity {

    ImageView addMenuItemImg;
    ExtendedFloatingActionButton saveItemEFab;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Uri imgUri;
    private String dlUrl;
    private StorageTask storageTask;
    private StorageReference storageReference;

    private String name;
    private String price;
    private String quantity;
    private String categoryId;
    private List<String> categoryNameList;
    private List<String> categoryIdList;
    private ArrayAdapter<String> adapter;

    private MaterialEditText nameMet;
    private MaterialEditText priceMet;
    private MaterialEditText quantityMet;
    private Spinner categorySpinner;
    private int categorySpinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        storageReference = FirebaseStorage.getInstance().getReference().child("item");

        nameMet = findViewById(R.id.itemNameMet);
        priceMet = findViewById(R.id.itemPriceMet);
        quantityMet = findViewById(R.id. itemQuantityMet);
        categorySpinner = findViewById(R.id.itemCategorySpinner);

        categoryIdList = new ArrayList<>();
        categoryNameList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        loadCategories();

        addMenuItemImg = findViewById(R.id.addMenuItemImg);
        addMenuItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameMet.getText().toString();
                price = priceMet.getText().toString();
                quantity = quantityMet.getText().toString();
                categorySpinnerPosition = categorySpinner.getSelectedItemPosition();

                CropImage.activity().setCropShape(CropImageView.CropShape.RECTANGLE).setAspectRatio(1, 1)
                        .start(AddItem.this);
            }
        });

        saveItemEFab = findViewById(R.id.saveItemEfab);
        saveItemEFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameMet.getText().toString().equals("")) {
                    Toast.makeText(AddItem.this, "Item Name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (priceMet.getText().toString().equals("")) {
                    Toast.makeText(AddItem.this, "Item Price cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (quantityMet.getText().toString().equals("")) {
                    Toast.makeText(AddItem.this, "Item Quantity cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    name = nameMet.getText().toString();
                    price = priceMet.getText().toString();
                    quantity = quantityMet.getText().toString();
                    categoryId = categoryIdList.get(categorySpinner.getSelectedItemPosition());

                    addItem();
                    finish();
                }
            }
        });
    }

    private void addItem() {
        HashMap<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", Integer.parseInt(price));
        item.put("quantity", Integer.parseInt(quantity));
        item.put("categoryId", categoryId);
        if (dlUrl == null || dlUrl.equals("")) {
            item.put("imgUrl", "default");
        } else {
            item.put("imgUrl", dlUrl);
        }

        db.collection("category").document(categoryId)
            .collection("item").document().set(item)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddItem.this, "Item Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddItem.this, "Error! (Item Addition Failed)", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void loadCategories() {
        db.collection("category")
            .orderBy("name")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        categoryIdList.add(document.getId());
                        String category = document.getString("name");
                        categoryNameList.add(category);
                    }
                    adapter.notifyDataSetChanged();
                    categorySpinner.setSelection(categorySpinnerPosition);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                imgUri = result.getUri();
                uploadImage();
            }

            nameMet.setText(name);
            priceMet.setText(price);
            quantityMet.setText(quantity);
            categorySpinner.setSelection(categorySpinnerPosition);

        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if (imgUri != null) {
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpeg");
            storageTask = fileRef.putFile(imgUri);
            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dlUrl = task.getResult().toString();

                        progressDialog.dismiss();

                        if (dlUrl != null && !dlUrl.equals("")) {
                            Picasso.get().load(dlUrl).into(addMenuItemImg);
                        }
                    } else {
                        Toast.makeText(AddItem.this, "Error! (Uploading Image)", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}