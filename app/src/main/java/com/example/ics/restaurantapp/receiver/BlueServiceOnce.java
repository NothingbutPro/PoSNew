package com.example.ics.restaurantapp.receiver;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hoin.btsdk.BluetoothService;

public class BlueServiceOnce extends Service {
   public static BluetoothService  monceService = null;
    BluetoothDevice con_dev = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("service" , "BlueService created");
        monceService = new BluetoothService(this, mHandler);
        if (monceService.isAvailable() == false) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
        String address = sharedPreferences.getString("device", "");
        if (address == null || address.equals("")) {

        } else {
            con_dev = monceService.getDevByMac(address);
//            try {
//                createBluetoothSocket(con_dev);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            monceService.connect(con_dev);

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
                        case BluetoothService.STATE_LISTEN:     //????????????
//                            stopSelf();
                        case BluetoothService.STATE_NONE:
                            Log.d("????????","???????.....");
//                            stopSelf();
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
    public void onStart(Intent intent, int startId) {
        Log.e("service","Blueonce started");
      //  monceService = new BluetoothService(this, mHandler);

        super.onStart(intent, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("service","Blueonce task removed");
        stopService(new Intent(this , BlueServiceOnce.class));
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        monceService = new BluetoothService(this, mHandler);
//        startService(new Intent());
        Log.e("service","Blueonce destroy");
//        startService(new Intent(this , BlueServiceOnce.class));
        super.onDestroy();
    }
}
