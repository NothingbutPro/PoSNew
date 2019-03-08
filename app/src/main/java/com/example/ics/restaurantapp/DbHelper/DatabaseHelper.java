package com.example.ics.restaurantapp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ICS on 25/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "POSDb";
    public static final String TABLE_NAME = "dineAreaTable";
    public static final String COL_1 = "da_id";
    public static final String COL_2 = "da_name";
    public static final String COL_3 = "outlet_id";

    public static final String TABLE_NAME_2 = "floorTable";
    public static final String TABLE_2_COL_1 = "t_id";
    public static final String TABLE_2_COL_2 = "t_name";
    public static final String TABLE_2_COL_3 = "din_area";
    public static final String TABLE_2_COL_4 = "alias";
    public static final String TABLE_2_COL_5 = "outlet_id";
    public static final String TABLE_2_COL_6 = "d_id";
    public static final String TABLE_2_COL_7 = "status";
    public static final String TABLE_2_COL_8 = "waiter_id";
    public static final String TABLE_2_COL_9 = "splitted";
    public static final String TABLE_2_COL_10 = "waiter_id_2";


    public static final String TABLE_NAME_3 = "waiterTable";
    public static final String TABLE_3_COL_1 = "w_id";
    public static final String TABLE_3_COL_2 = "w_name";
    public static final String TABLE_3_COL_3 = "alias";
    public static final String TABLE_3_COL_4 = "waiter_id";
    public static final String TABLE_3_COL_5 = "outlet_id";
    public static final String TABLE_3_COL_6 = "discount_amo";

    public static final String TABLE_NAME_4 = "menuTable";
    public static final String TABLE_4_COL_1 = "cat_id";
    public static final String TABLE_4_COL_2 = "cat_name";
    public static final String TABLE_4_COL_3 = "outlet_id";

    public static final String TABLE_NAME_5 = "categoryTable";
    public static final String TABLE_5_COL_1 = "g_id";
    public static final String TABLE_5_COL_2 = "g_name";
    public static final String TABLE_5_COL_3 = "category";
    public static final String TABLE_5_COL_4 = "outlet_id";

    public static final String TABLE_NAME_6 = "gridMenuTable";
    public static final String TABLE_6_COL_1 = "m_id";
    public static final String TABLE_6_COL_2 = "m_name";
    public static final String TABLE_6_COL_3 = "barcode";
    public static final String TABLE_6_COL_4 = "alias";
    public static final String TABLE_6_COL_5 = "item_rate1";
    public static final String TABLE_6_COL_6 = "item_rate2";
    public static final String TABLE_6_COL_7 = "item_rate3";
    public static final String TABLE_6_COL_8 = "rate_Taxsgst";
    public static final String TABLE_6_COL_9 = "rate_Taxcgst";
    public static final String TABLE_6_COL_10 = "rate_Taxigst";
    public static final String TABLE_6_COL_11 = "vat";
    public static final String TABLE_6_COL_12 = "group_name";
    public static final String TABLE_6_COL_13 = "category";
    public static final String TABLE_6_COL_14 = "serve_unit";
    public static final String TABLE_6_COL_15 = "department";
    public static final String TABLE_6_COL_16 = "outlet_id";

    public static final String TABLE_NAME_7 = "cardOrderList";
    public static final String TABLE_7_COL_1 = "item_name";
    public static final String TABLE_7_COL_2 = "item_rate";
    public static final String TABLE_7_COL_3 = "item_quantity";
    public static final String TABLE_7_COL_4 = "item_total_price";

    public static final String TABLE_NAME_8 = "searchMenuTable";
    public static final String TABLE_8_COL_1 = "m_id";
    public static final String TABLE_8_COL_2 = "m_name";
    public static final String TABLE_8_COL_3 = "barcode";
    public static final String TABLE_8_COL_4 = "alias";
    public static final String TABLE_8_COL_5 = "item_rate1";
    public static final String TABLE_8_COL_6 = "item_rate2";
    public static final String TABLE_8_COL_7 = "item_rate3";
    public static final String TABLE_8_COL_8 = "rate_Taxsgst";
    public static final String TABLE_8_COL_9 = "rate_Taxcgst";
    public static final String TABLE_8_COL_10 = "rate_Taxigst";
    public static final String TABLE_8_COL_11 = "vat";
    public static final String TABLE_8_COL_12 = "group";
    public static final String TABLE_8_COL_13 = "category";
    public static final String TABLE_8_COL_14 = "serve_unit";
    public static final String TABLE_8_COL_15 = "department";
    public static final String TABLE_8_COL_16 = "outlet_id";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(da_id TEXT PRIMARY KEY, da_name TEXT, outlet_id TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(t_id TEXT PRIMARY KEY, t_name TEXT, din_area TEXT,alias TEXT, outlet_id TEXT,d_id TEXT, " +
                "status TEXT,waiter_id TEXT,splitted TEXT,waiter_id_2 TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_3 + "(w_id TEXT PRIMARY KEY, w_name TEXT, alias TEXT,waiter_id TEXT ,outlet_id TEXT,discount_amo TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_4 + "(cat_id TEXT PRIMARY KEY, cat_name TEXT,outlet_id TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_5 + "(g_id TEXT PRIMARY KEY, g_name TEXT, category TEXT,outlet_id TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_6 + "(m_id TEXT PRIMARY KEY, m_name TEXT,barcode TEXT,alias TEXT,item_rate1 TEXT," +
                "item_rate2 TEXT,item_rate3 TEXT,rate_Taxsgst TEXT,rate_Taxcgst TEXT,rate_Taxigst TEXT,vat TEXT,group_name TEXT,category TEXT," +
                "serve_unit TEXT,department TEXT,outlet_id TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_7 + "(item_name TEXT, item_rate INTEGER, item_quantity INTEGER, item_total_price INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_8 + "(m_id TEXT PRIMARY KEY, m_name TEXT,barcode TEXT,alias TEXT,item_rate1 TEXT," +
                "item_rate2 TEXT,item_rate3 TEXT,rate_Taxsgst TEXT,rate_Taxcgst TEXT,rate_Taxigst TEXT,vat TEXT,group_name TEXT,category TEXT," +
                "serve_unit TEXT,department TEXT,outlet_id TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_5);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_6);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_7);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_8);
        onCreate(sqLiteDatabase);
    }
//-----------------------------------------------------------------------------------

    /**
     * inserting all te dine area/floor of outlet
     *
     * @param da_id
     * @param da_name
     * @param output_id
     * @return
     */
    public boolean insertDineArea(String da_id, String da_name, String output_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, da_id);
        contentValues.put(COL_2, da_name);
        contentValues.put(COL_3, output_id);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public void trancateDineArea() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

//---------------------------------------------------------------------------------------

    /**
     * inserting floor details.
     *
     * @param t_id
     * @param t_name
     * @param din_area
     * @param alias
     * @param outlet_id
     * @param d_id
     * @return
     */
    public boolean insertFloorTable(String t_id, String t_name, String din_area, String alias, String outlet_id, String d_id,
                                    String status, String waiter_id, String splitted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_1, t_id);
        contentValues.put(TABLE_2_COL_2, t_name);
        contentValues.put(TABLE_2_COL_3, din_area);
        contentValues.put(TABLE_2_COL_4, alias);
        contentValues.put(TABLE_2_COL_5, outlet_id);
        contentValues.put(TABLE_2_COL_6, d_id);
        contentValues.put(TABLE_2_COL_7, status);
        contentValues.put(TABLE_2_COL_8, waiter_id);
        contentValues.put(TABLE_2_COL_9, splitted);
        long result = db.insert(TABLE_NAME_2, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * returing details of particular dinArea
     *
     * @param dinArea
     * @return
     */
    public Cursor getAllDataTable2(String dinArea) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_2 + " where din_area = '" + dinArea + "'", null);
        return res;
    }

    public Cursor getTableDetails(String t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_2 + " where t_id = '" + t_id + "'", null);
        return res;
    }


    public boolean updateTableStatus(String t_id, String t_name, String din_area, String alias, String outlet_id, String d_id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_1, t_id);
        contentValues.put(TABLE_2_COL_2, t_name);
        contentValues.put(TABLE_2_COL_3, din_area);
        contentValues.put(TABLE_2_COL_4, alias);
        contentValues.put(TABLE_2_COL_5, outlet_id);
        contentValues.put(TABLE_2_COL_6, d_id);
        contentValues.put(TABLE_2_COL_7, status);
        db.update(TABLE_NAME_2, contentValues, "t_id = ?", new String[]{t_id});
        Log.d(TAG, "updateTableStatus: updating table status " + t_id + " " + status);
        return true;
    }

    public boolean updateTableStatus(String t_id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ;
        contentValues.put(TABLE_2_COL_7, status);
        db.update(TABLE_NAME_2, contentValues, "t_id = ?", new String[]{t_id});
        Log.d(TAG, "updateTableStatus: updating table status " + t_id + " " + status);
        return true;
    }

    public boolean updateTableWaiterID(String t_id, String waiter_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ;
        contentValues.put(TABLE_2_COL_8, waiter_id);
        db.update(TABLE_NAME_2, contentValues, "t_id = ?", new String[]{t_id});
        Log.d(TAG, "updateTableStatus: updating table status " + t_id + " " + waiter_id);
        return true;
    }

    public boolean updateSplittedStatus(String t_id, String splitted, String waiter_id, String waiter_id_2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_8, waiter_id);
        contentValues.put(TABLE_2_COL_9, splitted);
        contentValues.put(TABLE_2_COL_10, waiter_id_2);
        db.update(TABLE_NAME_2, contentValues, "t_id = ?", new String[]{t_id});
        Log.d(TAG, "updateTableStatus: updating table status " + t_id + " " + waiter_id);
        return true;
    }


    public void trancateFloorTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_2);
    }

