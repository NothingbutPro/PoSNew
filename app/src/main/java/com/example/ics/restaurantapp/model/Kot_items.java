package com.example.ics.restaurantapp.model;

public class Kot_items {
    String item_name ;
    int item_qut;

    public Kot_items(String item_name, int item_qut) {
        this.item_name = item_name;
        this.item_qut = item_qut;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_qut() {
        return item_qut;
    }

    public void setItem_qut(int item_qut) {
        this.item_qut = item_qut;
    }
}
