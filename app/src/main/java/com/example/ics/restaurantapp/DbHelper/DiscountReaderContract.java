package com.example.ics.restaurantapp.DbHelper;

import android.provider.BaseColumns;

public class DiscountReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DiscountReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Discount_by_waiter";
        public static final String Waiter_Id = "wait_id";
        public static final String Waiter_Name = "waiter_name";
        public static final String Waiter_Table = "wait_table";
        public static final String  Waiter_Discount = "wait_discount";



    }
}