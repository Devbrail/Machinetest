package com.test.whiterabbit.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.whiterabbitmt.R;
import com.test.whiterabbit.DataBase.DatabaseHandler;
import com.test.whiterabbit.Models.Response.Employee;

public class EmployeeDetails extends AppCompatActivity {

    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        TextView uname, email, address, phone, website, company, name;
        ImageView profile;


        db = new DatabaseHandler(this);

        uname=findViewById(R.id.uname);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        website=findViewById(R.id.website);
        name=findViewById(R.id.name);
        profile=findViewById(R.id.profile);

        Bundle b=this.getIntent().getExtras();

        Integer id=b.getInt("DATA");
        Employee employee=db.getEmployee(id);

        name.setText(employee.getName());
        email.setText(employee.getEmail());
        address.setText(employee.getAddres());
        uname.setText(employee.getUsername());

        Glide.with(this).
                load(employee.getProfileImage()).
                placeholder(R.drawable.placeholder).
                into(profile);




    }
}
