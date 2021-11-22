package com.example.lnmiitshoppingcomplex.Shops.StationaryShop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.HomePage.MainActivity;
import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Category.CategoryAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Category.CategoryModel;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Item.ItemAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Item.ItemModel;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Menu.AboutUs.AboutUs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationaryShop extends AppCompatActivity implements CategoryAdapter.ItemClickListener {
    private List<CategoryModel> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private List<ItemModel> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    Toolbar toolbar;
    String mode;
    LinearLayout addCategoryLayout;
    boolean isShopkeeper;
    boolean isEmployee;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_shop);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        addCategoryLayout = findViewById(R.id.addcategory_layout);

        if(mode!=null && mode.equals("s")){
            //nav bar is visible
            isShopkeeper = true;
            addCategoryLayout.setVisibility(View.VISIBLE);
        }
        else if(mode!=null && mode.equals("e")){
            isEmployee = true;
        }
        else {
            addCategoryLayout.setVisibility(View.GONE);
        }

        addCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                createAlertDialogForCategory();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Stationary and Photostat Shop");
        setSupportActionBar(toolbar);

        prepareCategories();

        // Category
        RecyclerView category_recyclerView = findViewById(R.id.category_recyclerView);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_recyclerView.setLayoutManager(mLayoutManager);
        category_recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.addItemClickListener(this);

        // Items
        RecyclerView item_recyclerView = findViewById(R.id.item_recyclerView);
        itemAdapter = new ItemAdapter(this, itemList, isShopkeeper, isEmployee);
        item_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //item_recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayout.VERTICAL, false));
        item_recyclerView.setAdapter(itemAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void createAlertDialogForCategory() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Category");

        // Set an EditText view to get user input
        final EditText name = new EditText(this);
        name.setHint("Enter name");
        alert.setView(name);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String c_name = name.getText().toString();
                Toast.makeText(StationaryShop.this, c_name+" added successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void prepareCategories() {
        CategoryModel category1 = new CategoryModel("Papers",R.drawable.papers);
        CategoryModel category2 = new CategoryModel("Notebooks",R.drawable.notebooks);
        CategoryModel category3 = new CategoryModel("Pencils",R.drawable.pencils);
        CategoryModel category4 = new CategoryModel("Pens",R.drawable.pens);
        CategoryModel category5 = new CategoryModel("Colors",R.drawable.colorsa);
        CategoryModel category6 = new CategoryModel("Paints",R.drawable.colorsb);

        categoryList = Arrays.asList(category1,category2,category3,category4,category5,category6);
        //categoryAdapter.notifyDataSetChanged();
    }

    // For category
    @Override
    public void onItemClick(int position) {
        prepareItems(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void prepareItems(int position){
       switch (position){
           case 1:
           {
               Toast.makeText(StationaryShop.this, "position", Toast.LENGTH_SHORT).show();
               // retrieve the list of items of the category = categoryList[position]
               // and store it in itemList
               ItemModel i1 = new ItemModel("Classmate",30,"url",23);
               ItemModel i2 = new ItemModel("PaperGrid",30,"url",12);
               ItemModel i3 = new ItemModel("Classmate",30,"url",11);
               ItemModel i4 = new ItemModel("Factor Notes",30,"url",33);
               ItemModel i5 = new ItemModel("Classmate",30,"url",15);
               ItemModel i6 = new ItemModel("Classmate",30,"url",12);
               ItemModel i7 = new ItemModel("PaperMate",30,"url",46);
               ItemModel i8 = new ItemModel("Luxor",30,"url",23);
               ItemModel i9 = new ItemModel("Classmate",30,"url",43);
               ItemModel i10 = new ItemModel("Luxor",30,"url",22);
               ItemModel i11 = new ItemModel("PaperGrid",30,"url",10);
               ItemModel i12 = new ItemModel("Reynolds",30,"url",5);
               ItemModel i13 = new ItemModel("Classmate",30,"url",75);
               ItemModel i14 = new ItemModel("Unigo",30,"url",66);
               ItemModel i15 = new ItemModel("Classmate",30,"url",23);

               itemList = Arrays.asList(i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15);
               itemAdapter.notifyDataSetChanged();
           }
       }
    }


    public boolean onPrepareOptionsMenu(Menu menu)
    {
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

        }
        else
        {
            menu.setGroupVisible(R.id.stationaryshop_shopkeeper_menu_grp, false);
            shopkeeper_login.setVisible(true);
            shopkeeper_logout.setVisible(false);
        }
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
                //Toast.makeText(getApplicationContext(),"Refresh Selected",Toast.LENGTH_LONG).show();
                startActivity(new Intent(StationaryShop.this, LoginActivity.class));
                return true;
            case R.id.stationaryshop_manage_employees:
                Toast.makeText(getApplicationContext(),"Manage Employees",Toast.LENGTH_LONG).show();
                return true;
            case R.id.stationaryshop_manage_items:
                Toast.makeText(getApplicationContext(),"Manage Items",Toast.LENGTH_LONG).show();
                return true;
            case R.id.stationaryshop_settings:
                Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_LONG).show();
                return true;
            case R.id.stationaryshop_about_us:
                //Toast.makeText(getApplicationContext(),"About Us",Toast.LENGTH_LONG).show();
                startActivity(new Intent(StationaryShop.this, AboutUs.class));
                return true;
            case R.id.stationaryshop_shopkeeper_logout:
                Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();
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