package com.example.ics.restaurantapp.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Admin on 17-10-2015.
 */
public class SessionManager {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String MyPREFERENCES = "MyPrefss";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "userName";



    public static final String KEY_USER_AREA = "UserArea";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_STATE = "state";
    public static final String KEY_OP_BAL = "opening_bal";
    public static final String KEY_TYPE = "type";
    private static final String IS_SKIPPED = "IsSlipped";
    private static final String WAITER_NAME = "waiter_name";
    private static final String WAITER_ID = "waiter_id";
    private static final String KEY_DISCOUNT = "discount";
    private static final String KEY_BILL_TOKEN = "billstoken";
    private static final String KEY_BILL_RES_NAME = "res_name";
    private static final String KEY_BILL_RES_TYPE = "res_type";
    private static final String KEY_BILL_RES_TYPE2 = "res_type2";

    public  String getKeyBillResType2() {
        return  pref.getString(KEY_BILL_RES_TYPE2, null);
    }

    public  String getKeyBillResName() {
        return  pref.getString(KEY_BILL_RES_NAME, null);
    }

    public  String getKeyBillResType() {
        return  pref.getString(KEY_BILL_RES_TYPE, null);
    }
    public void setKeyBillResName(String us_name) {
        editor.putString(KEY_BILL_RES_NAME, us_name);
        editor.commit();
    }
    public void setKeyBillResType(String us_tyype) {
        editor.putString(KEY_BILL_RES_TYPE, us_tyype);
        editor.commit();
    }
    public void setKeyBillResTyp2e(String us_tyype2) {
        editor.putString(KEY_BILL_RES_TYPE2, us_tyype2);
        editor.commit();
    }
    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = pref.edit();
        editor = pref.edit();

    }
    public  Boolean getKeyBillToken() {
        return  pref.getBoolean(KEY_BILL_TOKEN, false);
    }

    public  String getKeyUserArea() {

        return pref.getString(KEY_USER_AREA, null);
    }

    public void waiterName(String strName) {
        editor.putString(WAITER_NAME, strName);
        editor.commit();
    }
    public void setKeyUserArea(String us_area) {
        editor.putString(KEY_USER_AREA, us_area);
        editor.commit();
    }
    public void setKey_Bill_Token(Boolean token_area) {
        editor.putBoolean(KEY_BILL_TOKEN, token_area);
        editor.commit();
    }

    public void serverLogin(String strName, String strType, String strState, String strOPBal) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, strName);
        editor.putString(KEY_TYPE, strType);
        editor.putString(KEY_STATE, strState);
        editor.putString(KEY_OP_BAL, strOPBal);
        editor.commit();
    }

    public void setWaiterDetails(String strName, String strId) {
        editor.putString(WAITER_NAME, strName);
        editor.putString(WAITER_ID, strId);
        editor.commit();
    }

    public void setDiscount(String disAmo) {
        editor.putString(KEY_DISCOUNT, disAmo);
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
    }

    public void setSkipped(boolean isLoggedIn) {
        editor.putBoolean(IS_SKIPPED, isLoggedIn);
        editor.commit();
    }

    // Get Skipped State
    public boolean isSkipped() {
        return pref.getBoolean(IS_SKIPPED, false);
    }

    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Clearing all data from Shared Preferences
    public void logoutUser() {
        editor.clear();
        editor.commit();

    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getMobile() {
        return pref.getString(KEY_MOBILE, null);
    }

    public String getState() {
        return pref.getString(KEY_STATE, null);
    }

    public String getOpBal() {
        return pref.getString(KEY_OP_BAL, null);
    }

    public String getType() {
        return pref.getString(KEY_TYPE, null);
    }

    public String getWaiterName() {
        return pref.getString(WAITER_NAME, null);
    }

    public String getWaiterId() {
        return pref.getString(WAITER_ID, null);
    }

    public String getDiscountAmo() {
        return pref.getString(KEY_DISCOUNT, null);
    }


}