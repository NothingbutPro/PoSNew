package com.example.ics.restaurantapp.model;

/**
 * Created by ICS on 20/03/2018.
 */

public class Model_Item {
    public int productImage;
    public String productName;

    public Model_Item(String productName, int productImage) {
        this.productImage = productImage;
        this.productName = productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
