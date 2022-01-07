package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnmiitshoppingcomplex.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    private List<CategoryModel> categoryList;
    private String mode;

    // Click listener object created for recycler view item click
    private CategoryAdapter.onRecyclerViewItemClickListener itemListener;

    // Interface to perform action for click on item in recycler view
    public interface onRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategoryAdapter.onRecyclerViewItemClickListener itemListener) {
        this.itemListener = itemListener;
    }

    public CategoryAdapter(Context context, List<CategoryModel> categoryList, String mode) {
        this.context = context;
        this.categoryList = categoryList;
        this.mode = mode;
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
        if (c.getImgUrl().equals("default")) {
            holder.image.setImageResource(R.drawable.papers);
        } else {
            Picasso.get().load(c.getImgUrl()).into(holder.image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(position);
            }
        });

        // shopkeeper and employee can delete the category
        if (mode!=null && (mode.equals("s") || mode.equals("e"))) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String categoryId = categoryList.get(position).getId();
                    String categoryName = categoryList.get(position).getName();
                    builder.setTitle("Delete " + categoryName + " Category");
                    builder.setMessage("Deleting category will delete all it's items as well.");
                    builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("category").document(categoryId).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "Category Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), "Error! (Category Deletion)", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}