package com.test.whiterabbit.Networking.Retrofit;

import com.test.whiterabbit.Models.Response.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("5d565297300000680030a986")
    Call<List<Employee>> getEmployees();


}
