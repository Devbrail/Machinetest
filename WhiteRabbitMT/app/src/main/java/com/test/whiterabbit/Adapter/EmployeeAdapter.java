package com.test.whiterabbit.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.whiterabbitmt.R;
import com.test.whiterabbit.Models.Response.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    Activity context;
    List<Employee> employeeList = new ArrayList();
    int resource;

    public EmployeeAdapter(@NonNull Activity context, int resource, List<Employee> employeeList) {
        super(context, resource, employeeList);
        this.context = context;
        this.employeeList = employeeList;
        this.resource = resource;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(resource, null, false);

        TextView name = rowView.findViewById(R.id.name);
        ImageView profile = rowView.findViewById(R.id.profile);
        TextView company = rowView.findViewById(R.id.company);

        name.setText(employeeList.get(position).getName());
        company.setText(employeeList.get(position).getCompany().getName());

        Glide.with(context).
                load(employeeList.get(position).getProfileImage()).
                placeholder(R.drawable.placeholder).
                into(profile);


        return rowView;

    }

}
