package com.example.ics.restaurantapp.activities;

import android.content.Context;
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
import com.hoin.wfsdk.PrintPic;
import com.hoin.wfsdk.WifiCommunication;


public class WifiFragment extends Fragment {
    Button btnConn = null;
    Button btnPrint = null;
    Button qrCodeSend = null;
    Button btn_test = null;
    Button btnClose = null;
    //	Button btn_opencasher = null;
    EditText edtContext = null;
    WifiCommunication wfComm = null;
    EditText txt_ip = null;
    int  connFlag = 0;
    revMsgThread revThred = null;
    //checkPrintThread cheThread = null;
    private static final int WFPRINTER_REVMSG = 0x06;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, null);
        btnConn = (Button)view.findViewById(R.id.btn_conn);
        btnConn.setOnClickListener(new ClickEvent());
        btnPrint = (Button)view.findViewById(R.id.btnSend);
        btnPrint.setOnClickListener(new ClickEvent());
        qrCodeSend = (Button)view.findViewById(R.id.qr_code_Send);
        qrCodeSend.setOnClickListener(new ClickEvent());
        btn_test = (Button)view.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new ClickEvent());
        btnClose = (Button)view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new ClickEvent());
        edtContext = (EditText) view.findViewById(R.id.txt_content);
        txt_ip = (EditText)view.findViewById(R.id.txt_ip);
        wfComm = new WifiCommunication(mHandler);
//		btn_opencasher = (Button)this.findViewById(R.id.btn_opencasher);
//		btn_opencasher.setOnClickListener(new ClickEvent());

        btnConn.setEnabled(true);
        btnPrint.setEnabled(false);
        qrCodeSend.setEnabled(false);
        btn_test.setEnabled(false);
        btnClose.setEnabled(false);
