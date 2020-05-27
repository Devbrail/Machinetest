package com.test.whiterabbit.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.whiterabbit.Models.Response.Employee;
import com.test.whiterabbit.Utlities.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;


    public DatabaseHandler(Context context) {
        super(context, Constants.Db.DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_employeeS_TABLE = "CREATE TABLE IF NOT EXISTS " + Constants.Db.TABLE_employeeS + "("
                + Constants.Db.KEY_ID + " INTEGER PRIMARY KEY," + Constants.Db.KEY_NAME + " TEXT,"
                + Constants.Db.KEY_PROFILE_URL + " TEXT," + Constants.Db.COMPANY_NAME + " TEXT," +
                Constants.Db.EMAIL + " TEXT,"+
                Constants.Db.ADDR + " TEXT,"+
                Constants.Db.PHONE + " TEXT,"+
                Constants.Db.UNAME + " TEXT,"+
                Constants.Db.WEB + " TEXT"
                +")";
        db.execSQL(CREATE_employeeS_TABLE);
    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Db.TABLE_employeeS);

        // Create tables again  
        onCreate(db);
    }

    public void dropTable() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.execSQL("DROP TABLE IF EXISTS " + Constants.Db.TABLE_employeeS);

    }

    // code to add the new employee  
    public void addemployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.Db.KEY_ID, employee.getId());
        values.put(Constants.Db.KEY_NAME, employee.getName());
        values.put(Constants.Db.KEY_PROFILE_URL, employee.getProfileImage());
        if (employee.getCompany() != null)
            values.put(Constants.Db.COMPANY_NAME, employee.getCompany().getName());
        else
            values.put(Constants.Db.COMPANY_NAME, "N/A");

        values.put(Constants.Db.EMAIL,employee.getEmail());

        String address= getConsolidatedAddress(employee);
        values.put(Constants.Db.ADDR,address);

        values.put(Constants.Db.PHONE,employee.getPhone());
        values.put(Constants.Db.UNAME,employee.getUsername());
        values.put(Constants.Db.WEB,employee.getWebsite());

        // Inserting Row
        db.insert(Constants.Db.TABLE_employeeS, null, values);
        //2nd argument is String containing nullColumnHack  
        db.close(); // Closing database connection  
    }

    private String getConsolidatedAddress(Employee employee) {
        String addres="N/A";

        if(employee.getAddress()!=null){
            Employee.Address address= employee.getAddress();
            addres = address.getStreet()+address.getSuite()+address.getCity();

        }

        return addres;
    }


    // code to get all employees in a list view  
    public List<Employee> getAllemployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + Constants.Db.TABLE_employeeS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                Employee.Company company = new Employee.Company();


                employee.setId(Integer.parseInt(cursor.getString(0)));
                employee.setName(cursor.getString(1));
                employee.setProfileImage(cursor.getString(2));

                company.setName(cursor.getString(3));
                employee.setEmail(cursor.getString(4));

                employee.setAddress(cursor.getString(5));
                employee.setPhone(cursor.getString(6));
                employee.setUsername(cursor.getString(7));
                employee.setWebsite(cursor.getString(8));

                employee.setCompany(company);
                // Adding employee to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        // return employee list  
        return employeeList;
    }

   public  Employee getEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.Db.TABLE_employeeS, new String[] { Constants.Db.KEY_ID,
                        Constants.Db.KEY_NAME,Constants.Db.KEY_PROFILE_URL}, Constants.Db.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee();
        Employee.Company company = new Employee.Company();


        employee.setId(Integer.parseInt(cursor.getString(0)));
        employee.setName(cursor.getString(1));
        employee.setProfileImage(cursor.getString(2));

//        company.setName(cursor.getString(3));
//        employee.setEmail(cursor.getString(4));
//
//        employee.setAddress(cursor.getString(5));
//        employee.setPhone(cursor.getString(6));
//        employee.setUsername(cursor.getString(7));
//        employee.setWebsite(cursor.getString(8));

        employee.setCompany(company);
        return employee;
    }

    // Getting employees Count  
    public int getemployeesCount() {
        String countQuery = "SELECT  * FROM " + Constants.Db.TABLE_employeeS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count  
        return cursor.getCount();
    }

}