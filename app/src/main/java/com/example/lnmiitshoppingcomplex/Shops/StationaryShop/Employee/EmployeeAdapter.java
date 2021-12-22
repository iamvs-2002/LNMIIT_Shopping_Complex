package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lnmiitshoppingcomplex.R;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    private Context context;
    private List<EmployeeModel> employeeList;

    public EmployeeAdapter(Context context, List<EmployeeModel> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_card, parent, false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmployeeModel employeeModel = employeeList.get(position);
        holder.nameEmployee.setText(employeeModel.getName());
        holder.phoneNoEmployee.setText(employeeModel.getPhoneNumber());
        holder.aadharNoEmployee.setText(employeeModel.getAadharNumber());
        holder.emailEmployee.setText(employeeModel.getEmail());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameEmployee;
        public TextView phoneNoEmployee;
        public TextView aadharNoEmployee;
        public TextView emailEmployee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameEmployee = itemView.findViewById(R.id.nameEmployee);
            phoneNoEmployee = itemView.findViewById(R.id.phoneNoEmployee);
            aadharNoEmployee = itemView.findViewById(R.id.aadharNoEmployee);
            emailEmployee = itemView.findViewById(R.id.emailEmployee);
        }
    }
}
