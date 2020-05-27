package com.test.whiterabbit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whiterabbitmt.R;
import com.test.whiterabbit.Adapter.EmployeeAdapter;
import com.test.whiterabbit.DataBase.DatabaseHandler;
import com.test.whiterabbit.Models.Response.Employee;
import com.test.whiterabbit.Networking.Retrofit.RetrofitConnect;
import com.test.whiterabbit.Networking.Retrofit.RetrofitListener;
import com.test.whiterabbit.Utlities.Constants;
import com.test.whiterabbit.Utlities.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends AppCompatActivity implements RetrofitListener {
    private static final String TAG = "MainActivity";
    DatabaseHandler db;
    ListView employeeListView;
    List<Employee> employeeList = new ArrayList<>();
    EmployeeAdapter employeeAdapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emplyee_list);

        db = new DatabaseHandler(this);
        SharedPref.init(getApplicationContext());
        initUI();

        RetrofitConnect retrofitConnect = RetrofitConnect.getInstance(this);


        if (!isLoadedDB()) {

            retrofitConnect.getEmployees(this, Constants.ApiFlags.getEmployees);
        } else {
            if (db.getemployeesCount() != 0)


                employeeList = db.getAllemployees();
            bindUI(employeeList);


        }

    }

    private void initUI() {
        employeeListView = findViewById(R.id.employee_listy);
        search = findViewById(R.id.search);

        employeeAdapter = new EmployeeAdapter(this, R.layout.eemployee_item, employeeList);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.i(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: ");
                if (s.length() != 0)
                    filterUI(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: ");
            }
        });
    }

    private void filterUI(String s) {

        List<Employee> employeeListTemp = new ArrayList<>();

        if (s.length() == 0) {

            bindUI(employeeList);
            return;
        }
        if (employeeList.size() != 0) {


            for (Employee employee : employeeList
            ) {
                try {
                    if (employee.getName().toLowerCase().contains(s.toLowerCase()) ||
                            employee.getEmail().toLowerCase().contains(s.toLowerCase())) {
                        employeeListTemp.add(employee);
                    }
                } catch (Exception e) {

                }

            }

             bindUI(employeeListTemp);

        }

    }

    private boolean isLoadedDB() {
        return SharedPref.read(Constants.SharedPref.HAS_OFFLINE, false);
    }

    @Override
    public void onResponseSuccess(List<Employee> employees, int apiFlag) {
        List<Employee> employeeList = new ArrayList<>();

        if (apiFlag == Constants.ApiFlags.getEmployees) {

            employeeList = employees;


            for (Employee employee : employees
            ) {

                db.addemployee(employee);

            }

            bindUI(employeeList);
            markAsSaved();

        }

    }

    private void bindUI(List<Employee> employeeListt) {

        employeeAdapter = new EmployeeAdapter(this, R.layout.eemployee_item, employeeListt);
        employeeListView.setAdapter(employeeAdapter);
        employeeAdapter.notifyDataSetChanged();
        
        
        
        employeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: ");

                Employee employee= (Employee) employeeListView.getAdapter().getItem(position);

                Bundle b=new Bundle();
                b.putInt("DATA", employee.getId());
                Intent i=new Intent(getApplicationContext(), EmployeeDetails.class);
                i.putExtras(b);
                startActivity(i);

            }
        });
        
    }

    private void markAsSaved() {
        SharedPref.write(Constants.SharedPref.HAS_OFFLINE, true);
    }

    @Override
    public void onResponseError(String t, int apiFlag) {
        Log.e(TAG, "onResponseError: " + t);
    }
}
