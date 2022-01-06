package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.AddItem.AddItem;
import com.example.lnmiitshoppingcomplex.HomePage.MainActivity;
import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category.CategoryAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category.CategoryModel;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Item.ItemAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Item.ItemModel;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.Login.LoginActivity;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.AboutUs;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.ManageEmployees;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.PhotoStat.PhotoStatActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
import java.util.Map;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class StationaryShop extends AppCompatActivity {
    private List<CategoryModel> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private List<ItemModel> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    Toolbar toolbar;
    String mode;
    LinearLayout addCategoryLayout;
    static boolean isShopkeeper;
    static boolean isEmployee;
    ExtendedFloatingActionButton addItemEfab;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Uri imgUri;
    private String dlUrl;
    private StorageTask storageTask;
    private StorageReference storageReference;
    private String categoryName;
    private MaterialEditText editText;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_shop);

        storageReference = FirebaseStorage.getInstance().getReference().child("category");

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        addItemEfab = findViewById(R.id.addItemEfab);
        addItemEfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StationaryShop.this, AddItem.class));
            }
        });

        addCategoryLayout = findViewById(R.id.addcategory_layout);

        if (mode!=null && mode.equals("s")) {
            //nav bar is visible
            isShopkeeper = true;
            addCategoryLayout.setVisibility(View.VISIBLE);
            addItemEfab.setVisibility(View.VISIBLE);
        } else if (mode!=null && mode.equals("e")) {
            isEmployee = true;
        } else {
            addCategoryLayout.setVisibility(View.GONE);
            addItemEfab.setVisibility(View.GONE);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Stationary and Photostat Shop");
        setSupportActionBar(toolbar);

        prepareCategories();

        addCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        // Category
        RecyclerView category_recyclerView = findViewById(R.id.category_recyclerView);
        categoryAdapter = new CategoryAdapter(this, categoryList, mode);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_recyclerView.setLayoutManager(mLayoutManager);
        category_recyclerView.setAdapter(categoryAdapter);

        // item click listener for every category
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showItems(position);
            }
        });

        // Items
        RecyclerView item_recyclerView = findViewById(R.id.item_recyclerView);
        itemAdapter = new ItemAdapter(this, itemList, isShopkeeper, isEmployee);
        item_recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        item_recyclerView.addItemDecoration(new GridSpacingItemDecoration(50));
        item_recyclerView.setAdapter(itemAdapter);
    }

    // for add category
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.add_category, null);
        builder.setView(customLayout);
        builder.setCancelable(false);

        imageView = customLayout.findViewById(R.id.addCategoryImg);
        editText = customLayout.findViewById(R.id.categoryNameMet);
        ImageButton saveBtn = customLayout.findViewById(R.id.saveCategoryBtn);
        ImageButton closeBtn = customLayout.findViewById(R.id.dialog_cancel_btn);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.requireNonNull(editText.getText()).toString().equals("")) {
                    Toast.makeText(StationaryShop.this, "Category Name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    categoryName = editText.getText().toString();
                    addCategory();
                    dialog.dismiss();
                    dlUrl = null;
                }
            }
        });
        
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = editText.getText().toString();

                CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).setAspectRatio(1, 1)
                        .start(StationaryShop.this);
            }
        });
    }

    private void addCategory() {
        HashMap<String, Object> category = new HashMap<>();
        category.put("name", categoryName);
        if (dlUrl == null || dlUrl.equals("")) {
            category.put("imgUrl", "default");
        } else {
            category.put("imgUrl", dlUrl);
        }

        db.collection("category").document().set(category)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(StationaryShop.this, "Category Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StationaryShop.this, "Error! (Category Addition Failed)", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void prepareCategories() {
        // To be fetched from database
        db.collection("category")
            .orderBy("name")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        categoryList.clear();
                        for (DocumentSnapshot documentSnapshot: value) {
                            CategoryModel category = documentSnapshot.toObject(CategoryModel.class);
                            if (category != null) {
                                category.setId(documentSnapshot.getId());
                                categoryList.add(category);
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
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

            editText.setText(categoryName);
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
                            Picasso.get().load(dlUrl).into(imageView);
                        }
                    } else {
                        Toast.makeText(StationaryShop.this, "Error! (Uploading Image)", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showItems(int position) {

        String categoryId = categoryList.get(position).getId();
        db.collection("category").document(categoryId)
            .collection("item")
            .orderBy("name")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        itemList.clear();
                        for(DocumentSnapshot documentSnapshot: value) {
                            ItemModel item = new ItemModel();
                            item.setId(documentSnapshot.getId());
                            item.setName(documentSnapshot.get("name").toString());
                            item.setPrice(Integer.parseInt(documentSnapshot.get("price").toString()));
                            item.setQuantity(Integer.parseInt(documentSnapshot.get("quantity").toString()));
                            item.setImgUrl(documentSnapshot.get("imgUrl").toString());
                            item.setCategoryId(documentSnapshot.get("categoryId").toString());

                            itemList.add(item);
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                }
            });
    }

    private boolean isChecked = false;

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem shopkeeper_login = menu.findItem(R.id.stationaryshop_login);
        MenuItem shopkeeper_logout = menu.findItem(R.id.stationaryshop_shopkeeper_logout);
        if(mode!=null && mode.equals("s")) //shopkeeper
        {
            menu.setGroupVisible(R.id.stationaryshop_shopkeeper_menu_grp, true);
            shopkeeper_login.setVisible(false);
            shopkeeper_logout.setVisible(true);
        }
        else if(mode!=null && mode.equals("e")) //employee
        {
            menu.setGroupVisible(R.id.stationaryshop_shopkeeper_menu_grp, false);
            shopkeeper_login.setVisible(false);
            shopkeeper_logout.setVisible(true);
        }
        else
        {
            menu.setGroupVisible(R.id.stationaryshop_shopkeeper_menu_grp, false);
            shopkeeper_login.setVisible(true);
            shopkeeper_logout.setVisible(false);
        }

        MenuItem checkable = menu.findItem(R.id.stationaryshop_manage_time);
        db.collection("setting").document("settings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot!=null){

                            checkable.setChecked((Boolean) documentSnapshot.get("shopstatus"));
                        }
                    }
                });

        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.stationaryshop_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.stationaryshop_login:
                startActivity(new Intent(StationaryShop.this, LoginActivity.class));
                return true;
            case R.id.stationaryshop_manage_employees:
                startActivity(new Intent(StationaryShop.this, ManageEmployees.class));
                return true;
            case R.id.stationaryshop_about_us:
                startActivity(new Intent(StationaryShop.this, AboutUs.class));
                //Toast.makeText(getApplicationContext(),"About Us",Toast.LENGTH_LONG).show();
                return true;
            case R.id.stationaryshop_manage_time:
                isChecked = !item.isChecked();
                Map<String, Object> shopstatus = new HashMap<>();
                shopstatus.put("shopstatus", isChecked);

                String status = isChecked?"Open":"Closed";
                db.collection("setting").document("settings").update(shopstatus)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Shop status set to "+status, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error! Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
                item.setChecked(isChecked);
                //Toast.makeText(getApplicationContext(),"About Us",Toast.LENGTH_LONG).show();
                return true;
            case R.id.stationaryshop_photostat:
                Intent i = new Intent(StationaryShop.this, PhotoStatActivity.class);
                i.putExtra("mode", mode);
                startActivity(i);
                return true;
            case R.id.stationaryshop_shopkeeper_logout:
                startActivity(new Intent(StationaryShop.this, StationaryShop.class));
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
        startActivity(new Intent(StationaryShop.this, MainActivity.class));
        finish();
    }
}