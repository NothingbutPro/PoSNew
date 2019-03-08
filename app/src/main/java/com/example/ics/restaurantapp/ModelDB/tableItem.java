package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by android on 5/31/2018.
 */

public class tableItem {
    private String t_id,t_name,din_area,alias,outlet_id,d_id,status,waiter_id,splitted;

    public tableItem() {
    }

    public tableItem(String t_id, String t_name, String din_area, String alias, String outlet_id, String d_id, String status, String waiter_id, String splitted) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.din_area = din_area;
        this.alias = alias;
        this.outlet_id = outlet_id;
        this.d_id = d_id;
        this.status = status;
        this.waiter_id = waiter_id;
        this.splitted = splitted;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getDin_area() {
        return din_area;
    }

    public void setDin_area(String din_area) {
        this.din_area = din_area;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(String waiter_id) {
        this.waiter_id = waiter_id;
    }

    public String getSplitted() {
        return splitted;
    }

    public void setSplitted(String splitted) {
        this.splitted = splitted;
    }
}
