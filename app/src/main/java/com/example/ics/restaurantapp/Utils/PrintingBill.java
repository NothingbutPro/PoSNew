//package com.example.ics.restaurantapp.Utils;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.ics.restaurantapp.R;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Set;
//import java.util.UUID;
//
///**
// * Created by android on 6/14/2018.
// */
//
//public class PrintingBill {
//    Button btnPrint,btnDisconnect,btnConnect;
//    EditText editText;
//    TextView lblPrinterName;
//
//    public static final int BLUETOOTH_REQUEST = 0;
//    public static final String MY_BLUETOOTH_NAME = "";
//    //standard uuid from string
//    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//
//    BluetoothAdapter bluetoothAdapter;
//    BluetoothDevice bluetoothDevice;
//    BluetoothSocket bluetoothSocket;
//
//    Context context;
//
//
//
//    InputStream inputStream;
//    OutputStream outputStream;
//    Thread thread;
//
//    byte[] readBuffer;
//    int readBufferPostion;
//    volatile boolean stopWorker;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnPrint = (Button)findViewById(R.id.btn_print);
//        btnDisconnect = (Button)findViewById(R.id.btn_disconnect);
//        btnConnect = (Button)findViewById(R.id.btn_connect);
//
//        editText = (EditText)findViewById(R.id.txt_msg);
//
//        lblPrinterName = (TextView)findViewById(R.id.lblPrinterNAme);
//
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    findBluetoothDevice();
//                    openBluetoothDevice();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        btnDisconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    disconnectBT();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        btnPrint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    printData();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
//
//    public void printBill(){
//        try{
//            printData();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void disconnectBluetooth(){
//        try{
//            disconnectBT();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void connectBluetooth(){
//        try {
//            findBluetoothDevice();
//            openBluetoothDevice();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void findBluetoothDevice(){
//        try{
//            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            if(bluetoothAdapter == null){
//                lblPrinterName.setText("No bluetooth adapter found");
//            }
//            if(bluetoothAdapter.isEnabled()){
//                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                context.startActivityForResult(enableBT,BLUETOOTH_REQUEST);
//            }
//
//            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
//
//            if(pairedDevice.size()>0){
//                for(BluetoothDevice pairedDev: pairedDevice){
//                    if(pairedDev.getName().equals(MY_BLUETOOTH_NAME)){
//                        bluetoothDevice  = pairedDev;
//                        lblPrinterName.setText("Buetooth is conncted : " +pairedDev.getName());
//                        break;
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    //open bluetooth
//    public void openBluetoothDevice() throws IOException{
//        try{
//            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
//            bluetoothSocket.connect();
//            outputStream = bluetoothSocket.getOutputStream();
//            inputStream = bluetoothSocket.getInputStream();
//
//            beginListenData();
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    private void beginListenData() {
//        try {
//            final Handler handler = new Handler();
//            final byte delimiter = 10;
//            stopWorker = false;
//            readBufferPostion = 0;
//            readBuffer = new byte[1024];
//
//            thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while(!Thread.currentThread().isInterrupted() && !stopWorker){
//                        try{
//                            int byteAvailable = inputStream.available();
//                            if (byteAvailable>0){
//                                byte[] packageByte = new byte[byteAvailable];
//                                inputStream.read(packageByte);
//
//                                for (int i=0;i<byteAvailable;i++){
//                                    byte b = packageByte[i];
//                                    if (b == delimiter){
//                                        byte[] encodedByte = new byte[readBufferPostion];
//                                        System.arraycopy(
//                                                readBuffer,0,
//                                                encodedByte,0,
//                                                encodedByte.length
//                                        );
//
//                                        final String data = new String (encodedByte,"US-ASCII");
//                                        readBufferPostion =0;
//                                        handler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                lblPrinterName.setText(data);
//                                            }
//                                        });
//                                    }else {
//                                        readBuffer[readBufferPostion++]=b;
//                                    }
//                                }
//
//                            }
//                        }catch (Exception ex){
//                            stopWorker = true;
//                        }
//                    }
//                }
//            });
//
//            thread.start();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    //printing text from bluetooth
//    public void printData() throws IOException {
//        try {
//            String msg = editText.getText().toString();
//            msg+='\n';
//            outputStream.write(msg.getBytes());
//            lblPrinterName.setText("Printing Text....");
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    //disconnect Bluetooth
//    public void disconnectBT() throws  IOException{
//        try{
//            inputStream.close();
//            outputStream.close();;
//            bluetoothSocket.connect();;
//            lblPrinterName.setText("Disconnected");
//            stopWorker = true;
//        }catch (Exception e){
//
//        }
//    }
//}
