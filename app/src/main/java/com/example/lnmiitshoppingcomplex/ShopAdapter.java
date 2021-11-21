package com.example.lnmiitshoppingcomplex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    Context context;
    private List<Shop> shops;

    /*
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopName;
        TextView shopkeeperName;
        TextView timing;
        TextView status;
        CardView shopCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shopName=(TextView) itemView.findViewById(R.id.SHOP_NAME);
            this.shopkeeperName=(TextView) itemView.findViewById(R.id.SHOPKEEPER_NAME);
            this.timing=(TextView) itemView.findViewById(R.id.TIMINGS);
            this.status=(TextView) itemView.findViewById(R.id.STATUS);
            this.shopCard=(CardView) itemView.findViewById(R.id.SHOP_CARD);
        }
    }

    public ShopAdapter(Context context,List<Shop> shops) {
        this.context=context;
        this.shops=shops;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_view,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView shopName=holder.shopName;
        TextView shopkeeperName=holder.shopkeeperName;
        TextView timing=holder.timing;
        TextView status=holder.status;
        CardView shopCard=holder.shopCard;

        Shop shop = shops.get(position);

        shopName.setText(shop.getName());
        shopkeeperName.setText(shop.getShopkeeperName());
        timing.setText(String.format("%s - %s", shop.startTime, shop.endTime));

        String temp = shop.status ? "Open" : "Closed";
        status.setText(temp);

        shopCard.setCardBackgroundColor(shop.color);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }
}
