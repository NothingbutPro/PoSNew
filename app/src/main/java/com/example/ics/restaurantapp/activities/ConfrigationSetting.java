package com.example.ics.restaurantapp.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ConfrigationSetting extends AppCompatActivity {

    Button btnConnect,btnDisconnect;

    public static final int BLUETOOTH_REQUEST = 0;
    public static final String MY_BLUETOOTH_NAME = "";
    //standard uuid from string
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;



    InputStream inputStream;
    OutputStream outputStream;
    Thread thread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;


    public String BILL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrigation_setting);

        BILL = "                      RESTAURANT    \n"
                + "                   XX.AA.BB.CC.     \n " +
                "                 NO 25 ABC ABCDE    \n" +
                "                  XXXXX YYYYYY      \n" +
                "                   MMM 590019091      \n"
                + "-----------------------------------------------\n"
        + String.format("%1$-10s %2$10s %3$13s %4$10s", "Item", "Qty", "Rate", "Total")
                + "\n" + "-----------------------------------------------"
                + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-001", "5", "10", "50.00")
                + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-002", "10", "5", "50.00")
                + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-003", "20", "10", "200.00")
                + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-004", "50", "10", "500.00")
                + "\n-----------------------------------------------"
                + "\n\n "
                + "                   Total Qty:" + "      " + "85" + "\n"
                + "                   Total Value:" + "     " + "700.00" + "\n"
                + "-----------------------------------------------\n"
                + "\n\n ";

        btnConnect = (Button)findViewById(R.id.btnConnect);
        btnDisconnect = (Button)findViewById(R.id.btnDisconnect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    findBluetoothDevice();
                    openBluetoothDevice();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    disconnectBT();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void printBill(){
        try{
            printData(BILL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void findBluetoothDevice(){
        try{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter == null){
//                lblPrinterName.setText("No bluetooth adapter found");
                Toast.makeText(ConfrigationSetting.this, "No bluetooth adapter found", Toast.LENGTH_SHORT).show();
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,BLUETOOTH_REQUEST);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev: pairedDevice){
                    if(pairedDev.getName().equals(MY_BLUETOOTH_NAME)){
                        bluetoothDevice  = pairedDev;
//                        lblPrinterName.setText("Buetooth is conncted : " +pairedDev.getName());
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //open bluetooth
    public void openBluetoothDevice() throws IOException{
        try{
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            beginListenData();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void beginListenData() {
        try {
            final Handler handler = new Handler();
            final byte delimiter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if (byteAvailable>0){
                                byte[] packageByte = new byte[byteAvailable];
                                inputStream.read(packageByte);

                                for (int i=0;i<byteAvailable;i++){
                                    byte b = packageByte[i];
                                    if (b == delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );

                                        final String data = new String (encodedByte,"US-ASCII");
                                        readBufferPosition =0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ConfrigationSetting.this, data, Toast.LENGTH_SHORT).show();;
                                            }
                                        });
                                    }else {
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }

                            }
                        }catch (Exception ex){
                            stopWorker = true;
                        }
                    }
                }
            });

            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //printing text from bluetooth
    public void printData(String msg) throws IOException {
        try {
//            String msg = editText.getText().toString();
//            msg+='\n';
            outputStream.write(msg.getBytes());
//            lblPrinterName.setText("Printing Text....");
            Toast.makeText(ConfrigationSetting.this, "Printing Text....", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //disconnect Bluetooth
    public void disconnectBT() throws  IOException{
        try{
            inputStream.close();
            outputStream.close();;
            bluetoothSocket.connect();
            Toast.makeText(ConfrigationSetting.this, "Disconnected", Toast.LENGTH_SHORT).show();
            stopWorker = true;
        }catch (Exception e){

        }
    }
}
