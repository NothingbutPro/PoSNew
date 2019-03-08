package com.example.ics.restaurantapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ics on 8/17/2018.
 */

public class Data_Order {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Bill_No")
    @Expose
    private String billNo;
    @SerializedName("Customer_name")
    @Expose
    private String customerName;
    @SerializedName("Dinenig_Area")
    @Expose
    private String dinenigArea;
    @SerializedName("Table_No")
    @Expose
    private String tableNo;
    @SerializedName("Waiter_Name")
    @Expose
    private String waiterName;
    @SerializedName("Date_Time")
    @Expose
    private String dateTime;
    @SerializedName("Beofer_tax_amount")
    @Expose
    private String beoferTaxAmount;
    @SerializedName("Tax_amount")
    @Expose
    private String taxAmount;
    @SerializedName("SGST")
    @Expose
    private String sGST;
    @SerializedName("CGST")
    @Expose
    private String cGST;
    @SerializedName("Total_Amount")
    @Expose
    private String totalAmount;
    @SerializedName("Discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("Round_off_amount")
    @Expose
    private String roundOffAmount;
    @SerializedName("Payment_method")
    @Expose
    private String paymentMethod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDinenigArea() {
        return dinenigArea;
    }

    public void setDinenigArea(String dinenigArea) {
        this.dinenigArea = dinenigArea;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBeoferTaxAmount() {
        return beoferTaxAmount;
    }

    public void setBeoferTaxAmount(String beoferTaxAmount) {
        this.beoferTaxAmount = beoferTaxAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getSGST() {
        return sGST;
    }

    public void setSGST(String sGST) {
        this.sGST = sGST;
    }

    public String getCGST() {
        return cGST;
    }

    public void setCGST(String cGST) {
        this.cGST = cGST;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getRoundOffAmount() {
        return roundOffAmount;
    }

    public void setRoundOffAmount(String roundOffAmount) {
        this.roundOffAmount = roundOffAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
