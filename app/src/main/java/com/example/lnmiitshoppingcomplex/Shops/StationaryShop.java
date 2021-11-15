package com.example.lnmiitshoppingcomplex.Shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.CategoryAdapter;
import com.example.lnmiitshoppingcomplex.CategoryModel;
import com.example.lnmiitshoppingcomplex.NotebooksFragment;
import com.example.lnmiitshoppingcomplex.R;

import java.util.ArrayList;
import java.util.List;

public class StationaryShop extends AppCompatActivity implements CategoryAdapter.ItemClickListener {
    private List<CategoryModel> categoryList = new ArrayList<>();
    private CategoryAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_shop);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Stationary and Photostat Shop");
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new CategoryAdapter(categoryList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.addItemClickListener(this);
        prepareMovieData();
    }
    private void prepareMovieData() {
        CategoryModel category = new CategoryModel("All",R.drawable.stationaryb);
        categoryList.add(category);
        category = new CategoryModel("Papers",R.drawable.papers);
        categoryList.add(category);
        category = new CategoryModel("Notebooks",R.drawable.notebooks);
        categoryList.add(category);
        category = new CategoryModel("Pencils",R.drawable.pencils);
        categoryList.add(category);
        category = new CategoryModel("Pens",R.drawable.pens);
        categoryList.add(category);
        category = new CategoryModel("Colors",R.drawable.colorsa);
        categoryList.add(category);
        category = new CategoryModel("Paints",R.drawable.colorsb);
        categoryList.add(category);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if (position==2)
        {
            loadFragment(new NotebooksFragment());
        }
        Toast.makeText(StationaryShop.this, categoryList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}