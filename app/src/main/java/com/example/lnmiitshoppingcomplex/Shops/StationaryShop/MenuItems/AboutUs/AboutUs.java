package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import com.example.lnmiitshoppingcomplex.R;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes.EmployeeAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes.EmployeeModel;

public class AboutUs extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Toolbar toolbar;
    TextView shopkeeper_name, shopkeeper_phone;
    CircleImageView shopkeeper_img;

    RecyclerView employeeDetails;
    EmployeeAdapter employeeAdapter;
    List<EmployeeModel> employeeList;

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
        employeeDetails.setHasFixedSize(true);
        employeeDetails.setLayoutManager(new LinearLayoutManager(AboutUs.this));
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(this, employeeList);
        employeeDetails.setAdapter(employeeAdapter);

        loadEmployees();
    }

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

/*
package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes;

public class EmployeeModel {
    String name;
    String phonenum;
    String url;

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String phonenum, String url) {
        this.name = name;
        this.phonenum = phonenum;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

 */