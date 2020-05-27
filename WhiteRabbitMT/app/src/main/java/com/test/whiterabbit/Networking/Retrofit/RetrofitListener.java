package com.test.whiterabbit.Networking.Retrofit;

import com.test.whiterabbit.Models.Response.Employee;

import java.util.List;

public interface RetrofitListener {
    void onResponseSuccess(List<Employee> responseBodyString, int apiFlag);


    void onResponseError(String t, int apiFlag);
}
