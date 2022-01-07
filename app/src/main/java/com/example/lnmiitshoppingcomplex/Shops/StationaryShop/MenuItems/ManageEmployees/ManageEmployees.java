package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category.CategoryModel;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.AddItem.AddItem;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.HomePage.StationaryShop;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes.AddEmployee;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes.EmployeeAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes.EmployeeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManageEmployees extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Toolbar toolbar;
    RecyclerView manageEmployeesRv;
    EmployeeAdapter employeeAdapter;
    List<EmployeeModel> employeeList;

    ExtendedFloatingActionButton addEmployeeEFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employees);

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("Manage Employees");
        setSupportActionBar(toolbar);

        addEmployeeEFab = findViewById(R.id.addEmployeeEfab);
        addEmployeeEFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageEmployees.this, AddEmployee.class));
            }
        });

        manageEmployeesRv = findViewById(R.id.manageEmployeesRv);
        manageEmployeesRv.setHasFixedSize(true);
        manageEmployeesRv.setLayoutManager(new LinearLayoutManager(ManageEmployees.this));
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(ManageEmployees.this, employeeList);
        manageEmployeesRv.setAdapter(employeeAdapter);

        loadEmployees();
    }

    // fetch employees from database
    private void loadEmployees() {
        db.collection("employee")
            .orderBy("name")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        employeeList.clear();
                        for (DocumentSnapshot documentSnapshot: value) {
                            if (documentSnapshot != null) {
//                                EmployeeModel employee = documentSnapshot.toObject(EmployeeModel.class);
                                EmployeeModel employee = new EmployeeModel();
                                employee.setId(documentSnapshot.getId());
                                employee.setName(documentSnapshot.get("name").toString());
                                employee.setPhoneNo(Long.valueOf(documentSnapshot.get("phoneNo").toString()));
                                employee.setAadharNo(Long.valueOf(documentSnapshot.get("aadharNo").toString()));
                                employee.setEmail(documentSnapshot.get("email").toString());
                                employee.setImgUrl(documentSnapshot.get("imgUrl").toString());
                                employeeList.add(employee);
                            }
                        }
                        employeeAdapter.notifyDataSetChanged();
                    }
                }
            });
    }
}