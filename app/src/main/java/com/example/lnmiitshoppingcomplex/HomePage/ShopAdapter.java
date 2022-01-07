package com.example.lnmiitshoppingcomplex.HomePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnmiitshoppingcomplex.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    Context context;
    private List<ShopModel> shops;

    // Click listener object created for recycler view item(shop) click
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

    public ShopAdapter(Context context, List<ShopModel> shops) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TextView shopName=holder.shopName;
        TextView shopkeeperName=holder.shopkeeperName;
        TextView timing=holder.timing;
        TextView status=holder.status;
        CardView shopCard=holder.shopCard;

        ShopModel shop = shops.get(position);

        shopName.setText(shop.getName());
        shopkeeperName.setText(shop.getShopkeeperName());
        timing.setText(String.format("%s - %s", shop.startTime, shop.endTime));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("setting").document("settings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot!=null){
                            String temp = (Boolean) documentSnapshot.get("shopstatus") ? "Open" : "Closed";
                            status.setText(temp);
                        }
                    }
                });

        shopCard.setCardBackgroundColor(shop.getColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }
}