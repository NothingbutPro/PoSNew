package com.example.ics.restaurantapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.activities.DeviceListActivity;
import com.hoin.btsdk.BluetoothService;
import com.hoin.btsdk.PrintPic;


public class BlueToothFragment extends Fragment {
    Button btnSearch;
    Button btnSendDraw;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    EditText edtPrint;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private View qrCodeBtnSend;
    private static final int REQUEST_CONNECT_DEVICE = 1;  //????????
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue_tooth, null);
        btnSendDraw = (Button)view.findViewById(R.id.btn_test);
        btnSendDraw.setOnClickListener(new ClickEvent());
        btnSearch = (Button)view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new ClickEvent());
        btnSend = (Button)view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new ClickEvent());
        qrCodeBtnSend = (Button)view.findViewById(R.id.qr_code_Send);
        qrCodeBtnSend.setOnClickListener(new ClickEvent());
        btnClose = (Button)view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new ClickEvent());
        edtContext = (EditText)view.findViewById(R.id.txt_content);
        btnClose.setEnabled(false);
        btnSend.setEnabled(false);
        qrCodeBtnSend.setEnabled(false);
        btnSendDraw.setEnabled(false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = new BluetoothService(getActivity(), mHandler);
        //?????????????????
        if( mService.isAvailable() == false ){
            Toast.makeText(getActivity(), "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if( mService.isBTopen() == false){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            String msg = "";
            switch (v.getId()) {
                case R.id.btn_test:
                    String lang = getString(R.string.bluetooth_strLang);
                    printImage();

                    byte[] cmd = new byte[3];
                    cmd[0] = 0x1b;
                    cmd[1] = 0x21;
                    if((lang.compareTo("en")) == 0){
                        cmd[2] |= 0x10;
                        mService.write(cmd);           //??????????
                        mService.sendMessage("Restaurant!\n", "GBK");
                        cmd[2] &= 0xEF;
                        mService.write(cmd);           //??????????????
                        msg = "                      RESTAURANT    \n"
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
                                + "\n\n";
                        mService.sendMessage(msg,"GBK");
                    }else if((lang.compareTo("ch")) == 0){
                        cmd[2] |= 0x10;
                        mService.write(cmd);           //??????????
                        mService.sendMessage("???????\n", "GBK");
                        cmd[2] &= 0xEF;
                        mService.write(cmd);           //??????????????
                        msg = "  ??????????????????????????????????\n\n"
                                + "  ??????????????????????????????????????????????????????????????????.\n\n";
                        mService.sendMessage(msg,"GBK");
                    }
                    break;
                case R.id.btnSearch:
                    Intent serverIntent = new Intent(getActivity(),DeviceListActivity.class);      //???????????????
                    startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
                    break;
                case R.id.btnSend:
                    msg = edtContext.getText().toString();
                    if( msg.length() > 0 ){
                        mService.sendMessage(msg, "GBK");
                    }
                    break;
                case R.id.qr_code_Send:
                    cmd = new byte[7];
                    cmd[0] = 0x1B;
                    cmd[1] = 0x5A;
                    cmd[2] = 0x00;
                    cmd[3] = 0x02;
                    cmd[4] = 0x07;
                    cmd[5] = 0x17;
                    cmd[6] = 0x00;
                    msg = getResources().getString(R.string.bluetooth_qr_code_Send_string);
                    if( msg.length() > 0){
                        mService.write(cmd);
                        mService.sendMessage(msg, "GBK");
                    }
                    break;
                case R.id.btnClose:
                    mService.stop();
                    break;
            }
        }
    }

    /**
     * ???????Handler????????????BluetoothService????????????
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //??????
                            Toast.makeText(getActivity(), "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            btnClose.setEnabled(true);
                            btnSend.setEnabled(true);
                            qrCodeBtnSend.setEnabled(true);
                            btnSendDraw.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:  //????????
                            Log.d("????????","????????.....");
                            break;
                        case BluetoothService.STATE_LISTEN:     //????????????
                        case BluetoothService.STATE_NONE:
                            Log.d("????????","???????.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //????????????
//                    Toast.makeText(getActivity(), "Device connection was lost",
//                            Toast.LENGTH_SHORT).show();
                    btnClose.setEnabled(false);
                    btnSend.setEnabled(false);
                    qrCodeBtnSend.setEnabled(false);
                    btnSendDraw.setEnabled(false);
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //?????????
                   // Toast.makeText(getActivity(), "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:      //?????????
                if (resultCode == Activity.RESULT_OK) {   //?????????
                    Toast.makeText(getActivity(), "Bluetooth open successful", Toast.LENGTH_LONG).show();
                }
                break;
            case  REQUEST_CONNECT_DEVICE:     //????????????????
                if (resultCode == Activity.RESULT_OK) {   //?????????????????????
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //??????????????mac???
                    con_dev = mService.getDevByMac(address);

                    mService.connect(con_dev);
                }
                break;
        }
    }

    //??????
    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(576);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        //
        sendData = pg.printDraw();
        mService.write(sendData);   //???byte??????
        Log.d("????????",""+sendData.length);
    }
}
