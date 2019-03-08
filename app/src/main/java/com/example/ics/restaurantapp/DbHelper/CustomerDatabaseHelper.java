package com.example.ics.restaurantapp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ics.restaurantapp.ModelDB.CustomerItem;

import java.util.ArrayList;

/**
 * Created by sagar on 23/6/18.
 */

public class CustomerDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "CustomerDatabaseHelper";

    public static final String DATABASE_NAME = "CustomerDB";
    public static final String TABLE_NAME = "CustomerDetails";
    public static final String COL_1 = "mobile_number";
    public static final String COL_2 = "customer_name";
    public static final String COL_3 = "customer_address";

    private static final String TABLE_NAME_2 = "TableItemOrderInformation";
    private static final String TABLE_2_COL_0 = "receipt_no";
    private static final String TABLE_2_COL_1 = "table_no";
    private static final String TABLE_2_COL_2 = "item_id";
    private static final String TABLE_2_COL_3 = "item_name";
    private static final String TABLE_2_COL_4 = "item_rate";
    private static final String TABLE_2_COL_5 = "item_quantity";
    private static final String TABLE_2_COL_6 = "item_total_price";
    private static final String TABLE_2_COL_7 = "item_sgst_tax";
    private static final String TABLE_2_COL_8 = "item_cgst_tax";
    private static final String TABLE_2_COL_9 = "item_igst_tax";
    private static final String TABLE_2_COL_10 = "item_total_sgst_tax";
    private static final String TABLE_2_COL_11 = "item_total_cgst_tax";
    private static final String TABLE_2_COL_12 = "item_total_igst_tax";
    private static final String TABLE_2_COL_13 = "split_number";
    private static final String TABLE_2_COL_14 = "item_vat_tax";
    private static final String TABLE_2_COL_15 = "item_total_vat_tax";
    private static final String TABLE_2_COL_16 = "barcode";
    private static final String TABLE_2_COL_17 = "alias";
    private static final String TABLE_2_COL_18 = "item_group";
    private static final String TABLE_2_COL_19 = "category";
    private static final String TABLE_2_COL_20 = "serve_unit";
    private static final String TABLE_2_COL_21 = "department";

    private static final String TABLE_NAME_3 = "CancelledTableItemOrderInformation";
    private static final String TABLE_3_COL_0 = "receipt_no";
    private static final String TABLE_3_COL_1 = "table_no";
    private static final String TABLE_3_COL_2 = "item_id";
    private static final String TABLE_3_COL_3 = "item_name";
    private static final String TABLE_3_COL_4 = "item_rate";
    private static final String TABLE_3_COL_5 = "item_quantity";
    private static final String TABLE_3_COL_6 = "item_total_price";
    private static final String TABLE_3_COL_7 = "item_sgst_tax";
    private static final String TABLE_3_COL_8 = "item_cgst_tax";
    private static final String TABLE_3_COL_9 = "item_igst_tax";
    private static final String TABLE_3_COL_10 = "item_total_sgst_tax";
    private static final String TABLE_3_COL_11 = "item_total_cgst_tax";
    private static final String TABLE_3_COL_12 = "item_total_igst_tax";
    private static final String TABLE_3_COL_13 = "split_number";
    private static final String TABLE_3_COL_14 = "item_vat_tax";
    private static final String TABLE_3_COL_15 = "item_total_vat_tax";
    private static final String TABLE_3_COL_16 = "barcode";
    private static final String TABLE_3_COL_17 = "alias";
    private static final String TABLE_3_COL_18 = "item_group";
    private static final String TABLE_3_COL_19 = "category";
    private static final String TABLE_3_COL_20 = "serve_unit";
    private static final String TABLE_3_COL_21 = "department";
    //for discount
    public static final String TABLE_NAME_DISCOUNT = "Discount_by_waiter";
    public static final String Waiter_Id = "wait_id";
    public static final String Waiter_Name = "waiter_name";
    public static final String Waiter_Table = "wait_table";
    public static final String  Waiter_Discount = "wait_discount";

    public CustomerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(mobile_number TEXT PRIMARY KEY, customer_name TEXT, customer_address TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_DISCOUNT + "( Waiter_Id TEXT , Waiter_Table TEXT, Waiter_Name TEXT, Waiter_Discount TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(receipt_no TEXT,table_no TEXT,item_id TEXT,item_name TEXT," +
                " item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,item_sgst_tax REAL," +
                "item_cgst_tax REAL,item_igst_tax REAL,item_total_sgst_tax REAL,item_total_cgst_tax REAL," +
                "item_total_igst_tax REAL,split_number TEXT,item_vat_tax REAL, item_total_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_3 + "(receipt_no TEXT,table_no TEXT,item_id TEXT,item_name TEXT," +
                " item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,item_sgst_tax REAL," +
                "item_cgst_tax REAL,item_igst_tax REAL,item_total_sgst_tax REAL,item_total_cgst_tax REAL," +
                "item_total_igst_tax REAL,split_number TEXT,item_vat_tax REAL, item_total_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DISCOUNT);
    }

    //---------------------------------------------------------------------------

    public boolean insertNewCustomer(String mobile_number, String customer_name, String customer_address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, mobile_number);
        contentValues.put(COL_2, customer_name);
        contentValues.put(COL_3, customer_address);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertNewWAITER_DISCOUNT(String Wait_id, String Table_name, String Wait_name , String wait_discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Waiter_Id, Wait_id);
        contentValues.put(Waiter_Table, Table_name);
        contentValues.put(Waiter_Name, Wait_name);
        contentValues.put(Waiter_Discount, wait_discount);
        long result = db.insert(TABLE_NAME_DISCOUNT, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
    public ArrayList<CustomerItem> getValues() {
        ArrayList<CustomerItem> values = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursor.moveToFirst()) {
            do {
                values.add(new CustomerItem(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return values;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + "CustomerDetails", null);
        return cursor;
    }

    public Cursor getCustomer(String mobnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where mobile_number = '" + mobnum + "'", null);
        return res;
    }

    public Cursor getCustomerSearchResult(String newText) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where  customer_name like '" + "%" + newText + "%" + "'", null);
        return res;
    }

    //---------------------------------------------------------------------------

    public boolean insertAllTableItemOrderInformation(String receipt_no, String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float sgst_tax, float cgst_tax
            , float igst_tax, float total_sgst_tax, float total_cgst_tax, float total_igst_tax, String split_number
            , float item_vat_tax, float total_item_vat_tax, String barcode, String alias
            , String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_0, receipt_no);
        contentValues.put(TABLE_2_COL_1, table_no);
        contentValues.put(TABLE_2_COL_2, item_id);
        contentValues.put(TABLE_2_COL_3, item_name);
        contentValues.put(TABLE_2_COL_4, item_rate);
        contentValues.put(TABLE_2_COL_5, item_quantity);
        contentValues.put(TABLE_2_COL_6, item_total_price);
        contentValues.put(TABLE_2_COL_7, sgst_tax);
        contentValues.put(TABLE_2_COL_8, cgst_tax);
        contentValues.put(TABLE_2_COL_9, igst_tax);
        contentValues.put(TABLE_2_COL_10, total_sgst_tax);
        contentValues.put(TABLE_2_COL_11, total_cgst_tax);
        contentValues.put(TABLE_2_COL_12, total_igst_tax);
        contentValues.put(TABLE_2_COL_13, split_number);
        contentValues.put(TABLE_2_COL_14, item_vat_tax);
        contentValues.put(TABLE_2_COL_15, total_item_vat_tax);
        contentValues.put(TABLE_2_COL_16, barcode);
        contentValues.put(TABLE_2_COL_17, alias);
        contentValues.put(TABLE_2_COL_18, item_group);
        contentValues.put(TABLE_2_COL_19, category);
        contentValues.put(TABLE_2_COL_20, serve_unit);
        contentValues.put(TABLE_2_COL_21, department);
        long result = db.insert(TABLE_NAME_2, null, contentValues);
        Log.d(TAG, "insertTableItemOrderInformation: inserting new item " + item_name + " " + item_id);
        if (result == -1)
            return false;
        else
            return true;
    }

    //---------------------------------------------------------------------------

    public boolean insertAllCancelledTableItemOrderInformation(String receipt_no, String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float sgst_tax, float cgst_tax
            , float igst_tax, float total_sgst_tax, float total_cgst_tax, float total_igst_tax, String split_number
            , float item_vat_tax, float total_item_vat_tax, String barcode, String alias
            , String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_0, receipt_no);
        contentValues.put(TABLE_3_COL_1, table_no);
        contentValues.put(TABLE_3_COL_2, item_id);
        contentValues.put(TABLE_3_COL_3, item_name);
        contentValues.put(TABLE_3_COL_4, item_rate);
        contentValues.put(TABLE_3_COL_5, item_quantity);
        contentValues.put(TABLE_3_COL_6, item_total_price);
        contentValues.put(TABLE_3_COL_7, sgst_tax);
        contentValues.put(TABLE_3_COL_8, cgst_tax);
        contentValues.put(TABLE_3_COL_9, igst_tax);
        contentValues.put(TABLE_3_COL_10, total_sgst_tax);
        contentValues.put(TABLE_3_COL_11, total_cgst_tax);
        contentValues.put(TABLE_3_COL_12, total_igst_tax);
        contentValues.put(TABLE_3_COL_13, split_number);
        contentValues.put(TABLE_3_COL_14, item_vat_tax);
        contentValues.put(TABLE_3_COL_15, total_item_vat_tax);
        contentValues.put(TABLE_3_COL_16, barcode);
        contentValues.put(TABLE_3_COL_17, alias);
        contentValues.put(TABLE_3_COL_18, item_group);
        contentValues.put(TABLE_3_COL_19, category);
        contentValues.put(TABLE_3_COL_20, serve_unit);
        contentValues.put(TABLE_3_COL_21, department);
        long result = db.insert(TABLE_NAME_3, null, contentValues);
        Log.d(TAG, "insertTableItemOrderInformation: inserting new item " + item_name + " " + item_id);
        if (result == -1)
            return false;
        else
            return true;
    }


}
