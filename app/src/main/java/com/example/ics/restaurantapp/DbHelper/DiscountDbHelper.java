package com.example.ics.restaurantapp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiscountDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dISCOUNT_WAIT.db";
    String SQL_CREATE_ENTRIES = "CREATE TABLE " + "wait_id" + "("
            + "waiter_name" + " INTEGER PRIMARY KEY," + "wait_table" + " TEXT,"
            + "wait_discount" + " TEXT" + ")";
//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + DiscountReaderContract.FeedEntry.TABLE_NAME + " (" +
//                    DiscountReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
//                    DiscountReaderContract.FeedEntry.Waiter_Id + " INTEGER," +
//                    DiscountReaderContract.FeedEntry.Waiter_Name + " TEXT,"+DiscountReaderContract.FeedEntry.Waiter_Table+"TEXT,"+DiscountReaderContract.FeedEntry.Waiter_Discount+
//        " VARCHAR )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DiscountReaderContract.FeedEntry.TABLE_NAME;

    public DiscountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DiscountDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DiscountDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.e("things" , "for discount created");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void Insert_Wait_Discount( String wait_id , String wait_name , String wait_table ,String wait_dis ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+DiscountReaderContract.FeedEntry.TABLE_NAME+" VALUES (NULL, ?, ?, ?,?)";

        ContentValues values = new ContentValues();
        values.clear();
        //  statement.bindDouble(1, Id);
        values.put(DiscountReaderContract.FeedEntry.Waiter_Id,wait_id);
        values.put(DiscountReaderContract.FeedEntry.Waiter_Name, wait_name);
        values.put(DiscountReaderContract.FeedEntry.Waiter_Table, wait_table);
        values.put(DiscountReaderContract.FeedEntry.Waiter_Discount, wait_dis);

        long newRowId;
        newRowId = database.insert(
                ""+DiscountReaderContract.FeedEntry.TABLE_NAME,
                null,
                values);
        database.close();
        //  database.close();
//        System.out.println("statement execute insert is "+statement.executeInsert());
//        statement.executeInsert();

    }
}