package com.example.ics.restaurantapp.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BUSYAPK on 9/28/2017.
 */

public class RetrofitClient {


    public static Retrofit getClient(String baseUrl){
        Retrofit retrofit = null;
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
