package com.example.ics.restaurantapp.Retrofit;

import android.widget.TextView;

import com.example.ics.restaurantapp.model.MenuItem_Response;
import com.example.ics.restaurantapp.model.OrderResponse;
import com.example.ics.restaurantapp.model.Order_Item;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Procure on 29-12-2017.
 */

public interface ApiUtils {
    @FormUrlEncoded
    @POST("add_order")
    Call<OrderResponse> getOrderResponseCall(@Field("Customer_name") String guest_name,
                                             @Field("Dinenig_Area") String serial_no,
                                             @Field("Table_No") String reciept_no,
                                             @Field("Waiter_Name") String order_for,
                                             @Field("Date_Time") String table_no,
                                             @Field("Beofer_tax_amount") String floor_no,
                                             @Field("Tax_amount") String order_by,
                                             @Field("SGST") String amountS,
                                             @Field("CGST") String waiter_name,
                                             @Field("Total_Amount") String sgst,
                                             @Field("Discount_amount") String cgst,
                                             @Field("Round_off_amount") String total_tax,
                                             @Field("Payment_method") String bill_dicount);

    @GET("get_orderList?order_id=")
    Call<MenuItem_Response> getMenuItemResponseCall(@Query("order_id") String s);
}
