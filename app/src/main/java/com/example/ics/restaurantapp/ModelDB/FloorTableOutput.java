package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by ICS on 25/05/2018.
 */

public class FloorTableOutput {
    private String t_id,t_name,din_area,alias,output_id,d_id;

    public FloorTableOutput() {
    }

    public FloorTableOutput(String t_id, String t_name, String din_area, String alias, String output_id, String d_id) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.din_area = din_area;
        this.alias = alias;
        this.output_id = output_id;
        this.d_id = d_id;
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

    public String getOutput_id() {
        return output_id;
    }

    public void setOutput_id(String output_id) {
        this.output_id = output_id;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }
}
