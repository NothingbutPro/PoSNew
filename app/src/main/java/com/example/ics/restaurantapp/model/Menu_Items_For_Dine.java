package com.example.ics.restaurantapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import static com.esotericsoftware.minlog.Log.trace;

/**
 * Created by Ics on 8/15/2018.
 */

public class Menu_Items_For_Dine {
    private String Menu_Name;
    private String Qty;
    private String Rate;
    private String Tax;
    private String Amount;

    public Menu_Items_For_Dine(String menu_Name, String qty, String rate, String tax, String amount) {
        Menu_Name = menu_Name;
        Qty = qty;
        Rate = rate;
        Tax = tax;
        Amount = amount;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Menu_Name", Menu_Name);
            obj.put("Qty", Qty);
            obj.put("Rate", Rate);
            obj.put("Tax", Tax);
            obj.put("Amount", Amount);
        } catch (JSONException e) {
            trace("DefaultListItem.toString JSONException: " + e.getMessage());
        }
        return obj;
    }

    public String getMenu_Name() {
        return Menu_Name;
    }

    public void setMenu_Name(String menu_Name) {
        Menu_Name = menu_Name;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
