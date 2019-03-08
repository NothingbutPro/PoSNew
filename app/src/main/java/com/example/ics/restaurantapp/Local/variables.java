package com.example.ics.restaurantapp.Local;

import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.ModelDB.foodDetailsItem;
import com.example.ics.restaurantapp.ModelDB.tableItem;
import com.example.ics.restaurantapp.ModelDB.waiterItem;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by ICS on 28/05/2018.
 */

public class variables {
    public static List<foodDetailsItem> list = new ArrayList<>();



    //-------------------------------------------------------------------------------
    /**
     * all selected data on runtime(temporary)
     */
    public static tableItem selecetd_table_data;
    public static waiterItem selected_waiter_data;
    public static String tableNumber;
    public static int cancelledTotalAmount = 0;
    public static float cancelledTotalTax = 0;

    //--------------------------------------------------------------------------------


    public static int total_price=0;
    public static int total_price_kot =0;

    public static  int kot_count =0;

    public static  String group_name ="";

    public static int order_no= 0;
    public static int receipt_no=0;
    public static int initial_receipt_no= 0;

    public static int modifyBtn = 0;

    public static String table_no = "";
    public static String table_name = "";
    public static  String order_for ="Dine In";
    public static  String selected_floor ="";
    public static  String guest_name ="";
    public static  String status ="running";

    public static String table_status = "free";

    public static String generateReceiptNo(int number){
        int n = number;
        int count =0;
        String temp="";
        while(n>0){
            count++;
            n/=10;
        }

        for (int i=0;i<5-count;i++){
            temp+="0";
        }

        return temp+String.valueOf(number);
    }

    //-----------------------------------------------------------------



}
