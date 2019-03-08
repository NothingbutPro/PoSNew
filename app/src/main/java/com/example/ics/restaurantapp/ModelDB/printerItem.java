package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by sagar on 18/7/18.
 */

public class printerItem {
    private String name,mAddress;

    public printerItem(String name, String mAddress) {
        this.name = name;
        this.mAddress = mAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}
