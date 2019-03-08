package com.example.ics.restaurantapp.activities;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;
import com.hoin.usbsdk.PrintPic;
import com.hoin.usbsdk.UsbController;


public class UsbFragment extends Fragment {
    private Button btn_conn = null;
    private Button btnSend = null;
    private Button qrCodeSend = null;
    private Button btn_test = null;
    private Button btnClose = null;
    private EditText txt_content = null;

    //private int VID = 0x1CBE;
    //private int PID = 0x0003;
    private int[][] u_infor;
    UsbController usbCtrl = null;
    UsbDevice dev = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usb, null);
        btn_conn = (Button)view.findViewById(R.id.btn_conn);
        btnSend = (Button)view.findViewById(R.id.btnSend);
        qrCodeSend = (Button)view.findViewById(R.id.qr_code_Send);
        btn_test = (Button)view.findViewById(R.id.btn_test);
        btnClose = (Button)view.findViewById(R.id.btnClose);
        txt_content = (EditText)view.findViewById(R.id.txt_content);
        txt_content.setSelection(txt_content.getText().toString().length());

        btn_conn.setOnClickListener(new ClickEvent());
        btnSend.setOnClickListener(new ClickEvent());
        qrCodeSend.setOnClickListener(new ClickEvent());
        btn_test.setOnClickListener(new ClickEvent());
        btnClose.setOnClickListener(new ClickEvent());

        btnSend.setEnabled(false);
        qrCodeSend.setEnabled(false);
        btn_test.setEnabled(false);
        btnClose.setEnabled(false);

        usbCtrl = new UsbController(getActivity(),mHandler);
        u_infor = new int[5][2];
        u_infor[0][0] = 0x1CBE;
        u_infor[0][1] = 0x0003;
        u_infor[1][0] = 0x1CB0;
        u_infor[1][1] = 0x0003;
        u_infor[2][0] = 0x0483;
        u_infor[2][1] = 0x5740;
        u_infor[3][0] = 0x0493;
        u_infor[3][1] = 0x8760;
        u_infor[4][0] = 0x0471;
        u_infor[4][1] = 0x0055;
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usbCtrl.close();
        usbCtrl = null;
    }

    //????????????usb???????
    public boolean CheckUsbPermission(){
        if( dev != null ){
            if( usbCtrl.isHasPermission(dev)){
                return true;
            }
        }
        btnSend.setEnabled(false);
        qrCodeSend.setEnabled(false);
        btn_test.setEnabled(false);
        btnClose.setEnabled(false);
        btn_conn.setEnabled(true);
        Toast.makeText(getActivity(), getString(R.string.usb_msg_conn_state),
                Toast.LENGTH_SHORT).show();
        return false;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbController.USB_CONNECTED:
                    Toast.makeText(getActivity(), getString(R.string.usb_msg_getpermission),
                            Toast.LENGTH_SHORT).show();
                    btnSend.setEnabled(true);
                    qrCodeSend.setEnabled(true);
                    btn_test.setEnabled(true);
                    btnClose.setEnabled(true);
                    btn_conn.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };

    //????????
    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            byte isHasPaper;
            byte[] cmd = null;
            switch (v.getId()) {
                case R.id.btn_conn:
                    usbCtrl.close();
                    int  i = 0;
                    for( i = 0 ; i < 5 ; i++ ){
                        dev = usbCtrl.getDev(u_infor[i][0],u_infor[i][1]);
                        if(dev != null)
                            break;
                    }
                    if( dev != null ){
                        if( !(usbCtrl.isHasPermission(dev))){
                            //Log.d("usb????","????USB?????.");
                            usbCtrl.getPermission(dev);
                        }else{
                            Toast.makeText(getActivity(), getString(R.string.usb_msg_getpermission),
                                    Toast.LENGTH_SHORT).show();
                            btnSend.setEnabled(true);
                            qrCodeSend.setEnabled(true);
                            btn_test.setEnabled(true);
                            btnClose.setEnabled(true);
                            btn_conn.setEnabled(false);
                        }
                    }
                    break;
                case R.id.btnSend:
                    isHasPaper = usbCtrl.revByte(dev);
                    if( isHasPaper == 0x38 ){
                        Toast.makeText(getActivity(), "The printer has no paper",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String txt_msg = txt_content.getText().toString();
                    if( CheckUsbPermission() == true ){
                        usbCtrl.sendMsg(txt_msg, "GBK", dev);
                    }
                    break;
                case R.id.qr_code_Send:
                    isHasPaper = usbCtrl.revByte(dev);
                    if( isHasPaper == 0x38 ){
                        Toast.makeText(getActivity(), "The printer has no paper",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cmd = new byte[7];
                    cmd[0] = 0x1B;
                    cmd[1] = 0x5A;
                    cmd[2] = 0x07;
                    cmd[3] = 0x00;
                    cmd[4] = 0x00;
                    cmd[5] = 0x17;
                    cmd[6] = 0x00;
                    String content = getResources().getString(R.string.usb_qr_code_Send_string);
                    if( CheckUsbPermission() == true){
                        usbCtrl.sendByte(cmd, dev);
                        usbCtrl.sendMsg(content, "GBK", dev);
                    }
                    break;
                case R.id.btn_test:
                    String msg = "";
                    String lang = getString(R.string.usb_strLang);
                    cmd = new byte[3];
                    cmd[0] = 0x1b;
                    cmd[1] = 0x21;

                    isHasPaper = usbCtrl.revByte(dev);
                    if( isHasPaper == 0x38 ){
                        Toast.makeText(getActivity(), "The printer has no paper",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    printImage();
                    if((lang.compareTo("en")) == 0){
                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);    //??????????
                        usbCtrl.sendMsg("Congratulations!\n", "GBK", dev);
                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);     //??????????????
                        msg = "  You have sucessfully created communications between your device and our usb printer.\n\n"
                                +" Our company is a high-tech enterprise which specializes" +
                                " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n"
                                +"  Please go to our website and see details about our company :\n" +"     www.hoinprinter.com\n\n";
                        usbCtrl.sendMsg(msg, "GBK", dev);
                    }else if((lang.compareTo("ch")) == 0){
                        cmd[2] |= 0x10;
                        usbCtrl.sendByte(cmd, dev);   //??????????
                        usbCtrl.sendMsg("???????\n", "GBK", dev);
                        cmd[2] &= 0xEF;
                        usbCtrl.sendByte(cmd, dev);   //??????????????
                        msg = "  ???????????????????????usb???????\n\n"
                                + "  ??????????????????????????????????????????????????????????????????.\n\n"
                                + "  ?????????????????????????????????:\n"+"     www.hoinprinter.com.\n\n";
                        usbCtrl.sendMsg(msg, "GBK", dev);
                    }
                    break;
                case R.id.btnClose:
                    usbCtrl.close();
                    btnSend.setEnabled(false);
                    qrCodeSend.setEnabled(false);
                    btn_test.setEnabled(false);
                    btnClose.setEnabled(false);
                    btn_conn.setEnabled(true);
                    break;
            }
        }
    }

    //??????
    private void printImage() {
        int i = 0,s = 0,j = 0,index = 0;
        byte[] temp = new byte[56];
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        sendData = pg.printDraw();

        for( i = 0 ; i < pg.getLength() ; i++ ){  //???????????????????????
            s = 0;
            temp[s++] = 0x1D;
            temp[s++] = 0x76;
            temp[s++] = 0x30;
            temp[s++] = 0x00;
            temp[s++] = (byte)(pg.getWidth() / 8);
            temp[s++] = 0x00;
            temp[s++] = 0x01;
            temp[s++] = 0x00;
            for( j = 0 ; j < (pg.getWidth() / 8) ; j++ )
                temp[s++] = sendData[index++];
            usbCtrl.sendByte(temp, dev);
        }
    }
}