//		btn_opencasher.setEnabled(false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wfComm.close();
        wfComm = null;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {

            String msg = "";
            byte[] tcmd = null;
            switch (v.getId()) {
                case R.id.btn_conn:
                    if( connFlag == 0 ){   //?????????????????????????????
                        connFlag = 1;
                        Log.d("wifi????","???\"????\"");
                        String strAddressIp = txt_ip.getText().toString();
                        wfComm.initSocket(strAddressIp,9100);
                    }
                    break;
                case R.id.btnSend:
                    msg = edtContext.getText().toString();
                    if( msg.length() > 0 ){
                        wfComm.sendMsg(msg, "GBK");
                    }
                    break;
                case R.id.qr_code_Send:
                    tcmd = new byte[7];
                    tcmd[0] = 0x1B;
                    tcmd[1] = 0x5A;
                    tcmd[2] = 0x00;
                    tcmd[3] = 0x02;
                    tcmd[4] = 0x06;
                    tcmd[5] = 0x17;
                    tcmd[6] = 0x00;
                    String content = getResources().getString(R.string.wifi_qr_code_Send_string);
                    wfComm.sndByte(tcmd);
                    wfComm.sendMsg(content, "GBK");
                    break;
                case R.id.btn_test:
                    tcmd = new byte[3];
                    tcmd[0] = 0x10;
                    tcmd[1] = 0x04;
                    tcmd[2] = 0x04;
                    wfComm.sndByte(tcmd);   //????????????

                    String lang = getString(R.string.wifi_strLang);
                    printImage();           //?????
                    byte[] cmd = new byte[3];
                    cmd[0] = 0x1b;
                    cmd[1] = 0x21;
                    if((lang.compareTo("en")) == 0){
                        cmd[2] |= 0x10;
                        wfComm.sndByte(cmd);          //set double height and double width mode
                        wfComm.sendMsg("Congratulations! \n\n", "GBK");
                        cmd[2] &= 0xEF;
                        wfComm.sndByte(cmd);          //cancel double height and double width mode
                        try {
                            Thread.sleep(50);                   //????????????
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        msg = "  You have sucessfully created communications between your device and our WIFI printer.\n\n"
                                +"  Our company is a high-tech enterprise which specializes" +
                                " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";
                        wfComm.sendMsg(msg, "GBK");
                    }else if((lang.compareTo("ch")) == 0){
                        cmd[2] |= 0x10;
                        wfComm.sndByte(cmd);             //set double height and double width mode
                        wfComm.sendMsg("?????! \n\n", "GBK");  //send data to the printer By gbk encoding
                        cmd[2] &= 0xEF;
                        wfComm.sndByte(cmd);            //cancel double height and double width mode
                        try {
                            Thread.sleep(50);                   //???????????
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        msg = "  ???????????????????????WIFI???????\n\n"
                                + "  ??????????????????????????????????????????????????????????????????.\n\n";
                        wfComm.sendMsg(msg, "GBK");
                    }
                    break;
                case R.id.btnClose:
                    wfComm.close();
                    break;
//			case R.id.btn_opencasher:
//				tcmd = new byte[5];
//				tcmd[0] = 0x1B;
//				tcmd[1] = 0x70;
//				tcmd[2] = 0x00;
//				tcmd[3] = 0x40;
//				tcmd[4] = 0x50;
//				wfComm.sndByte(tcmd);
//				break;
            }
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WifiCommunication.WFPRINTER_CONNECTED:
                    connFlag = 0;
                    Toast.makeText(getActivity(), "Connect the WIFI-printer successful",
                            Toast.LENGTH_SHORT).show();
                    btnPrint.setEnabled(true);
                    qrCodeSend.setEnabled(true);
                    btn_test.setEnabled(true);
                    btnClose.setEnabled(true);
//				btn_opencasher.setEnabled(true);
                    btnConn.setEnabled(false);

                    revThred = new revMsgThread();
                    revThred.start();
                    break;
                case WifiCommunication.WFPRINTER_DISCONNECTED:
                    Toast.makeText(getActivity(), "Disconnect the WIFI-printer successful",
                            Toast.LENGTH_SHORT).show();
                    btnConn.setEnabled(true);
                    btnPrint.setEnabled(false);
                    qrCodeSend.setEnabled(false);
                    btn_test.setEnabled(false);
                    btnClose.setEnabled(false);
//				btn_opencasher.setEnabled(false);
                    revThred.interrupt();
                    break;
                case WifiCommunication.SEND_FAILED:
                    connFlag = 0;
                    Toast.makeText(getActivity(), "Send Data Failed,please reconnect",
                            Toast.LENGTH_SHORT).show();
                    btnConn.setEnabled(true);
                    btnPrint.setEnabled(false);
                    qrCodeSend.setEnabled(false);
                    btn_test.setEnabled(false);
                    btnClose.setEnabled(false);
//				btn_opencasher.setEnabled(false);
                    revThred.interrupt();
                    break;
                case WifiCommunication.WFPRINTER_CONNECTEDERR:
                    connFlag = 0;
                    Toast.makeText(getActivity(), "Connect the WIFI-printer error",
                            Toast.LENGTH_SHORT).show();
                    break;
                case WFPRINTER_REVMSG:
                    byte revData = (byte)Integer.parseInt(msg.obj.toString());
                    if(((revData >> 6) & 0x01) == 0x01)
                        Toast.makeText(getActivity(), "The printer has no paper",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    class checkPrintThread extends Thread {
        @Override
        public void run() {
            byte[] tcmd = new byte[3];
            tcmd[0] = 0x10;
            tcmd[1] = 0x04;
            tcmd[2] = 0x04;
            try {
                while(true){
                    wfComm.sndByte(tcmd);
                    Thread.sleep(15);
                    Log.d("wifi????","??????????????");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
                Log.d("wifi????","??????");
            }
        }
    }

    //?????????????????????????????????????
    class revMsgThread extends Thread {
        @Override
        public void run() {
            try {
                Message msg = new Message();
                int revData;
                while(true)
                {
                    revData = wfComm.revByte();               //????????????????????????????????????????????????
                    if(revData != -1){

                        msg = mHandler.obtainMessage(WFPRINTER_REVMSG);
                        msg.obj = revData;
                        mHandler.sendMessage(msg);
                    }
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("wifi????","??????");
            }
        }
    }


    //??????
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        int i = 0,s = 0,j = 0,index = 0,lines = 0;
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.bmp");
        sendData = pg.printDraw();
        byte[] temp = new byte[(pg.getWidth() / 8)*5];
        byte[] dHeader = new byte[8];
        if(pg.getLength()!=0){
            dHeader[0] = 0x1D;
            dHeader[1] = 0x76;
            dHeader[2] = 0x30;
            dHeader[3] = 0x00;
            dHeader[4] = (byte)(pg.getWidth()/8);
            dHeader[5] = 0x00;
            dHeader[6] = (byte)(pg.getLength()%256);
            dHeader[7] = (byte)(pg.getLength()/256);
            wfComm.sndByte(dHeader);
            for( i = 0 ; i < (pg.getLength()/5)+1 ; i++ ){         //???????????????
                s = 0;
                if( i < pg.getLength()/5 ){
                    lines = 5;
                }else{
                    lines = pg.getLength()%5;
                }
                for( j = 0 ; j < lines*(pg.getWidth() / 8) ; j++ ){
                    temp[s++] = sendData[index++];
                }
                wfComm.sndByte(temp);
                try {
                    Thread.sleep(60);                              //???????????
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for(j = 0 ; j <(pg.getWidth()/8)*5 ; j++ ){         //??????????
                    temp[j] = 0;
                }
            }
        }
    }
}
