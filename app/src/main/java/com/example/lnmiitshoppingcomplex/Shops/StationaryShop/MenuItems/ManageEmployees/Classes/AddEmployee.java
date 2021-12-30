package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lnmiitshoppingcomplex.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.HashMap;

public class AddEmployee extends AppCompatActivity {

    ImageView addEmployeeImg;
    ExtendedFloatingActionButton saveEmployeeEFab;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Uri imgUri;
    private String dlUrl;
    private StorageTask storageTask;
    private StorageReference storageReference;

    private String name;
    private String phoneNo;
    private String aadharNo;
    private String email;

    private MaterialEditText nameMet;
    private MaterialEditText phoneNoMet;
    private MaterialEditText aadharNoMet;
    private MaterialEditText emailMet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        storageReference = FirebaseStorage.getInstance().getReference().child("employee");

        nameMet = findViewById(R.id.employeeNameMet);
        phoneNoMet = findViewById(R.id.employeePhoneMet);
        aadharNoMet = findViewById(R.id.employeeAadharMet);
        emailMet = findViewById(R.id.employeeEmailMet);

        addEmployeeImg = findViewById(R.id.addEmployeeImg);
        addEmployeeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameMet.getText().toString();
                phoneNo = phoneNoMet.getText().toString();
                aadharNo = aadharNoMet.getText().toString();
                email = emailMet.getText().toString();

                CropImage.activity().setCropShape(CropImageView.CropShape.RECTANGLE).setAspectRatio(1, 1)
                        .start(AddEmployee.this);
            }
        });

        saveEmployeeEFab = findViewById(R.id.saveEmployeeEFab);
        saveEmployeeEFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameMet.getText().toString().equals("")) {
                    Toast.makeText(AddEmployee.this, "Employee Name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (phoneNoMet.getText().toString().equals("")) {
                    Toast.makeText(AddEmployee.this, "Employee Phone No. cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (aadharNoMet.getText().toString().equals("")) {
                    Toast.makeText(AddEmployee.this, "Employee Aadhar No. cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (emailMet.getText().toString().equals("")) {
                    Toast.makeText(AddEmployee.this, "Employee Email cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    name = nameMet.getText().toString();
                    phoneNo = phoneNoMet.getText().toString();
                    aadharNo = aadharNoMet.getText().toString();
                    email = emailMet.getText().toString();

                    addEmployee();
                    finish();
                }
            }
        });
    }

    private void addEmployee() {
        HashMap<String, Object> employee = new HashMap<>();
        employee.put("name", name);
        employee.put("phoneNo", phoneNo);
        employee.put("aadharNo", aadharNo);
        employee.put("email", email);
        if (dlUrl == null || dlUrl.equals("")) {
            employee.put("imgUrl", "default");
        } else {
            employee.put("imgUrl", dlUrl);
        }

        db.collection("employee").document().set(employee)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddEmployee.this, "Employee Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddEmployee.this, "Error! (Employee Addition Failed)", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                imgUri = result.getUri();
                uploadImage();
            }

            nameMet.setText(name);
            phoneNoMet.setText(phoneNo);
            aadharNoMet.setText(aadharNo);
            emailMet.setText(email);

        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if (imgUri != null) {
            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpeg");
            storageTask = fileRef.putFile(imgUri);
            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dlUrl = task.getResult().toString();

                        progressDialog.dismiss();

                        if (dlUrl != null && !dlUrl.equals("")) {
                            Picasso.get().load(dlUrl).into(addEmployeeImg);
                        }
                    } else {
                        Toast.makeText(AddEmployee.this, "Error! (Uploading Image)", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}