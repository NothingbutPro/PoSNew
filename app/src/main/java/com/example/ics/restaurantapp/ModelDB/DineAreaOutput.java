package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by ICS on 25/05/2018.
 */

public class DineAreaOutput {
    private String da_id,da_name,outlet_id;

    public DineAreaOutput() {
    }

    public DineAreaOutput(String da_id, String da_name, String outlet_id) {
        this.da_id = da_id;
        this.da_name = da_name;
        this.outlet_id = outlet_id;
    }

    public String getDa_id() {
        return da_id;
    }

    public void setDa_id(String da_id) {
        this.da_id = da_id;
    }

    public String getDa_name() {
        return da_name;
    }

    public void setDa_name(String da_name) {
        this.da_name = da_name;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }
}
