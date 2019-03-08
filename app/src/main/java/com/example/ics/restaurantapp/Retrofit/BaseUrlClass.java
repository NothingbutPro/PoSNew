package com.example.ics.restaurantapp.Retrofit;

/**
 * Created by BUSYAPK on 10/3/2017.
 */

public class BaseUrlClass {

//    public static final String BASE_URL = "http://103.76.252.211/SushilaTraders/Service1.svc/";
    public static final String BASE_URL = "http://twors.in/POS/Webservices/";

    public static ApiUtils getInterface() {
        return RetrofitClient.getClient(BASE_URL).create(ApiUtils.class);
    }

}



