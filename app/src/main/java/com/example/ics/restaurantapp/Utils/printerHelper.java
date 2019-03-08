package com.example.ics.restaurantapp.Utils;

import android.content.Context;
import android.database.Cursor;
import android.widget.Adapter;

import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.ModelDB.printerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagar on 18/7/18.
 */

public class printerHelper {

    Context context;
    OrderDatabseHelper orderDatabseHelper;

    public printerHelper(Context context) {
        this.context = context;
        orderDatabseHelper = new OrderDatabseHelper(context);
    }

    public void insertPrinter(String name, String mAddress ){
        orderDatabseHelper.insertPrinterInformation(name,mAddress);
    }

    public void updatePrinterInformation(String name, String mAddress){
        orderDatabseHelper.updatePrinterInformation(name, mAddress);
    }

    public List<printerItem> getAllPrinter(){
        Cursor localCursor = orderDatabseHelper.getAllPrinterInformation();
        List<printerItem> list = new ArrayList<>();
        while (localCursor.moveToNext()){
            list.add(new printerItem(localCursor.getString(0),localCursor.getString(1)));
        }
        return list;
    }

    public String getMaddress(String name){
        Cursor localCursor = orderDatabseHelper.getmAddress(name);
        String mAddress =  "";
        while(localCursor.moveToNext()){
            mAddress = localCursor.getString(1);
        }
        return mAddress;
    }

    public void removeMAddress(String name){
        orderDatabseHelper.removePrinterInformation(name);
    }

    public void removeAllPrinter(){
        orderDatabseHelper.trancatePrinterInformation();
    }

}
