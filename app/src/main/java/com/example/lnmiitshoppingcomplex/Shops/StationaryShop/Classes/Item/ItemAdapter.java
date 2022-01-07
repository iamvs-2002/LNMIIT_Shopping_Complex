package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    boolean isShopkeeper;
    boolean isEmployee;
    private List<ItemModel> itemList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        EditText itemQuantity;
        ImageView itemImage;
        ImageButton decreaseQuantity, increaseQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemName = (TextView) itemView.findViewById(R.id.citem_name);
            this.itemPrice = (TextView) itemView.findViewById(R.id.citem_price);
            this.itemQuantity = (EditText) itemView.findViewById(R.id.citem_quantity);
            this.itemImage = (ImageView) itemView.findViewById(R.id.citem_img);
            this.decreaseQuantity = (ImageButton) itemView.findViewById(R.id.citem_quantity_decrease);
            this.increaseQuantity = (ImageButton) itemView.findViewById(R.id.citem_quantity_increase);
        }
    }

    public ItemAdapter(Context context, List<ItemModel> itemList, boolean isShopkeeper, boolean isEmployee) {
        this.context = context;
        this.itemList = itemList;
        this.isShopkeeper = isShopkeeper;
        this.isEmployee = isEmployee;
    }

    public void setItemList(List<ItemModel> itemList) {
        this.itemList = itemList;
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

        ItemModel item = itemList.get(position);

        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));
        holder.itemQuantity.setText(String.valueOf(item.getQuantity()));
        if (item.imgUrl.equals("default")) {
            holder.itemImage.setImageResource(R.drawable.notebooks);
        } else {
            Picasso.get().load(item.getImgUrl()).into(holder.itemImage);
        }

        if(!isShopkeeper && !isEmployee){
            holder.itemQuantity.setClickable(false);
            holder.itemQuantity.setFocusable(false);
            holder.itemQuantity.setVisibility(View.VISIBLE);
            holder.decreaseQuantity.setVisibility(View.GONE);
            holder.increaseQuantity.setVisibility(View.GONE);
        }

        String itemId = itemList.get(position).getId();
        String categoryId = itemList.get(position).getCategoryId();

        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getQuantity() > 0 ) {
                    db.collection("category").document(categoryId)
                        .collection("item").document(itemId)
                        .update("quantity", item.getQuantity()-1)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Error! (Item Quantity Updation)", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });

        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("category").document(categoryId)
                    .collection("item").document(itemId)
                    .update("quantity", item.getQuantity()+1)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Error! (Item Quantity Updation)", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

        if (isShopkeeper || isEmployee) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String itemName = itemList.get(position).getName();
                    builder.setTitle("Delete " + itemName + " Item");
                    builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("category").document(categoryId)
                                .collection("item").document(itemId).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), "Error! (Item Deletion)", Toast.LENGTH_SHORT).show();
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
        return itemList.size();
    }
}