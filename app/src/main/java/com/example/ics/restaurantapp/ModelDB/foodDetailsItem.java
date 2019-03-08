package com.example.ics.restaurantapp.ModelDB;

/**
 * Created by ICS on 28/05/2018.
 */

public class foodDetailsItem {
    private String m_id, m_name, barcode, alias, item_rate1, item_rate2, item_rate3, rate_Taxsgst, rate_Taxcgst, rate_Taxigst, vat, group, category, serve_unit, department, outlet_id;
    private int quantity,totalPrice;


    public foodDetailsItem() {
    }

    public foodDetailsItem(String m_id, String m_name, String barcode, String alias, String item_rate1, String item_rate2, String item_rate3, String rate_Taxsgst, String rate_Taxcgst, String rate_Taxigst, String vat, String group, String category, String serve_unit, String department, String outlet_id, int quantity, int totalPrice) {
        this.m_id = m_id;
        this.m_name = m_name;
        this.barcode = barcode;
        this.alias = alias;
        this.item_rate1 = item_rate1;
        this.item_rate2 = item_rate2;
        this.item_rate3 = item_rate3;
        this.rate_Taxsgst = rate_Taxsgst;
        this.rate_Taxcgst = rate_Taxcgst;
        this.rate_Taxigst = rate_Taxigst;
        this.vat = vat;
        this.group = group;
        this.category = category;
        this.serve_unit = serve_unit;
        this.department = department;
        this.outlet_id = outlet_id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getItem_rate1() {
        return item_rate1;
    }

    public void setItem_rate1(String item_rate1) {
        this.item_rate1 = item_rate1;
    }

    public String getItem_rate2() {
        return item_rate2;
    }

    public void setItem_rate2(String item_rate2) {
        this.item_rate2 = item_rate2;
    }

    public String getItem_rate3() {
        return item_rate3;
    }

    public void setItem_rate3(String item_rate3) {
        this.item_rate3 = item_rate3;
    }

    public String getRate_Taxsgst() {
        return rate_Taxsgst;
    }

    public void setRate_Taxsgst(String rate_Taxsgst) {
        this.rate_Taxsgst = rate_Taxsgst;
    }

    public String getRate_Taxcgst() {
        return rate_Taxcgst;
    }

    public void setRate_Taxcgst(String rate_Taxcgst) {
        this.rate_Taxcgst = rate_Taxcgst;
    }

    public String getRate_Taxigst() {
        return rate_Taxigst;
    }

    public void setRate_Taxigst(String rate_Taxigst) {
        this.rate_Taxigst = rate_Taxigst;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServe_unit() {
        return serve_unit;
    }

    public void setServe_unit(String serve_unit) {
        this.serve_unit = serve_unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
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
}