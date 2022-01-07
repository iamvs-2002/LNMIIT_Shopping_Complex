package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes;

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

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        EmployeeModel employee = employeeList.get(position);
        holder.nameEmployee.setText(employee.getName());
        holder.phoneNoEmployee.setText(String.valueOf(employee.getPhoneNo()));
        holder.aadharNoEmployee.setText(String.valueOf(employee.getAadharNo()));
        holder.emailEmployee.setText(employee.getEmail());
        if (employee.getImgUrl().equals("default")) {
            holder.imgEmployee.setImageResource(R.drawable.ic_baseline_perm_identity_24);
        } else {
            Picasso.get().load(employee.getImgUrl()).into(holder.imgEmployee);
        }

        // delete an employee
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String employeeId = employeeList.get(position).getId();
                String employeeName = employeeList.get(position).getName();
                builder.setTitle("Delete " + employeeName + " Employee");
                builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("employee").document(employeeId).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(v.getContext(), "Employee Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Error! (Employee Deletion)", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameEmployee;
        public TextView phoneNoEmployee;
        public TextView aadharNoEmployee;
        public TextView emailEmployee;
        public ImageView imgEmployee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameEmployee = itemView.findViewById(R.id.nameEmployee);
            phoneNoEmployee = itemView.findViewById(R.id.phoneNoEmployee);
            aadharNoEmployee = itemView.findViewById(R.id.aadharNoEmployee);
            emailEmployee = itemView.findViewById(R.id.emailEmployee);
            imgEmployee = itemView.findViewById(R.id.imgEmployee);
        }
    }
}
