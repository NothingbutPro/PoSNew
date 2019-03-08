package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by android on 5/31/2018.
 */

public class orderListItem {
    private String order_for, receipt_no, dining_area, date_time, guest_name,waiter_name;
    private int order_no, amount, due;

    public orderListItem() {
    }

    public orderListItem(int order_no, String receipt_no,String order_for, String dining_area, String date_time, String guest_name, int amount,String waiter_name ) {
        this.order_for = order_for;
        this.dining_area = dining_area;
        this.date_time = date_time;
        this.guest_name = guest_name;
        this.order_no = order_no;
        this.receipt_no = receipt_no;
        this.amount = amount;
        this.waiter_name = waiter_name;
    }

    public String getOrder_for() {
        return order_for;
    }

    public void setOrder_for(String order_for) {
        this.order_for = order_for;
    }

    public String getDining_area() {
        return dining_area;
    }

    public void setDining_area(String dining_area) {
        this.dining_area = dining_area;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String  getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
    }
}

