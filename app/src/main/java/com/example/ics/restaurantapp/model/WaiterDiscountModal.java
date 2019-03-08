package com.example.ics.restaurantapp.model;

/**
 * Created by Ics on 9/17/2018.
 */

public class WaiterDiscountModal {
    private String strWaiterId;
    private String strWaiterNam;
    private String strTableN;
    private String strDiscoun;

    public WaiterDiscountModal(String strWaiterId, String strWaiterNam, String strTableN, String strDiscoun) {
        this.strWaiterId = strWaiterId;
        this.strWaiterNam = strWaiterNam;
        this.strTableN = strTableN;
        this.strDiscoun = strDiscoun;
    }

    public String getStrWaiterId() {
        return strWaiterId;
    }

    public void setStrWaiterId(String strWaiterId) {
        this.strWaiterId = strWaiterId;
    }

    public String getStrWaiterNam() {
        return strWaiterNam;
    }

    public void setStrWaiterNam(String strWaiterNam) {
        this.strWaiterNam = strWaiterNam;
    }

    public String getStrTableN() {
        return strTableN;
    }

    public void setStrTableN(String strTableN) {
        this.strTableN = strTableN;
    }

    public String getStrDiscoun() {
        return strDiscoun;
    }

    public void setStrDiscoun(String strDiscoun) {
        this.strDiscoun = strDiscoun;
    }
}
