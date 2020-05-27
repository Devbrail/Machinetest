package com.test.whiterabbit.Networking.Retrofit;

import android.content.Context;

import com.test.whiterabbit.Models.Response.Employee;
import com.test.whiterabbit.Utlities.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnect {

    private static RetrofitConnect retrofitConnect = null;
    private Context mContext;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.ApiPaths.baseURL)
            .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
            .build();
    private ApiServices api = retrofit.create(ApiServices.class);

    private RetrofitConnect(Context context) {
        this.mContext = context;
    }

    public static RetrofitConnect getInstance(Context context) {
        if (retrofitConnect == null) {
            retrofitConnect = new RetrofitConnect(context);
        }
        return retrofitConnect;
    }

    public void getEmployees(final RetrofitListener retrofitListener, final int apiFlag) {

        Call<List<Employee>> call = api.getEmployees();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                retrofitListener.onResponseSuccess(response.body(), apiFlag);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

                retrofitListener.onResponseError(t.getMessage(), apiFlag);
            }
        });
    }


}
