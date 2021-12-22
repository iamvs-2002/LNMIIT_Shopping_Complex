package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Employee.EmployeeAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Employee.EmployeeModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageEmployees extends AppCompatActivity {

    RecyclerView manageEmployeesRv;
    EmployeeAdapter employeeAdapter;
    List<EmployeeModel> employeeList;

    ExtendedFloatingActionButton addEmployeeEfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employees);

        addEmployeeEfab = findViewById(R.id.addEmployeeEfab);
        addEmployeeEfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageEmployees.this, "Employee Added to Database", Toast.LENGTH_SHORT).show();
            }
        });

        manageEmployeesRv = findViewById(R.id.manageEmployeesRv);
        manageEmployeesRv.setHasFixedSize(true);
        manageEmployeesRv.setLayoutManager(new LinearLayoutManager(ManageEmployees.this));
        employeeList = new ArrayList<>();
        employeeList.add(new EmployeeModel("Ramesh", "123456789", "32482343", "abc@gmail.com"));
        employeeList.add(new EmployeeModel("Suresh", "987654321", "23433248", "def@gmail.com"));
        employeeAdapter = new EmployeeAdapter(ManageEmployees.this, employeeList);
        manageEmployeesRv.setAdapter(employeeAdapter);

    }
}