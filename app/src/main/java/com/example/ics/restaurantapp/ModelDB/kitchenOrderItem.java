package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by android on 5/29/2018.
 */

public class kitchenOrderItem {
    private String mid,mname;
    private int serial,rate,quantity,totalPrice,SgstTax,CgstTax,IgstTax,totalSgstTax,totalCgstTax,totalIgstTax,Vattax,totalVatTax;

    public kitchenOrderItem() {
    }

    public kitchenOrderItem(String mid, String mname, int serial, int rate, int quantity, int totalPrice, int sgstTax, int cgstTax, int igstTax, int totalSgstTax, int totalCgstTax, int totalIgstTax, int vattax, int totalVatTax) {
        this.mid = mid;
        this.mname = mname;
        this.serial = serial;
        this.rate = rate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        SgstTax = sgstTax;
        CgstTax = cgstTax;
        IgstTax = igstTax;
        this.totalSgstTax = totalSgstTax;
        this.totalCgstTax = totalCgstTax;
        this.totalIgstTax = totalIgstTax;
        Vattax = vattax;
        this.totalVatTax = totalVatTax;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSgstTax() {
        return SgstTax;
    }

    public void setSgstTax(int sgstTax) {
        SgstTax = sgstTax;
    }

    public int getCgstTax() {
        return CgstTax;
    }

    public void setCgstTax(int cgstTax) {
        CgstTax = cgstTax;
    }

    public int getIgstTax() {
        return IgstTax;
    }

    public void setIgstTax(int igstTax) {
        IgstTax = igstTax;
    }

    public int getTotalSgstTax() {
        return totalSgstTax;
    }

    public void setTotalSgstTax(int totalSgstTax) {
        this.totalSgstTax = totalSgstTax;
    }

    public int getTotalCgstTax() {
        return totalCgstTax;
    }

    public void setTotalCgstTax(int totalCgstTax) {
        this.totalCgstTax = totalCgstTax;
    }

    public int getTotalIgstTax() {
        return totalIgstTax;
    }

    public void setTotalIgstTax(int totalIgstTax) {
        this.totalIgstTax = totalIgstTax;
    }

    public int getVattax() {
        return Vattax;
    }

    public void setVattax(int vattax) {
        Vattax = vattax;
    }

    public int getTotalVatTax() {
        return totalVatTax;
    }

    public void setTotalVatTax(int totalVatTax) {
        this.totalVatTax = totalVatTax;
    }
}