//-------------------------------------------------------------------------------------------------

    /**
     * inserting data waiters
     *
     * @param w_id
     * @param w_name
     * @param alias
     * @param waiter_id
     * @param outlet_id
     * @return
     */
    public boolean insertWaiterTable(String w_id, String w_name, String alias, String waiter_id, String outlet_id, String discount_amo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_1, w_id);
        contentValues.put(TABLE_3_COL_2, w_name);
        contentValues.put(TABLE_3_COL_3, alias);
        contentValues.put(TABLE_3_COL_4, waiter_id);
        contentValues.put(TABLE_3_COL_5, outlet_id);
        contentValues.put(TABLE_3_COL_6, discount_amo);
        long result = db.insert(TABLE_NAME_3, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataTable3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3, null);
        return res;
    }

    public boolean Update_Discount(String waitedId, String discount_amo, String disAmo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_6, discount_amo);
        db.update(TABLE_NAME_3, contentValues, "w_name = ?", new String[]{waitedId});
        return true;
    }

    public Cursor getWaiterDetails(String waiter_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_3 + " where waiter_id = " + waiter_id, null);
        return res;
    }

    public void trancateWaiterTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_3);
    }


//---------------------------------------------------------------------------------------------

    /**
     * inserting menu of outlet
     *
     * @param cat_id
     * @param cat_name
     * @param output_id
     * @return
     */
    public boolean insertMenuTable(String cat_id, String cat_name, String output_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_4_COL_1, cat_id);
        contentValues.put(TABLE_4_COL_2, cat_name);
        contentValues.put(TABLE_4_COL_3, output_id);
        long result = db.insert(TABLE_NAME_4, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataTable4() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_4, null);
        return res;
    }

    public void trancateMenuTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_4);
    }

