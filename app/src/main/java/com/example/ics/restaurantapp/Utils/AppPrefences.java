package com.example.ics.restaurantapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ICS on 23/03/2018.
 */

public class AppPrefences {

    public static final String SHARED_PREFERENCE_NAME = "RESTAURANT";
    public static final String OUTLET = "Outlet";
    public static final String LOGOUT = "Logout";
    public static final String FLOOR = "Floor";
    public static final String GUEST = "Guest";
    public static final String OUTLET1 = "Outlet1";
    public static final String QUANTITY = "Quantity";
    public static final String TABLE = "Table";

    public static String getOutlet(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(OUTLET, "");
    }
    public static void setOutlet(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(OUTLET, value);
        editor.commit();
    }

    //------------------------------------------------------------------------

    public static String getOutlet1(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(OUTLET1, "");
    }
    public static void setOutlet1(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(OUTLET1, value);
        editor.commit();
    }

    //-------------------------------------------------------------------------
    //SharedPreferences preferences = .getSharedPreferences("pref", Context.MODE_PRIVATE);

    public static String getLogout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(LOGOUT, "");
    }
    public static void setLogout(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGOUT, value);
        editor.commit();
    }

    //---------------------------------------------------------------------------
    public static String getFloor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(FLOOR, "");
    }
    public static void setFloor(Context context,String value) {
        //AppPrefences.getAreaID = getAreaID;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FLOOR, value);
        editor.commit();
    }

    //-----------------------------------------------------------------------------

    public static String getGuest(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(GUEST, "");
    }
    public static void setGuest(Context context,String value) {
        //AppPrefences.getAreaID = getAreaID;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GUEST, value);
        editor.commit();
    }
//--------------------------------------------------------------------------

    public static String getQuantity(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(QUANTITY, "");
    }
    public static void setQuantity(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(QUANTITY, value);
        editor.commit();
    }

    //---------------------------------------------------------------------------

    public static String getTable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(TABLE, "");
    }
    public static void setTable(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TABLE, value);
        editor.commit();
    }

}
