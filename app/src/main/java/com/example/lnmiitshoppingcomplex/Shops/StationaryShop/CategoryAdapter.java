package com.example.lnmiitshoppingcomplex.Shops.StationaryShop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnmiitshoppingcomplex.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoryModel> categoryList;
    private Context context;
    private ItemClickListener mItemClickListener;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.c_name);
            image = view.findViewById(R.id.c_img);
        }
    }
    public CategoryAdapter(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel c = categoryList.get(position);
        holder.name.setText(c.getName());
        holder.image.setImageResource(c.getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}