//--------------------------------------------------------------------------------------------

    /**
     * inserting all category of menu
     *
     * @param g_id
     * @param g_name
     * @param category
     * @param output_id
     * @return
     */
    public boolean insertCategoryTable(String g_id, String g_name, String category, String output_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_1, g_id);
        contentValues.put(TABLE_5_COL_2, g_name);
        contentValues.put(TABLE_5_COL_3, category);
        contentValues.put(TABLE_5_COL_4, output_id);
        long result = db.insert(TABLE_NAME_5, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * returning data of particluar category
     *
     * @param categoryName
     * @return
     */
    public Cursor getAllDataOfCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_5 + " where category = '" + categoryName + "'", null);
        return res;
    }

    /**
     * get all category
     *
     * @return
     */
    public Cursor getAllMenuDataTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_5, null);
        return res;
    }

    public void trancateCategoryTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_5);
    }

//---------------------------------------------------------------------------------------------

    /*
     * inserting menu item in gridMenuTable
     */
    public boolean insertGridMenuTable(String m_id, String m_name, String barcode, String alias,
                                       String item_rate1, String item_rate2, String item_rate3, String rate_Taxsgst,
                                       String rate_Taxcgst, String rate_Taxigst, String vat, String group_name, String category,
                                       String serve_unit, String department, String outlet_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_6_COL_1, m_id);
        contentValues.put(TABLE_6_COL_2, m_name);
        contentValues.put(TABLE_6_COL_3, barcode);
        contentValues.put(TABLE_6_COL_4, alias);
        contentValues.put(TABLE_6_COL_5, item_rate1);
        contentValues.put(TABLE_6_COL_6, item_rate2);
        contentValues.put(TABLE_6_COL_7, item_rate3);
        contentValues.put(TABLE_6_COL_8, rate_Taxsgst);
        contentValues.put(TABLE_6_COL_9, rate_Taxcgst);
        contentValues.put(TABLE_6_COL_10, rate_Taxigst);
        contentValues.put(TABLE_6_COL_11, vat);
        contentValues.put(TABLE_6_COL_12, group_name);
        contentValues.put(TABLE_6_COL_13, category);
        contentValues.put(TABLE_6_COL_14, serve_unit);
        contentValues.put(TABLE_6_COL_15, department);
        contentValues.put(TABLE_6_COL_16, outlet_id);
        long result = db.insert(TABLE_NAME_6, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllGridDataTable(String group_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_6 + " where group_name = '" + group_name + "'", null);
        return res;
    }

    public Cursor getFoodInformation(String m_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_6 + " where m_id = " + m_id, null);
        return res;
    }


    public Cursor getSearchMenuResult(String newText) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_6 + " where m_name like '" + "%" + newText + "%" + "'", null);
        return res;
    }

    public void trancateGridMenuTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_6);
    }

    //-------------------------------------------------------------------------------------

    public boolean insertCardOrderItem(String item_name, int item_rate, int item_quantity, int item_total_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_7_COL_1, item_name);
        contentValues.put(TABLE_7_COL_2, item_rate);
        contentValues.put(TABLE_7_COL_3, item_quantity);
        contentValues.put(TABLE_7_COL_4, item_total_price);
        long result = db.insert(TABLE_NAME_7, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllCardOrderList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_7, null);
        return res;
    }

    public void updateCardOrderList(String item_name, int item_rate, int item_quantity, int item_total_price) {
        SQLiteDatabase db = this.getWritableDatabase();

    }

    //-------------------------------------------------------------------------------------

    /*
    * inserting search item
    */

    public boolean insertSearchMenu(String m_id, String m_name, String barcode, String alias,
                                    String item_rate1, String item_rate2, String item_rate3, String rate_Taxsgst,
                                    String rate_Taxcgst, String rate_Taxigst, String vat, String group_name, String category,
                                    String serve_unit, String department, String outlet_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_8_COL_1, m_id);
        contentValues.put(TABLE_8_COL_2, m_name);
        contentValues.put(TABLE_8_COL_3, barcode);
        contentValues.put(TABLE_8_COL_4, alias);
        contentValues.put(TABLE_8_COL_5, item_rate1);
        contentValues.put(TABLE_8_COL_6, item_rate2);
        contentValues.put(TABLE_8_COL_7, item_rate3);
        contentValues.put(TABLE_8_COL_8, rate_Taxsgst);
        contentValues.put(TABLE_8_COL_9, rate_Taxcgst);
        contentValues.put(TABLE_8_COL_10, rate_Taxigst);
        contentValues.put(TABLE_8_COL_11, vat);
        contentValues.put(TABLE_8_COL_12, group_name);
        contentValues.put(TABLE_8_COL_13, category);
        contentValues.put(TABLE_8_COL_14, serve_unit);
        contentValues.put(TABLE_8_COL_15, department);
        contentValues.put(TABLE_8_COL_16, outlet_id);
        long result = db.insert(TABLE_NAME_8, null, contentValues);
        if (result == -1)
            return false;
        else {
            Log.d(TAG, "insertSearchMenu: insersion sucessfull");
            return true;
        }
    }

    public Cursor getSearchMenu(String newText) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_8 + " where m_name like '" + "%" + newText + "%" + "'", null);
        return res;
    }

    public void trancateSearchMenuTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_8);
    }

    public Cursor getAllSearchMenuData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_8, null);
        return res;
    }
}
