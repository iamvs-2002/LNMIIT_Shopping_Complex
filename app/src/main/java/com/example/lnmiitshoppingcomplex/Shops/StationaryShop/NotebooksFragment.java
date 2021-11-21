package com.example.lnmiitshoppingcomplex.Shops.StationaryShop;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.CategoryItemAdapter;

public class NotebooksFragment extends Fragment {

    GridView gridView;
    String[] names = {"A","B","C","D","E","F","G","H","I","J"};
    int[] url = {R.drawable.notebooks,R.drawable.notebooks,R.drawable.notebooks,R.drawable.notebooks,
            R.drawable.notebooks,R.drawable.notebooks,R.drawable.notebooks,R.drawable.notebooks,
            R.drawable.notebooks,R.drawable.notebooks};
    int[] price = {2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6};

    public NotebooksFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notebooks, container, false);
        gridView = v.findViewById(R.id.gridView);
        CategoryItemAdapter mainAdapter = new CategoryItemAdapter(v.getContext(), names,url,price);
        gridView.setAdapter(mainAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), names[position], Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}