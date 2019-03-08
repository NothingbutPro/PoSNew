package com.example.ics.restaurantapp.ModelDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sagar on 26/6/18.
 */
public class CustomerItem implements Parcelable {
    private String mobile_number,customer_name,customer_address;

    public CustomerItem() {
    }

    public CustomerItem(String customer_name, String mobile_number, String customer_address) {
        this.mobile_number = mobile_number;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
    }

    protected CustomerItem(Parcel in) {
        mobile_number = in.readString();
        customer_name = in.readString();
        customer_address = in.readString();
    }

    public static final Creator<CustomerItem> CREATOR = new Creator<CustomerItem>() {
        @Override
        public CustomerItem createFromParcel(Parcel in) {
            return new CustomerItem(in);
        }

        @Override
        public CustomerItem[] newArray(int size) {
            return new CustomerItem[size];
        }
    };

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile_number);
        dest.writeString(customer_name);
        dest.writeString(customer_address);
    }
}

