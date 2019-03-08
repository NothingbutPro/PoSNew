package com.example.ics.restaurantapp.activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;

public class ConfigActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mUsbHeader;
    private TextView mBluetoothHeader;
    private TextView mWifiHeader;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private Fragment mUsbFragment;
    private Fragment mBlueToothFragment;
    private Fragment mWifiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        initViews();
    }

    private void initViews() {
        mUsbHeader = (TextView)findViewById(R.id.usb_header);
        mBluetoothHeader = (TextView)findViewById(R.id.bluetooth_header);
        mWifiHeader = (TextView)findViewById(R.id.wifi_header);
        mUsbHeader.setOnClickListener(this);
        mBluetoothHeader.setOnClickListener(this);
        mWifiHeader.setOnClickListener(this);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        mUsbFragment = new UsbFragment();
        mTransaction.replace(R.id.fragment_ll,mUsbFragment);
        mTransaction.commit();
        mUsbHeader.setBackgroundColor(Color.GREEN);
        mUsbHeader.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usb_header:
                mUsbHeader.setClickable(false);
                mBluetoothHeader.setClickable(true);
                mWifiHeader.setClickable(true);
                mUsbHeader.setBackgroundColor(Color.GREEN);
                mBluetoothHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mWifiHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mTransaction = mManager.beginTransaction();
                mUsbFragment = new UsbFragment();
                mTransaction.replace(R.id.fragment_ll,mUsbFragment);
                mTransaction.commit();
                break;
            case R.id.bluetooth_header:
                mUsbHeader.setClickable(true);
                mBluetoothHeader.setClickable(false);
                mWifiHeader.setClickable(true);
                mBluetoothHeader.setBackgroundColor(Color.GREEN);
                mUsbHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mWifiHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mTransaction = mManager.beginTransaction();
                mBlueToothFragment = new BlueToothFragment();
                mTransaction.replace(R.id.fragment_ll,mBlueToothFragment);
                mTransaction.commit();
                break;
            case R.id.wifi_header:
                mUsbHeader.setClickable(true);
                mBluetoothHeader.setClickable(true);
                mWifiHeader.setClickable(false);
                mWifiHeader.setBackgroundColor(Color.GREEN);
                mUsbHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mBluetoothHeader.setBackgroundColor(getResources().getColor(R.color.orange));
                mTransaction = mManager.beginTransaction();
                mWifiFragment = new WifiFragment();
                mTransaction.replace(R.id.fragment_ll,mWifiFragment);
                mTransaction.commit();
                break;
        }
    }
}
