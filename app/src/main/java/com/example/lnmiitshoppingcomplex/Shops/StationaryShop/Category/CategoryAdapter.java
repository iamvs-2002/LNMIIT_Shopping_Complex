package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Category;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<CategoryModel> categoryList;

    // Click listener object created for recycler view item click
    private CategoryAdapter.onRecyclerViewItemClickListener itemListener;

    // Interface to perform action for click on item in recycler view
    public interface onRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategoryAdapter.onRecyclerViewItemClickListener itemListener) {
        this.itemListener=itemListener;
    }

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.c_name);
            this.image = itemView.findViewById(R.id.c_img);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);

        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel c = categoryList.get(position);
        holder.name.setText(c.getName());
        holder.image.setImageResource(c.getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}