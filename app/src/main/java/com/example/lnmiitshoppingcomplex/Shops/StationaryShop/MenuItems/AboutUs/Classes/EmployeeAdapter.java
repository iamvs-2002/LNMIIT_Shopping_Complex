package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes;

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
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes.EmployeeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<EmployeeModel> employeeList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        ImageView image;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.employee_name);
            phone = view.findViewById(R.id.employee_phone);
            image = view.findViewById(R.id.employee_image);
        }
    }
    public EmployeeAdapter(Context context, List<EmployeeModel> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }
    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_details_item, parent, false);
        return new EmployeeAdapter.ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EmployeeModel employee = employeeList.get(position);
        holder.name.setText(employee.getName());
        holder.phone.setText(String.valueOf(employee.getPhoneNo()));
        if (employee.getImgUrl().equals("default")) {
            holder.image.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        } else {
            Picasso.get().load(employee.getImgUrl()).into(holder.image);
        }
    }
    @Override
    public int getItemCount() {
        return employeeList.size();
    }
}