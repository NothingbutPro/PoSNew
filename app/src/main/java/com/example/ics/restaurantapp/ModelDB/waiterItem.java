package com.example.ics.restaurantapp.ModelDB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ICS on 25/05/2018.
 */

public class waiterItem implements Parcelable{
    private String w_id,w_name,alias,waiter_id,outlet_id,discount_amo;

    public waiterItem() {
    }

    public waiterItem(String w_id, String w_name, String alias, String waiter_id, String outlet_id, String discount_amo) {
        this.w_id = w_id;
        this.w_name = w_name;
        this.alias = alias;
        this.waiter_id = waiter_id;
        this.outlet_id = outlet_id;
        this.discount_amo = discount_amo;
    }

    protected waiterItem(Parcel in) {
        w_id = in.readString();
        w_name = in.readString();
        alias = in.readString();
        waiter_id = in.readString();
        outlet_id = in.readString();
        discount_amo = in.readString();
    }

    public static final Creator<waiterItem> CREATOR = new Creator<waiterItem>() {
        @Override
        public waiterItem createFromParcel(Parcel in) {
            return new waiterItem(in);
        }

        @Override
        public waiterItem[] newArray(int size) {
            return new waiterItem[size];
        }
    };

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getW_name() {
        return w_name;
    }

    public void setW_name(String w_name) {
        this.w_name = w_name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(String waiter_id) {
        this.waiter_id = waiter_id;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getDiscount_amo() {
        return discount_amo;
    }

    public void setDiscount_amo(String discount_amo) {
        this.discount_amo = discount_amo;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(w_id);
        dest.writeString(w_name);
        dest.writeString(alias);
        dest.writeString(waiter_id);
        dest.writeString(outlet_id);
        dest.writeString(discount_amo);
    }
}
