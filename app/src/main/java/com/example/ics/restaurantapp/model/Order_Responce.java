package com.example.ics.restaurantapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ics on 8/17/2018.
 */

public class Order_Responce {
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private ArrayList<Message> message = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<Message> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Message> message) {
        this.message = message;
    }

}
