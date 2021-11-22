package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnmiitshoppingcomplex.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    boolean isShopkeeper;
    boolean isEmployee;
    private List<ItemModel> items;

    // Click listener object created for recycler view item click
    private onRecyclerViewItemClickListener itemListener;

    // Interface to perform action for click on item in recycler view
    public interface onRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onRecyclerViewItemClickListener itemListener) {
        this.itemListener=itemListener;
    }

    /*
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
        ImageView itemImage;
        LinearLayout quantity;
        ImageButton decreaseQuantity, increaseQuantity;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Log.d("Mytag","Hello1");
            this.itemName=(TextView) itemView.findViewById(R.id.citem_name);
            this.itemPrice=(TextView) itemView.findViewById(R.id.citem_price);
            this.itemQuantity=(TextView) itemView.findViewById(R.id.citem_quantity);
            this.itemImage=(ImageView) itemView.findViewById(R.id.citem_img);
            this.quantity=(LinearLayout) itemView.findViewById(R.id.stationary_item_quantity_layout);
            this.decreaseQuantity = (ImageButton) itemView.findViewById(R.id.citem_quantity_decrease);
            this.increaseQuantity = (ImageButton) itemView.findViewById(R.id.citem_quantity_increase);
        }
    }

    public ItemAdapter(Context context, List<ItemModel> items, boolean isShopkeeper, boolean isEmployee) {
        Log.d("Mytag","Hello4");
        this.context=context;
        this.items=items;
        this.isShopkeeper=isShopkeeper;
        this.isEmployee=isEmployee;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Mytag","Hello5");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TextView itemName=holder.itemName;
        TextView itemPrice=holder.itemPrice;
        TextView itemQuantity=holder.itemQuantity;
        ImageView itemImage=holder.itemImage;
        LinearLayout quantity=holder.quantity;
        ImageButton decreaseQuantity=holder.decreaseQuantity;
        ImageButton increaseQuantity=holder.increaseQuantity;

        ItemModel item = items.get(position);

        itemName.setText(item.getName());
        itemPrice.setText(item.getPrice());
        itemQuantity.setText(item.getQuantity());
        itemImage.setImageResource(R.drawable.notebooks);
        // change using Glide
        itemQuantity.setText(item.getQuantity());

        Log.d("Mytag","Hello2");

        if(isShopkeeper)
            quantity.setVisibility(View.VISIBLE);
        else
            quantity.setVisibility(View.GONE);

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity() - 1);
                itemQuantity.setText(item.getQuantity());
            }
        });
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity() + 1);
                itemQuantity.setText(item.getQuantity());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}