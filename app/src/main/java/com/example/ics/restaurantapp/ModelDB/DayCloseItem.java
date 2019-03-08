package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by sagar on 23/7/18.
 */

public class DayCloseItem {
    private  String orderFor,dineArea,dateTime,guestName,status,orderBy;
    private int orderNo, amount,due;

    public DayCloseItem() {
    }

    public DayCloseItem(int orderNo, String orderFor, String dineArea, String dateTime, String guestName, String status, String orderBy, int amount, int due) {
        this.orderNo = orderNo;
        this.orderFor = orderFor;
        this.dineArea = dineArea;
        this.dateTime = dateTime;
        this.guestName = guestName;
        this.status = status;
        this.orderBy = orderBy;
        this.amount = amount;
        this.due = due;
    }

    public String getOrderFor() {
        return orderFor;
    }

    public void setOrderFor(String orderFor) {
        this.orderFor = orderFor;
    }

    public String getDineArea() {
        return dineArea;
    }

    public void setDineArea(String dineArea) {
        this.dineArea = dineArea;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
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
