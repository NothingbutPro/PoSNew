package com.example.ics.restaurantapp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ics.restaurantapp.model.ForWaiterDetails;
import com.example.ics.restaurantapp.model.Kot_Items_Bill_Node;

import java.util.SimpleTimeZone;

import static com.example.ics.restaurantapp.activities.NewActivity.kot_items_bill_nodes;

/**
 * Created by Shubham on 5/28/2018.
 */

public class OrderDatabseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "POSDb_ORDER_DETAILS";
    private static final String TABLE_NAME_1 = "cardOrderList";
    private static final String TABLE_1_COL_1 = "item_id";
    private static final String TABLE_1_COL_2 = "item_name";
    private static final String TABLE_1_COL_3 = "item_rate";
    private static final String TABLE_1_COL_4 = "item_quantity";
    private static final String TABLE_1_COL_5 = "item_total_price";
    private static final String TABLE_1_COL_6 = "selected_floor";
    private static final String TABLE_1_COL_7 = "table_id";
    private static final String TABLE_1_COL_8 = "table_name";
    private static final String TABLE_1_COL_9 = "sgst_tax";
    private static final String TABLE_1_COL_10 = "cgst_tax";
    private static final String TABLE_1_COL_11 = "igst_tax";
    private static final String TABLE_1_COL_12 = "total_sgst_tax";
    private static final String TABLE_1_COL_13 = "total_cgst_tax";
    private static final String TABLE_1_COL_14 = "total_igst_tax";
    private static final String TABLE_1_COL_15 = "item_vat_tax";
    private static final String TABLE_1_COL_16 = "total_item_vat_tax";
    private static final String TABLE_1_COL_17 = "barcode";
    private static final String TABLE_1_COL_18 = "alias";
    private static final String TABLE_1_COL_19 = "item_group";
    private static final String TABLE_1_COL_20 = "category";
    private static final String TABLE_1_COL_21 = "serve_unit";
    private static final String TABLE_1_COL_22 = "department";

    private static final String TABLE_NAME_2 = "kitchenOrderList";
    private static final String TABLE_2_COL_1 = "item_sr";
    private static final String TABLE_2_COL_2 = "item_id";
    private static final String TABLE_2_COL_3 = "item_name";
    private static final String TABLE_2_COL_4 = "item_rate";
    private static final String TABLE_2_COL_5 = "item_quantity";
    private static final String TABLE_2_COL_6 = "item_total_price";
    private static final String TABLE_2_COL_7 = "selected_floor";
    private static final String TABLE_2_COL_8 = "table_id";
    private static final String TABLE_2_COL_9 = "table_name";

    private static final String TABLE_NAME_3 = "OrderListTable";
    private static final String TABLE_3_COL_1 = "order_no";
    private static final String TABLE_3_COL_2 = "receipt_no";
    private static final String TABLE_3_COL_3 = "order_for";
    private static final String TABLE_3_COL_4 = "dining_area";
    private static final String TABLE_3_COL_5 = "date_time";
    private static final String TABLE_3_COL_6 = "guest_name";
    private static final String TABLE_3_COL_7 = "amount";
    private static final String TABLE_3_COL_8 = "due";
    private static final String TABLE_3_COL_9 = "status";
    private static final String TABLE_3_COL_10 = "order_by";
    private static final String TABLE_3_COL_11 = "table_no";
    private static final String TABLE_3_COL_12 = "total_tax";
    private static final String TABLE_3_COL_13 = "split_number";
    private static final String TABLE_3_COL_14 = "discount";
    private static final String TABLE_3_COL_15 = "waiter_id";

    private static final String TABLE_NAME_4 = "CurrentRunningOrderTable";
    private static final String TABLE_4_COL_1 = "order_no";
    private static final String TABLE_4_COL_2 = "receipt_no";
    private static final String TABLE_4_COL_3 = "floor_no";
    private static final String TABLE_4_COL_4 = "table_no";
    private static final String TABLE_4_COL_5 = "amount";
    private static final String TABLE_4_COL_6 = "item_id";
    private static final String TABLE_4_COL_7 = "item_name";
    private static final String TABLE_4_COL_8 = "item_rate";
    private static final String TABLE_4_COL_9 = "item_quantity";
    private static final String TABLE_4_COL_10 = "item_total_price";

    private static final String TABLE_NAME_5 = "TableItemOrderInformation";
    private static final String TABLE_5_COL_1 = "table_no";
    private static final String TABLE_5_COL_2 = "item_id";
    private static final String TABLE_5_COL_3 = "item_name";
    private static final String TABLE_5_COL_4 = "item_rate";
    private static final String TABLE_5_COL_5 = "item_quantity";
    private static final String TABLE_5_COL_6 = "item_total_price";
    private static final String TABLE_5_COL_7 = "item_sgst_tax";
    private static final String TABLE_5_COL_8 = "item_cgst_tax";
    private static final String TABLE_5_COL_9 = "item_igst_tax";
    private static final String TABLE_5_COL_10 = "item_total_sgst_tax";
    private static final String TABLE_5_COL_11 = "item_total_cgst_tax";
    private static final String TABLE_5_COL_12 = "item_total_igst_tax";
    private static final String TABLE_5_COL_13 = "split_number";
    private static final String TABLE_5_COL_14 = "item_vat_tax";
    private static final String TABLE_5_COL_15 = "item_total_vat_tax";
    private static final String TABLE_5_COL_16 = "barcode";
    private static final String TABLE_5_COL_17 = "alias";
    private static final String TABLE_5_COL_18 = "item_group";
    private static final String TABLE_5_COL_19 = "category";
    private static final String TABLE_5_COL_20 = "serve_unit";
    private static final String TABLE_5_COL_21 = "department";


    private static final String TABLE_NAME_6 = "ReceiptAndOrderNoTable";
    private static final String TABLE_6_COL_1 = "order_no";
    private static final String TABLE_6_COL_2 = "receipt_no";

    private static final String TABLE_NAME_7 = "TodaysReportTable";
    private static final String TABLE_7_COL_1 = "serial";
    private static final String TABLE_7_COL_2 = "total_pending";
    private static final String TABLE_7_COL_3 = "net_sale";

    private static final String TABLE_NAME_8 = "CancelledOrderTable";
    private static final String TABLE_8_COL_1 = "table_no";
    private static final String TABLE_8_COL_2 = "item_id";
    private static final String TABLE_8_COL_3 = "item_name";
    private static final String TABLE_8_COL_4 = "item_rate";
    private static final String TABLE_8_COL_5 = "item_quantity";
    private static final String TABLE_8_COL_6 = "item_total_price";
    private static final String TABLE_8_COL_7 = "item_sgst_tax";
    private static final String TABLE_8_COL_8 = "item_cgst_tax";
    private static final String TABLE_8_COL_9 = "item_igst_tax";
    private static final String TABLE_8_COL_10 = "item_total_sgst_tax";
    private static final String TABLE_8_COL_11 = "item_total_cgst_tax";
    private static final String TABLE_8_COL_12 = "item_total_igst_tax";
    private static final String TABLE_8_COL_13 = "split_number";
    private static final String TABLE_8_COL_14 = "item_vat_tax";
    private static final String TABLE_8_COL_15 = "item_total_vat_tax";
    private static final String TABLE_8_COL_16 = "barcode";
    private static final String TABLE_8_COL_17 = "alias";
    private static final String TABLE_8_COL_18 = "item_group";
    private static final String TABLE_8_COL_19 = "category";
    private static final String TABLE_8_COL_20 = "serve_unit";
    private static final String TABLE_8_COL_21 = "department";

    private static final String TABLE_NAME_9 = "CancelledKotModifyData";
    private static final String TABLE_9_COL_1 = "table_no";
    private static final String TABLE_9_COL_2 = "item_id";
    private static final String TABLE_9_COL_3 = "item_name";
    private static final String TABLE_9_COL_4 = "item_rate";
    private static final String TABLE_9_COL_5 = "item_quantity";
    private static final String TABLE_9_COL_6 = "item_total_price";
    private static final String TABLE_9_COL_7 = "item_sgst_tax";
    private static final String TABLE_9_COL_8 = "item_cgst_tax";
    private static final String TABLE_9_COL_9 = "item_igst_tax";
    private static final String TABLE_9_COL_10 = "item_total_sgst_tax";
    private static final String TABLE_9_COL_11 = "item_total_cgst_tax";
    private static final String TABLE_9_COL_12 = "item_total_igst_tax";
    private static final String TABLE_9_COL_13 = "split_number";
    private static final String TABLE_9_COL_14 = "item_vat_tax";
    private static final String TABLE_9_COL_15 = "item_total_vat_tax";
    private static final String TABLE_9_COL_16 = "barcode";
    private static final String TABLE_9_COL_17 = "alias";
    private static final String TABLE_9_COL_18 = "item_group";
    private static final String TABLE_9_COL_19 = "category";
    private static final String TABLE_9_COL_20 = "serve_unit";
    private static final String TABLE_9_COL_21 = "department";

    private static final String TABLE_NAME_10 = "PrinterInformation";
    private static final String TABLE_10_COL_1 = "name";
    private static final String TABLE_10_COL_2 = "mAddress";

    private static final String TABLE_NAME_11 = "paymentOptions";
    private static final String TABLE_11_COL_0 = "Id";
    private static final String TABLE_11_COL_1 = "Cash";
    private static final String TABLE_11_COL_2 = "Card";
    private static final String TABLE_11_COL_3 = "Paytm";
    private static final String TABLE_11_COL_4 = "Other";

    private static final String TABLE_NAME_12 = "TableOrderDetails";
    private static final String TABLE_12_COL_1 = "Bill_no";
    private static final String TABLE_12_COL_2 = "Customer_name";
    private static final String TABLE_12_COL_3 = "Dinenig_Area";
    private static final String TABLE_12_COL_4 = "Table_No";
    private static final String TABLE_12_COL_5 = "Waiter_Name";
    private static final String TABLE_12_COL_6 = "Date_Time";
    private static final String TABLE_12_COL_7 = "Beofer_tax_amount";
    private static final String TABLE_12_COL_8 = "Tax_amount";
    private static final String TABLE_12_COL_9 = "SGST";
    private static final String TABLE_12_COL_10 = "CGST";
    private static final String TABLE_12_COL_11 = "Total_Amount";
    private static final String TABLE_12_COL_12 = "Discount_amount";
    private static final String TABLE_12_COL_13 = "Round_off_amount";
    private static final String TABLE_12_COL_14 = "Payment_method";

    private static final String TABLE_NAME_13 = "MenuItems";
    private static final String TABLE_13_COL_1 = "SerNo";
    private static final String TABLE_13_COL_2 = "Menu_Name";
    private static final String TABLE_13_COL_3 = "Qty";
    private static final String TABLE_13_COL_4 = "Rate";
    private static final String TABLE_13_COL_5 = "Tax";
    private static final String TABLE_13_COL_6 = "Amount";


    private static final String TABLE_NAME_14 = "WaiterWithDiscount";
    private static final String TABLE_14_COL_0 = "waiter_id";
    private static final String TABLE_14_COL_1 = "waiter_name";
    private static final String TABLE_14_COL_2 = "table_name";
    private static final String TABLE_14_COL_3 = "discount";
    private static final String TAG = "OrderDatabseHelper";

    private static final String TABLE_NAME_15 = "ForStroreBillNo";
    private static final String TABLE_15_COL_0 = "finalBillNo";

    public OrderDatabseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_1 + "(item_id TEXT,item_name TEXT," +
                "item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,selected_floor TEXT," +
                "table_id TEXT,table_name TEXT,sgst_tax REAL,cgst_tax REAL,igst_tax REAL," +
                "total_sgst_tax REAL,total_cgst_tax REAL,total_igst_tax REAL, item_vat_tax REAL,total_item_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(item_sr INTEGER PRIMARY KEY,item_id TEXT,item_name TEXT, item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,selected_floor TEXT,table_id TEXT,table_name TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_3 + "(order_no INTEGER,receipt_no TEXT, order_for TEXT,dining_area TEXT," +
                "date_time TEXT,guest_name TEXT,amount INTEGER,due INTEGER,status TEXT,order_by TEXT,table_no TEXT,total_tax REAL," +
                "split_number TEXT, discount REAL, waiter_id TEXT)");


        /*sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_3 + "("+TABLE_3_COL_1+" INTEGER," +
                TABLE_3_COL_1+" TEXT, TABLE_3_COL_1 TEXT,dining_area TEXT," +
                "date_time TEXT,guest_name TEXT,amount INTEGER,due INTEGER,status TEXT,order_by TEXT,table_no TEXT,total_tax REAL," +
                "split_number TEXT, discount REAL, waiter_id TEXT)");*/

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_4 + "(order_no INTEGER,receipt_no TEXT,floor_no TEXT,table_no TEXT" +
                ",amount INTEGER,item_id TEXT,item_name TEXT, item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_5 + "(table_no TEXT,item_id TEXT,item_name TEXT," +
                " item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,item_sgst_tax REAL," +
                "item_cgst_tax REAL,item_igst_tax REAL,item_total_sgst_tax REAL,item_total_cgst_tax REAL," +
                "item_total_igst_tax REAL,split_number TEXT,item_vat_tax REAL, item_total_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_6 + "(order_no TEXT,receipt_no TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_7 + "(serial TEXT,total_pending INTEGER,net_sale INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_8 + "(table_no TEXT,item_id TEXT,item_name TEXT," +
                " item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,item_sgst_tax REAL," +
                "item_cgst_tax REAL,item_igst_tax REAL,item_total_sgst_tax REAL,item_total_cgst_tax REAL," +
                "item_total_igst_tax REAL,split_number TEXT,item_vat_tax REAL, item_total_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_9 + "(table_no TEXT,item_id TEXT,item_name TEXT," +
                " item_rate INTEGER,item_quantity INTEGER,item_total_price INTEGER,item_sgst_tax REAL," +
                "item_cgst_tax REAL,item_igst_tax REAL,item_total_sgst_tax REAL,item_total_cgst_tax REAL," +
                "item_total_igst_tax REAL,split_number TEXT,item_vat_tax REAL, item_total_vat_tax REAL," +
                "barcode TEXT,alias TEXT,item_group TEXT,category TEXT,serve_unit TEXT,department TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_10 + "(name TEXT,mAddress TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_11 + "(Id TEXT,Cash REAL,Card REAL,Paytm REAL,Other REAL)");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_12 + "(Bill_no TEXT,Customer_name TEXT,Dinenig_Area TEXT," +
                "Table_No TEXT,Waiter_Name TEXT,Date_Time TEXT,Beofer_tax_amount REAL," +
                "Tax_amount TEXT,SGST TEXT,CGST REAL,Total_Amount REAL,Discount_amount REAL," +
                "Round_off_amount REAL,Payment_method REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_13 + "(SerNo TEXT,Menu_Name REAL,Qty REAL,Rate REAL,Tax REAL,Amount REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_14 + "(waiter_id INTEGER,waiter_name TEXT,table_name TEXT,discount TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_15 + "(finalBillNo REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_5);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_6);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_7);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_8);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_9);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_10);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_11);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_12);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_13);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_14);
        onCreate(sqLiteDatabase);
    }

    //---------------------------------------------------------------------------------
    public boolean insertCardOrder(String item_id, String item_name, int item_rate, int item_quantity,
                                   int item_total_price, String selected_floor, String table_id,
                                   String table_name, float sgst_tax, float cgst_tax, float igst_tax,
                                   float total_sgst_tax, float total_cgst_tax, float total_igst_tax,
                                   float item_vat_tax, float total_item_vat_tax, String barcode, String alias,
                                   String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_1, item_id);
        contentValues.put(TABLE_1_COL_2, item_name);
        contentValues.put(TABLE_1_COL_3, item_rate);
        contentValues.put(TABLE_1_COL_4, item_quantity);
        contentValues.put(TABLE_1_COL_5, item_total_price);
        contentValues.put(TABLE_1_COL_6, selected_floor);
        contentValues.put(TABLE_1_COL_7, table_id);
        contentValues.put(TABLE_1_COL_8, table_name);
        contentValues.put(TABLE_1_COL_9, sgst_tax);
        contentValues.put(TABLE_1_COL_10, cgst_tax);
        contentValues.put(TABLE_1_COL_11, igst_tax);
        contentValues.put(TABLE_1_COL_12, total_sgst_tax);
        contentValues.put(TABLE_1_COL_13, total_cgst_tax);
        contentValues.put(TABLE_1_COL_14, total_igst_tax);
        contentValues.put(TABLE_1_COL_15, item_vat_tax);
        contentValues.put(TABLE_1_COL_16, total_item_vat_tax);
        contentValues.put(TABLE_1_COL_17, barcode);
        contentValues.put(TABLE_1_COL_18, alias);
        contentValues.put(TABLE_1_COL_19, item_group);
        contentValues.put(TABLE_1_COL_20, category);
        contentValues.put(TABLE_1_COL_21, serve_unit);
        contentValues.put(TABLE_1_COL_22, department);
        long result = db.insert(TABLE_NAME_1, null, contentValues);
        Log.d(TAG, "insertCardOrder: inserting new item " + item_name + " " + item_id);
        kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(item_name ,item_quantity));
        Log.e("items", ""+kot_items_bill_nodes.get(0).getItem_node());

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllCardOrderData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_1, null);
        return res;
    }

    public boolean updateQuantityAndTotalPrice(String id, String item_name, int item_rate, int item_quantity
            , int item_total_price, String selected_floor, String table_id, String table_name
            , float total_sgst_tax, float total_cgst_tax, float total_igst_tax
            , float total_vat_tax) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_1, id);
        contentValues.put(TABLE_1_COL_2, item_name);
        contentValues.put(TABLE_1_COL_3, item_rate);
        contentValues.put(TABLE_1_COL_4, item_quantity);
        contentValues.put(TABLE_1_COL_5, item_total_price);
        contentValues.put(TABLE_1_COL_6, selected_floor);
        contentValues.put(TABLE_1_COL_7, table_id);
        contentValues.put(TABLE_1_COL_8, table_name);
        contentValues.put(TABLE_1_COL_12, total_sgst_tax);
        contentValues.put(TABLE_1_COL_13, total_cgst_tax);
        contentValues.put(TABLE_1_COL_14, total_igst_tax);
        contentValues.put(TABLE_1_COL_16, total_vat_tax);
        db.update(TABLE_NAME_1, contentValues, "item_id = ?", new String[]{id});
        Log.d(TAG, "insertCardOrder: updating item " + item_name + " " + id);
        return true;
    }

    public boolean UpdateData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_5, "0");
        db.update(TABLE_NAME_1, contentValues, "username = ? AND password = ?", new String[]{username, password});
        db.close();
        return true;
    }

    public Integer deleteCardOrderItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_1, "item_id = ?", new String[]{id});
    }

    //-----------------------------------------------------------------------------------

