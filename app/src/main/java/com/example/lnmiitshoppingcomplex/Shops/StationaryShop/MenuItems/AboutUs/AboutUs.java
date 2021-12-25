package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes.EmployeeAdapter;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes.EmployeeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUs extends AppCompatActivity {

    Toolbar toolbar;
    TextView shopkeeper_name, shopkeeper_phone;
    CircleImageView shopkeeper_img;

    RecyclerView employeeDetails;

    List<EmployeeModel> employees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);

        shopkeeper_name = findViewById(R.id.shopkeeper_name);
        shopkeeper_phone = findViewById(R.id.shopkeeper_phone);
        shopkeeper_img = findViewById(R.id.shopkeepr_image);
        employeeDetails = findViewById(R.id.employee_details_list);

        addEmployees();

    }

    private void addEmployees() {
        EmployeeModel e1 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e2 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e3 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e4 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e5 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e6 = new EmployeeModel("Name","+91 1234567891","url");
        EmployeeModel e7 = new EmployeeModel("Name","+91 1234567891","url");

        employees = Arrays.asList(e1,e2,e3,e4,e5,e6,e7);
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this, employees);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        employeeDetails.setLayoutManager(mLayoutManager);
        employeeDetails.setAdapter(employeeAdapter);
    }
}