package com.example.ics.restaurantapp.model;

public class Kot_Items_Bill_Node  {
    String item_node;
    int item_qtynode;
    Kot_Items_Bill_Node billNode;

    public Kot_Items_Bill_Node(String item_node, int item_qtynode) {
        this.item_node = item_node;
        this.item_qtynode = item_qtynode;
        billNode = null;
    }

    public String getItem_node() {
        return item_node;
    }

    public void setItem_node(String item_node) {
        this.item_node = item_node;
    }

    public int getitem_qtynode() {
        return item_qtynode;
    }

    public void setitem_qtynode(int item_qtynode) {
        this.item_qtynode = item_qtynode;
    }
}