//    public boolean insertKitchenOrder(int item_serial,String item_id, String item_name,int item_rate,int item_quantity,int item_total_price, String selected_floor, String table_id,String table_name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TABLE_2_COL_1,item_serial);
//        contentValues.put(TABLE_2_COL_2,item_id);
//        contentValues.put(TABLE_2_COL_3,item_name);
//        contentValues.put(TABLE_2_COL_4,item_rate);
//        contentValues.put(TABLE_2_COL_5,item_quantity);
//        contentValues.put(TABLE_2_COL_6,item_total_price);
//        contentValues.put(TABLE_2_COL_7,selected_floor);
//        contentValues.put(TABLE_2_COL_8,table_id);
//        contentValues.put(TABLE_2_COL_9,table_name);
//        long result = db.insert(TABLE_NAME_2,null ,contentValues);
//        Log.d(TAG, "insertKitchenOrder: inserting new item "+item_name+" "+item_id);
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }
//
//    public Cursor getAllKitchenOrderData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME_2,null);
//        return res;
//    }
//
//    public Cursor getInitialKOTData(String table_no) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME_2+" where table_id = '"+table_no+"'",null);
//        return res;
//    }
//
//
//    public boolean updateQuantityAndTotalPriceKOT(String id,String item_name,int item_rate,int item_quantity,int item_total_price,String selected_floor, String table_id,String table_name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TABLE_2_COL_2,id);
//        contentValues.put(TABLE_2_COL_3,item_name);
//        contentValues.put(TABLE_2_COL_4,item_rate);
//        contentValues.put(TABLE_2_COL_5,item_quantity);
//        contentValues.put(TABLE_2_COL_6,item_total_price);
//        contentValues.put(TABLE_2_COL_7,selected_floor);
//        contentValues.put(TABLE_2_COL_8,table_id);
//        contentValues.put(TABLE_2_COL_9,table_name);
//        db.update(TABLE_NAME_2, contentValues, "item_id = ?",new String[] { id });
//        Log.d(TAG, "updateQuantityAndTotalPriceKOT: updating item "+item_name+" "+ id);
//        return true;
//    }

    public void trancateCardOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_1);
    }

    public void trancateKotOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_2);
    }

    //--------------------------------------------------------------------------------
    public boolean insertOrderListItem(int order_no, int receipt_no, String order_for, String dining_area,
                                       String date_time, String guest_name, int amount, int due, String status,
                                       String order_by, String table_no, float total_tax, String split_number, float discount, String waiter_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_1, order_no);
        contentValues.put(TABLE_3_COL_2, receipt_no);
        contentValues.put(TABLE_3_COL_3, order_for);
        contentValues.put(TABLE_3_COL_4, dining_area);
        contentValues.put(TABLE_3_COL_5, date_time);
        contentValues.put(TABLE_3_COL_6, guest_name);
        contentValues.put(TABLE_3_COL_7, amount);
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_9, status);
        contentValues.put(TABLE_3_COL_10, order_by);
        contentValues.put(TABLE_3_COL_11, table_no);
        contentValues.put(TABLE_3_COL_12, total_tax);
        contentValues.put(TABLE_3_COL_13, split_number);
        contentValues.put(TABLE_3_COL_14, discount);
        contentValues.put(TABLE_3_COL_15, waiter_id);
        long result = db.insert(TABLE_NAME_3, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateOrderListItem(String table_no, String date_time, int amount, int due, String status, float total_tax, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_5, date_time);
        contentValues.put(TABLE_3_COL_7, amount);
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_9, status);
        contentValues.put(TABLE_3_COL_12, total_tax);
        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, split_number});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }


    public boolean updateOrderListItem(String table_no, int amount, int due, String status, float total_tax, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_7, amount);
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_9, status);
        contentValues.put(TABLE_3_COL_12, total_tax);
        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, split_number});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }

    public boolean updateOnFinishItem(String table_no, int due, String status, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_9, status);
        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, split_number});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }

    public boolean updateOrderListItemDiscountMoney2(String table_no, int amount, int due, double discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_7, amount);
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_14, discount);
//        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?",new String[] { table_no,splitNumber });
        db.update(TABLE_NAME_3, contentValues, "table_no =?", new String[]{table_no});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }


    public boolean updateOrderListItemDiscountMoney(String table_no, int amount, int due, double discount, String splitNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_7, amount);
        contentValues.put(TABLE_3_COL_8, due);
        contentValues.put(TABLE_3_COL_14, discount);
        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, splitNumber});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }


    public boolean updateOrderDetailsSplitNumber(String table_no, String split_number, String split_number_second) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_13, split_number);
        db.update(TABLE_NAME_3, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, split_number_second});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }

    public boolean updateOrderListItemTableStatus(String table_no_old, String table_no_new) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_11, table_no_new);
        db.update(TABLE_NAME_3, contentValues, "table_no = ?", new String[]{table_no_old});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no_old + " " + table_no_new);
        return true;
    }

    public Cursor getRunningOrderList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3 + " where status = 'running'", null);
        return res;
    }

    public Cursor getDayCloseList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3, null);
        return res;
    }

    public Cursor getCompletedOrderList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3 + " where status = 'Completed'", null);
        return res;
    }

    public Cursor getTakeAwayOrderList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3 + " where order_for = 'Take away'", null);
        return res;
    }

    public Cursor getDataOfSelectedTable(String table_no, String split_number) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME_3 + " where table_no = '" + table_no + "' AND status = 'running' AND split_number ='" + split_number + "'", null);
        return res;
    }


    //------------------------------------------------------------------------------------

