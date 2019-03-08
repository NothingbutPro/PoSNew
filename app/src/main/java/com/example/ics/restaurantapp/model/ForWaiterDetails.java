package com.example.ics.restaurantapp.model;

public class ForWaiterDetails {

    private String waiterId;
    private String waiterName;
    private String discount;

    public ForWaiterDetails(String waiterId, String waiterName, String discount) {
        this.waiterId = waiterId;
        this.waiterName = waiterName;
        this.discount = discount;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getDiscountAmo() {
        return discount;
    }

    public void setDiscountAmo(String discount) {
        this.discount = discount;
    }
}
