package com.example.ics.restaurantapp.receiver;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hoin.btsdk.BluetoothService;



public class BlueServiceFree extends Service {
    static BluetoothService  mbService = null;
    BluetoothDevice con_dev = null;
    private BluetoothService monfreeService = null;

    public BlueServiceFree() {
        super();
    }

    @Override
    public void onCreate() {
        Log.e("service" , "blueser created");
        monfreeService = new BluetoothService(this, mHandler);
        if (monfreeService.isAvailable() == false) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
        String address = sharedPreferences.getString("device", "");
        if (address == null || address.equals("")) {

        } else {
            con_dev = monfreeService.getDevByMac(address);
//            try {
//                createBluetoothSocket(con_dev);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            monfreeService.connect(con_dev);

        }
        super.onCreate();
    }
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //??????
                            Toast.makeText(getApplicationContext(), "Connect successful", Toast.LENGTH_SHORT).show();

                            break;
                        case BluetoothService.STATE_CONNECTING:  //????????
                            Log.d("????????","????????.....");
                            break;
                        case BluetoothService.STATE_LISTEN:
                            stopSelf();
                            //????????????
                        case BluetoothService.STATE_NONE:
                            stopSelf();
                            Log.d("????????","???????.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //????????????

//                    Toast.makeText(getActivity(), "Device connection was lost",
//                            Toast.LENGTH_SHORT).show();

                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //?????????
                    // Toast.makeText(getActivity(), "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        monfreeService = new BluetoothService(this, mHandler);
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