//    public boolean insertRunningOrderTable(int order_no , String receipt_no, String floor_no , String table_no , int amount, String item_id, String item_name, int item_rate, int item_quantity ,int  item_total_price){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TABLE_4_COL_1,order_no);
//        contentValues.put(TABLE_4_COL_2,receipt_no);
//        contentValues.put(TABLE_4_COL_3,floor_no);
//        contentValues.put(TABLE_4_COL_4,table_no);
//        contentValues.put(TABLE_4_COL_5,amount);
//        contentValues.put(TABLE_4_COL_6,item_id);
//        contentValues.put(TABLE_4_COL_7,item_name);
//        contentValues.put(TABLE_4_COL_8,item_rate);
//        contentValues.put(TABLE_4_COL_9,item_quantity);
//        contentValues.put(TABLE_4_COL_10,item_total_price);
//        long result = db.insert(TABLE_NAME_4,null ,contentValues);
//        Log.d(TAG, "insertKitchenOrder: inserting new item "+item_name+" "+item_id);
//        if(result == -1)
//        return false;
//        else
//            return true;
//    }
//
//    public Cursor getAllOrderData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME_2,null);
//        return res;
//    }
//
//    public Cursor getKOTInitialData(String table_no) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME_4+" where table_no = '"+table_no+"'",null);
//        return res;
//    }
//
//    public void trancateRunningOrderList(String table_no){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME_4, "table_no = ?",new String[] {table_no});
//    }

    //-----------------------------------------------------------------------

    public boolean insertTableItemOrderInformation(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float sgst_tax, float cgst_tax
            , float igst_tax, float total_sgst_tax, float total_cgst_tax, float total_igst_tax, String split_number
            , float item_vat_tax, float total_item_vat_tax, String barcode, String alias
            , String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_1, table_no);
        contentValues.put(TABLE_5_COL_2, item_id);
        contentValues.put(TABLE_5_COL_3, item_name);
        contentValues.put(TABLE_5_COL_4, item_rate);
        contentValues.put(TABLE_5_COL_5, item_quantity);
        contentValues.put(TABLE_5_COL_6, item_total_price);
        contentValues.put(TABLE_5_COL_7, sgst_tax);
        contentValues.put(TABLE_5_COL_8, cgst_tax);
        contentValues.put(TABLE_5_COL_9, igst_tax);
        contentValues.put(TABLE_5_COL_10, total_sgst_tax);
        contentValues.put(TABLE_5_COL_11, total_cgst_tax);
        contentValues.put(TABLE_5_COL_12, total_igst_tax);
        contentValues.put(TABLE_5_COL_13, split_number);
        contentValues.put(TABLE_5_COL_14, item_vat_tax);
        contentValues.put(TABLE_5_COL_15, total_item_vat_tax);
        contentValues.put(TABLE_5_COL_16, barcode);
        contentValues.put(TABLE_5_COL_17, alias);
        contentValues.put(TABLE_5_COL_18, item_group);
        contentValues.put(TABLE_5_COL_19, category);
        contentValues.put(TABLE_5_COL_20, serve_unit);
        contentValues.put(TABLE_5_COL_21, department);
        long result = db.insert(TABLE_NAME_5, null, contentValues);
        Log.d(TAG, "insertTableItemOrderInformation: inserting new item " + item_name + " " + item_id);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updatTableItemOrderInformation(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float item_total_sgst_tax,
                                                  float item_total_cgst_tax, float item_total_igst_tax, float item_total_vat_tax, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_2, item_id);
        contentValues.put(TABLE_5_COL_3, item_name);
        contentValues.put(TABLE_5_COL_4, item_rate);
        contentValues.put(TABLE_5_COL_5, item_quantity);
        contentValues.put(TABLE_5_COL_6, item_total_price);
        contentValues.put(TABLE_5_COL_10, item_total_sgst_tax);
        contentValues.put(TABLE_5_COL_11, item_total_cgst_tax);
        contentValues.put(TABLE_5_COL_12, item_total_igst_tax);
        contentValues.put(TABLE_5_COL_15, item_total_vat_tax);
        long ans = db.update(TABLE_NAME_5, contentValues, "item_id = ? AND table_no = ? AND split_number = ? ", new String[]{item_id, table_no, split_number});
        Log.d(TAG, "updatTableItemOrderInformation: updating item " + item_name + " " + table_no + " " + ans);
        //kot_items_bill_nodes.addFirst(new Kot_Items_Bill_Node(item_name , item_quantity));
        return true;
    }

    public Cursor getTableItemOrderInformation(String table_no, String table_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_5 + " where table_no = '" + table_no + "'" + " AND split_number = '" + table_number + "'", null);
        return res;
    }

    public boolean updateTableItemOrderInformationTableStatus(String table_no_old, String table_no_new) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_1, table_no_new);
        db.update(TABLE_NAME_5, contentValues, "table_no = ?", new String[]{table_no_old});
        Log.d(TAG, "updateTableItemOrderInformationTableStatus: updating item " + table_no_old + " " + table_no_new);
        return true;
    }

    public boolean updateTableItemOrderInformationTableRate(String table_no, String split_number, int rate, String item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_4, rate);
        db.update(TABLE_NAME_5, contentValues, "table_no = ? AND split_number = ? AND item_id = ?", new String[]{table_no, split_number, item_id});
        Log.d(TAG, "updateTableItemOrderInformationTableStatus: updating item " + table_no + " " + rate);
        return true;
    }


    public boolean updateATableItemOrderInformationSplitNumber(String table_no, String split_number, String split_number_second) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_13, split_number);
        db.update(TABLE_NAME_5, contentValues, "table_no = ? AND split_number = ?", new String[]{table_no, split_number_second});
        Log.d(TAG, "updateOrderListItem: updating item " + table_no);
        return true;
    }

    public void trancateFreeTableItems(String table_no, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_5, "table_no = ? AND split_number = ? ", new String[]{table_no, split_number});
    }

    //-------------------------------------------------------------------------------

    public boolean insertReceiptAndOrderNoTable(int order_no, int receipt_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_6_COL_1, order_no);
        contentValues.put(TABLE_6_COL_2, receipt_no);
        long result = db.insert(TABLE_NAME_6, null, contentValues);
        Log.d(TAG, "insertReceiptAndOrderNoTable: inserting new item " + order_no + " " + receipt_no);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updatReceiptAndOrderNoTable(String order_no, String receipt_no, String initial_receipt_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_6_COL_1, order_no);
        contentValues.put(TABLE_6_COL_2, receipt_no);
        db.update(TABLE_NAME_6, contentValues, "receipt_no = ?", new String[]{initial_receipt_no});
        Log.d(TAG, "updatReceiptAndOrderNoTable: updating item " + order_no);
        return true;
    }

    public Cursor getReceiptAndOrderNoTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_6, null);
        return res;
    }

    //------------------------------------------------------------------------------

    public boolean insertTodaysReportTable(String serial, int total_pending, int net_sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_7_COL_1, serial);
        contentValues.put(TABLE_7_COL_2, total_pending);
        contentValues.put(TABLE_7_COL_3, net_sale);
        long result = db.insert(TABLE_NAME_7, null, contentValues);
        Log.d(TAG, "insertTodaysReportTable: inserting new amount " + total_pending + " " + net_sale);
        if (result == -1)
            return false;
        else
            return true;
    }

    //-----------------------------------------------------------------------------

    public boolean insertCancelledOrderInfo(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float sgst_tax, float cgst_tax
            , float igst_tax, float total_sgst_tax, float total_cgst_tax, float total_igst_tax, String split_number
            , float item_vat_tax, float total_item_vat_tax, String barcode, String alias
            , String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_8_COL_1, table_no);
        contentValues.put(TABLE_8_COL_2, item_id);
        contentValues.put(TABLE_8_COL_3, item_name);
        contentValues.put(TABLE_8_COL_4, item_rate);
        contentValues.put(TABLE_8_COL_5, item_quantity);
        contentValues.put(TABLE_8_COL_6, item_total_price);
        contentValues.put(TABLE_8_COL_7, sgst_tax);
        contentValues.put(TABLE_8_COL_8, cgst_tax);
        contentValues.put(TABLE_8_COL_9, igst_tax);
        contentValues.put(TABLE_8_COL_10, total_sgst_tax);
        contentValues.put(TABLE_8_COL_11, total_cgst_tax);
        contentValues.put(TABLE_8_COL_12, total_igst_tax);
        contentValues.put(TABLE_8_COL_13, split_number);
        contentValues.put(TABLE_8_COL_14, item_vat_tax);
        contentValues.put(TABLE_8_COL_15, total_item_vat_tax);
        contentValues.put(TABLE_8_COL_16, barcode);
        contentValues.put(TABLE_8_COL_17, alias);
        contentValues.put(TABLE_8_COL_18, item_group);
        contentValues.put(TABLE_8_COL_19, category);
        contentValues.put(TABLE_8_COL_20, serve_unit);
        contentValues.put(TABLE_8_COL_21, department);
        long result = db.insert(TABLE_NAME_8, null, contentValues);
        Log.d(TAG, "insertTableItemOrderInformation: inserting new item " + item_name + " " + item_id);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateCancelledOrderInfo(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float item_total_sgst_tax
            , float item_total_cgst_tax, float item_total_igst_tax, float item_total_vat_tax, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_8_COL_2, item_id);
        contentValues.put(TABLE_8_COL_3, item_name);
        contentValues.put(TABLE_8_COL_4, item_rate);
        contentValues.put(TABLE_8_COL_5, item_quantity);
        contentValues.put(TABLE_8_COL_6, item_total_price);
        contentValues.put(TABLE_8_COL_10, item_total_sgst_tax);
        contentValues.put(TABLE_8_COL_11, item_total_cgst_tax);
        contentValues.put(TABLE_8_COL_12, item_total_igst_tax);
        contentValues.put(TABLE_8_COL_15, item_total_vat_tax);
        long ans = db.update(TABLE_NAME_8, contentValues, "item_id = ? AND table_no = ? AND split_number = ? ", new String[]{item_id, table_no, split_number});
        Log.d(TAG, "updatTableItemOrderInformation: updating item " + item_name + " " + table_no + " " + ans);
        return true;
    }

    public Cursor getCancelledOrderInfo(String table_no, String table_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_8 + " where table_no = '" + table_no + "'" + " AND split_number = '" + table_number + "'", null);
        return res;
    }

    public void trancateCancelledTableItems(String table_no, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_8, "table_no = ? AND split_number = ? ", new String[]{table_no, split_number});
    }

    //--------------------------------------------------------------------------

    public boolean insertCancelledKotModifiedData(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float sgst_tax, float cgst_tax
            , float igst_tax, float total_sgst_tax, float total_cgst_tax, float total_igst_tax, String split_number
            , float item_vat_tax, float total_item_vat_tax, String barcode, String alias
            , String item_group, String category, String serve_unit, String department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_9_COL_1, table_no);
        contentValues.put(TABLE_9_COL_2, item_id);
        contentValues.put(TABLE_9_COL_3, item_name);
        contentValues.put(TABLE_9_COL_4, item_rate);
        contentValues.put(TABLE_9_COL_5, item_quantity);
        contentValues.put(TABLE_9_COL_6, item_total_price);
        contentValues.put(TABLE_9_COL_7, sgst_tax);
        contentValues.put(TABLE_9_COL_8, cgst_tax);
        contentValues.put(TABLE_9_COL_9, igst_tax);
        contentValues.put(TABLE_9_COL_10, total_sgst_tax);
        contentValues.put(TABLE_9_COL_11, total_cgst_tax);
        contentValues.put(TABLE_9_COL_12, total_igst_tax);
        contentValues.put(TABLE_9_COL_13, split_number);
        contentValues.put(TABLE_9_COL_14, item_vat_tax);
        contentValues.put(TABLE_9_COL_15, total_item_vat_tax);
        contentValues.put(TABLE_9_COL_16, barcode);
        contentValues.put(TABLE_9_COL_17, alias);
        contentValues.put(TABLE_9_COL_18, item_group);
        contentValues.put(TABLE_9_COL_19, category);
        contentValues.put(TABLE_9_COL_20, serve_unit);
        contentValues.put(TABLE_9_COL_21, department);
        long result = db.insert(TABLE_NAME_9, null, contentValues);
        Log.d(TAG, "insertTableItemOrderInformation: inserting new item " + item_name + " " + item_id);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateCancelledKotModifiedData(String table_no, String item_id, String item_name
            , int item_rate, int item_quantity, int item_total_price, float item_total_sgst_tax
            , float item_total_cgst_tax, float item_total_igst_tax, float item_total_vat_tax, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_9_COL_2, item_id);
        contentValues.put(TABLE_9_COL_3, item_name);
        contentValues.put(TABLE_9_COL_4, item_rate);
        contentValues.put(TABLE_9_COL_5, item_quantity);
        contentValues.put(TABLE_9_COL_6, item_total_price);
        contentValues.put(TABLE_9_COL_10, item_total_sgst_tax);
        contentValues.put(TABLE_9_COL_11, item_total_cgst_tax);
        contentValues.put(TABLE_9_COL_12, item_total_igst_tax);
        contentValues.put(TABLE_9_COL_15, item_total_vat_tax);
        long ans = db.update(TABLE_NAME_9, contentValues, "item_id = ? AND table_no = ? AND split_number = ? ", new String[]{item_id, table_no, split_number});
        Log.d(TAG, "updatTableItemOrderInformation: updating item " + item_name + " " + table_no + " " + ans);
        return true;
    }

    public Cursor getCancelledKotModifiedData(String table_no, String table_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_8 + " where table_no = '" + table_no + "'" + " AND split_number = '" + table_number + "'", null);
        return res;
    }

    public Cursor getCancelledModifiedData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_9, null);
        return res;
    }

    public Cursor getCancelledOrderDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_8, null);
        return res;
    }

    public Cursor getCancelledOrderDetailData() {
        Cursor res = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM CancelledOrderTable";
        res = db.rawQuery(count, null);
        res.moveToFirst();
        int icount = res.getInt(0);
        if (icount > 0) {
            return res;
        } else {
            return res;
        }

    }

    public void trancateCancelledKotModifiedData(String table_no, String split_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_9, "table_no = ? AND split_number = ? ", new String[]{table_no, split_number});
    }

    //---------------------------------------------------------
    public boolean insertPrinterInformation(String name, String mAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_10_COL_1, name);
        contentValues.put(TABLE_10_COL_2, mAddress);
        long result = db.insert(TABLE_NAME_10, null, contentValues);
        Log.d(TAG, "insertPrinterInformation: inserting new information " + name + " " + mAddress);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updatePrinterInformation(String name, String mAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_10_COL_2, mAddress);
        db.update(TABLE_NAME_10, contentValues, "name = ?", new String[]{name});
        Log.d(TAG, "updatePrinterInformation: updating " + name + " " + mAddress);
        return true;
    }

    public Cursor getAllPrinterInformation() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_10, null);
        return res;
    }

    public Cursor getmAddress(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_10 + " where name = '" + name + "'", null);
        return res;
    }

    public void removePrinterInformation(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_10, "name = ?", new String[]{name});
    }

    public void trancatePrinterInformation() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_10);
    }

    //===================================================================================
    public boolean insertPaymentOptions(float cash, float card, float paytm, float other) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_11_COL_0, "id");
        contentValues.put(TABLE_11_COL_1, cash);
        contentValues.put(TABLE_11_COL_2, card);
        contentValues.put(TABLE_11_COL_3, paytm);
        contentValues.put(TABLE_11_COL_4, other);
        Log.d(TAG, "insertPaymentOptions: inserting new payment");
        long result = db.insert(TABLE_NAME_11, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updatePaymentOptions(float cash, float card, float paytm, float other) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "updatePaymentOptions: updating payment");
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_11_COL_1, cash);
        contentValues.put(TABLE_11_COL_2, card);
        contentValues.put(TABLE_11_COL_3, paytm);
        contentValues.put(TABLE_11_COL_4, other);
        db.update(TABLE_NAME_11, contentValues, "Id = ?", new String[]{"id"});
        return true;
    }

    public boolean getOrderDetails(String Bill_no, String customer_name, String dinenig_Area, String table_No, String waiter_Name,
                                   String date_Time, String beofer_tax_amount, String tax_amount, String SGST, String CGST,
                                   String total_Amount, String discount_amount, String round_off_amount, String payment_method) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "updatePaymentOptions: updating payment");
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_12_COL_1, Bill_no);
        contentValues.put(TABLE_12_COL_2, customer_name);
        contentValues.put(TABLE_12_COL_3, dinenig_Area);
        contentValues.put(TABLE_12_COL_4, table_No);
        contentValues.put(TABLE_12_COL_5, waiter_Name);
        contentValues.put(TABLE_12_COL_6, date_Time);
        contentValues.put(TABLE_12_COL_7, beofer_tax_amount);
        contentValues.put(TABLE_12_COL_8, tax_amount);
        contentValues.put(TABLE_12_COL_9, SGST);
        contentValues.put(TABLE_12_COL_10, CGST);
        contentValues.put(TABLE_12_COL_11, total_Amount);
        contentValues.put(TABLE_12_COL_12, discount_amount);
        contentValues.put(TABLE_12_COL_13, round_off_amount);
        contentValues.put(TABLE_12_COL_14, payment_method);
        long result = db.insert(TABLE_NAME_12, null, contentValues);
        Log.d(TAG, "insertCardOrder: inserting new item " + table_No + " " + waiter_Name);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean setMenuItem(String id, String stritemName, String stritemQty, String stritemRate, String stritemTax, String stritemAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_13_COL_1, id);
        contentValues.put(TABLE_13_COL_2, stritemName);
        contentValues.put(TABLE_13_COL_3, stritemQty);
        contentValues.put(TABLE_13_COL_4, stritemRate);
        contentValues.put(TABLE_13_COL_5, stritemTax);
        contentValues.put(TABLE_13_COL_6, stritemAmount);
        Log.d(TAG, "insertPaymentOptions: inserting new payment");
        long result = db.insert(TABLE_NAME_13, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean setBillNumber(String billNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_15_COL_0, billNumber);
        long result = db.insert(TABLE_NAME_15, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getBillNumber() {
        Cursor res = null;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME_15, null);
        return res;
    }

    public boolean setWaiterDiscount(String waiter_id, String waiter_name, String table_name, String discount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_14_COL_0, waiter_id);
        contentValues.put(TABLE_14_COL_1, waiter_name);
        contentValues.put(TABLE_14_COL_2, table_name);
        contentValues.put(TABLE_14_COL_3, discount);
        Log.d(TAG, "insertPaymentOptions: Waiter Discountt");
        long result = db.insert(TABLE_NAME_14, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getWaiterWithDiscount() {
        Cursor res = null;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME_14, null);
        return res;
    }

    public boolean Update_Discount(String waitedId, String strWaiterName, String strTableNo, String disAmo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_14_COL_3, disAmo);
        db.update(TABLE_NAME_14, contentValues, "waiter_id = ? AND waiter_name = ? AND table_name = ? ", new String[]{waitedId, strWaiterName, strTableNo});
        return true;
    }

    public Cursor getDiscountByTable(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT discount FROM WaiterWithDiscount WHERE table_name=type", null);
//        Cursor res = db.rawQuery("select discount,waiter_id,waiter_name from " + TABLE_NAME_14 + " where table_name = " + type, null);
        Cursor res = db.rawQuery("select discount from " + TABLE_NAME_14 + " where table_name = " + type, null);
        return res;
    }

    public Cursor getDiscountByTable3(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select discount,waiter_id,waiter_name from" + TABLE_NAME_14 + " where table_name = " + type;
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public ForWaiterDetails getDiscountByTableF(String type) {
        Cursor cursor = null;
        ForWaiterDetails details = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select discount , waiter_id , waiter_name from" + TABLE_NAME_14 + " where table_name = " + type;
        Log.e("type at gal",""+type);
        String query2 = TABLE_14_COL_0 +" = "+type;
//        Cursor res = db.rawQuery(query, null);
        cursor = db.query(TABLE_NAME_14, new String[]{TABLE_14_COL_0, TABLE_14_COL_1, TABLE_14_COL_3}
                , type,null  , null, null, null);

        if (cursor != null)
            if (cursor.moveToFirst()) {
               String a= cursor.getString(cursor.getColumnIndex(TABLE_14_COL_0));
               String a2= cursor.getString(cursor.getColumnIndex(TABLE_14_COL_1));
               String a3= cursor.getString(cursor.getColumnIndex(TABLE_14_COL_3));
         Log.e("at cursor "+TABLE_14_COL_0,""+cursor.getString(cursor.getColumnIndex(TABLE_14_COL_0)));
         Log.e("at cursor "+TABLE_14_COL_1,""+cursor.getString(cursor.getColumnIndex(TABLE_14_COL_1)));
         Log.e("at cursor "+TABLE_14_COL_3,""+cursor.getString(cursor.getColumnIndex(TABLE_14_COL_3)));
                details = new ForWaiterDetails(cursor.getString(cursor.getColumnIndex(TABLE_14_COL_0)),
                        cursor.getString(cursor.getColumnIndex(TABLE_14_COL_1)),
                        cursor.getString(cursor.getColumnIndex(TABLE_14_COL_3)));
                cursor.close();
            }
            db.close();
        return details;
    }

    public Cursor getDiscountByTable2(String type, String id, String name) {
        boolean assignmentStored = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columnNames = {TABLE_14_COL_0, TABLE_14_COL_1, TABLE_14_COL_3};
        String selection = "table_name =?";
        String[] selectionArgs = {type};
         String query = "select discount , waiter_id , waiter_name from " + TABLE_NAME_14 + " where waiter_id = " + type + " AND " + "TABLE_14_COL_3 > 10";
    //   Cursor res = db.query(TABLE_NAME_14, columnNames, selection, selectionArgs, null, null, null);
        Cursor res = db.rawQuery(query,null);

        if (res.getCount() > 0) {
            assignmentStored = true;
        }


        /*res = db.query(TABLE_NAME_14, // Table name
                columnNames, // String[] containing your column names
                TABLE_14_COL_2 + " = " + type, // your where statement, you do not include the WHERE or the FROM DATABASE_TABLE parts of the query,
                null,
                null,
                null,
                null
        );
        if (res.getCount() > 0) {
            assignmentStored = true;
        }*/
      //  res.close();
//        db.close();
        return res;
    }


    public Cursor getPaymentOptions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_11, null);
        return res;
    }

    public void Updateitem(String item_id, String tt, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "updatePaymentOptions: updating payment");
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_4, user);
        contentValues.put(TABLE_5_COL_6, tt);
        db.update(TABLE_NAME_5, contentValues, "item_id = ?", new String[]{item_id});
    }

    public Cursor getAllOrderDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_12, null);
        return res;
    }

    public Cursor getAllMenuItems(String id) {
        Cursor mCursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM MenuItems WHERE SerNo=?";
        mCursor = db.rawQuery(query, new String[]{id});
        return mCursor;
    }

    /* public Integer deleteOrderData(Integer id) {
         SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(TABLE_NAME_12,
                 TABLE_12_COL_1 + " = ? ",
                 new String[] { Integer.toString(id) });
     }*/
    public int deleteOrderDataByBillNo(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_12, TABLE_12_COL_1 + " = ?", new String[]{name});
    }

    public Integer deleteMenuItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_12,
                TABLE_12_COL_1 + " = ? ",
                new String[]{Integer.toString(id)});
    }

    /*public Integer deleteMenuItem1(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_NAME_12, TABLE_12_COL_1 + " = ?", new String[]{userName});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }*/
/*    public Cursor getAllMenuItems(String id) {
        Cursor cursor = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM MenuItems";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        *//*String query = "select * from " + TABLE_NAME_13 + " where  SerNo='" + id;
        Cursor res = db.rawQuery(query, null);*//*
        return mcursor;
        *//*SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM todo WHERE category='" + vg;
        cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;*//*
    }*/


}
