package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnmiitshoppingcomplex.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    boolean isShopkeeper;
    boolean isEmployee;
    private List<ItemModel> items;

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
        this.context=context;
        this.items=items;
        this.isShopkeeper=isShopkeeper;
        this.isEmployee=isEmployee;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,parent,false);

        ItemAdapter.ViewHolder viewHolder = new ItemAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TextView itemName=holder.itemName;
        TextView itemPrice=holder.itemPrice;
        TextView itemQuantity=holder.itemQuantity;
        ImageView itemImage=holder.itemImage;
        LinearLayout quantity=holder.quantity;
        ImageButton decreaseQuantity=holder.decreaseQuantity;
        ImageButton increaseQuantity=holder.increaseQuantity;

        ItemModel item = items.get(position);

        itemName.setText(item.getName());
        itemPrice.setText(String.valueOf(item.getPrice()));
        itemQuantity.setText(String.valueOf(item.getQuantity()));
        itemImage.setImageResource(R.drawable.notebooks);
        // change using Glide
        itemQuantity.setText(String.valueOf(item.getQuantity()));

        if(isShopkeeper)
            quantity.setVisibility(View.VISIBLE);
        else
            quantity.setVisibility(View.GONE);

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity() - 1);
                itemQuantity.setText(String.valueOf(item.getQuantity()));
            }
        });
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setQuantity(item.getQuantity() + 1);
                itemQuantity.setText(String.valueOf(item.getQuantity()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}