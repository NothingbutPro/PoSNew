package com.example.ics.restaurantapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ics on 8/18/2018.
 */

public class MenuItem_Response {
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private ArrayList<Menu_Detail> message = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<Menu_Detail> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Menu_Detail> message) {
        this.message = message;
    }

}