package com.example.ics.restaurantapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.BaseColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.CustomerDatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.DBDiscountHelper;
import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.DiscountDbHelper;
import com.example.ics.restaurantapp.DbHelper.DiscountReaderContract;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Delay_From_Bill;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.kitchenOrderItem;
import com.example.ics.restaurantapp.ModelDB.waiterItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.SharedPreference.SessionManager;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.Utils.MyApplication;
import com.example.ics.restaurantapp.adapter.BillItemAdapter;
import com.example.ics.restaurantapp.model.Menu_Items;
import com.example.ics.restaurantapp.model.PrintModel;
import com.hoin.btsdk.BluetoothService;
import com.hoin.btsdk.PrintPic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import io.paperdb.Paper;

import static com.example.ics.restaurantapp.receiver.BlueServiceOnce.monceService;

public class BillSectionActivity extends AppCompatActivity {
    Boolean CheckConnection = true;
    int px;

    String Restaurent_name;
    String t2;
    final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    int i = 0;
    RecyclerView recycler_item_list;
    ArrayList<HashMap<String, String>> bill_item_list;
    ArrayList<HashMap<String, String>> arl;
    private ArrayList<Menu_Items> menu_items = new ArrayList<>();
    private BillItemAdapter billItemAdapter;
    EditText discount_percentage, discount_amount, discountReason, recpNo, amountPaid, amountPaid1;
    TextView txttax, dicount, nonChargable, roundOff;
    ImageView btn_close;
    LinearLayout bill_type;
    String Outlet;
    private JSONArray arr;
    private JSONObject obj;
    Button btn_back, btn_discount, btn_nonchargeable, btn_discount1, bt_add, bt_cancel;
    LinearLayout layout_bill, layout_discount, manual_discount, subgroup_discount, view_discount, view_subgroup, line_discount, line_subgroup;
    public TextView tatal_amount;
    public static TextView amount;
    float bill_amount = 0;
    int total = 0;
    ArrayList<kitchenOrderItem> billSectionOrderList;
    private double discountedAmount, discountPercentage;
    Button billCash, billCard, billCustomerAccount, billSwiggy, billZomatoCash, billZomatoOnline, billUserOnline, billPaytm;
    TextView amountPaidTxtview1;
    Button btn_save, btn_printBill;
    float bill_discount;

    String msg;
    private int totalInt = 0;

    private double Beofer_tax_amount, Tax_amount, SGST, CGST, Total_Amount;
    private String Customer_name, Waiter_Name, Date_Time, Dinenig_Area, Table_No, Menu_Name, Payment_method;
    private String Bill_no = "";
    private double Discount_amount, Round_off_amount, Qty, Rate, Tax, Amount;

    int countFroSr = 0;
    private String currentDateTime;
    private OrderDatabseHelper orderDatabseHelper;
    private DatabaseHelper helper;
    private static final String TAG = "BillSectionActivity";
    private int editFlag;
    int saveFlag = 0;
    float bill_tax = 0;
    private float f = 0;
    private float x = 0;
    private String amountS;
    private String waiterName;
    private String strPayment_method;
    private String stritemName, stritemQty, stritemRate, stritemTax, stritemAmount;
    private SessionManager sessionManager;
    private String strRaj = "1";
    private int count;
    private DatabaseHelper databaseHelper;
    private ArrayList<waiterItem> waiter_list;
    private String strWaiterName = "", strWaiterId = "", strDiscount = "";
    private String strTableNo = "";
    private String strPrefWaitID;
    private String strPrefWaitName;
    private String strPrifTableNo;
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private View qrCodeBtnSend;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private String bill;
    int a, b, result;
    String amts;
    String alreadyDiscount ="0";
    Cursor getDisCursor;
    private BluetoothSocket mSocket;
    CustomerDatabaseHelper discountDatabaseHelper = new CustomerDatabaseHelper(this);
    SQLiteDatabase db;
    String pre_table_check;
    private DBDiscountHelper dbdisHelper;
    DiscountDbHelper discountDbHelper = new DiscountDbHelper(this);
    String[] splited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_section);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        currentDateTime = sdf1.format(currentTime);
        orderDatabseHelper = new OrderDatabseHelper(this);
        databaseHelper = new DatabaseHelper(this);
        billSectionOrderList = new ArrayList<>();
        bill_type = (LinearLayout) findViewById(R.id.bill_type);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        btn_back = (Button) findViewById(R.id.btn_back);
        amount = (TextView) findViewById(R.id.amount);
        tatal_amount = (TextView) findViewById(R.id.tatal_amount);
        btn_discount = (Button) findViewById(R.id.btn_discount);
        btn_discount1 = (Button) findViewById(R.id.btn_discount1);
        btn_nonchargeable = (Button) findViewById(R.id.btn_nonchargeable);
        layout_bill = (LinearLayout) findViewById(R.id.layout_bill);
        layout_discount = (LinearLayout) findViewById(R.id.layout_discount);
        view_discount = (LinearLayout) findViewById(R.id.view_discount);
        discount_percentage = (EditText) findViewById(R.id.discount_percentage);
        discount_amount = (EditText) findViewById(R.id.discount_amount);
        discountReason = (EditText) findViewById(R.id.discountReason);

        recpNo = (EditText) findViewById(R.id.recpNo);
        amountPaid = (EditText) findViewById(R.id.amountPaid);
        amountPaid1 = (EditText) findViewById(R.id.amountPaid1);

        amountPaidTxtview1 = (TextView) findViewById(R.id.amountPaidTextview);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_printBill = (Button) findViewById(R.id.btn_printbill);

        txttax = (TextView) findViewById(R.id.tax);
        dicount = (TextView) findViewById(R.id.dicount);
        nonChargable = (TextView) findViewById(R.id.nonChargable);
        roundOff = (TextView) findViewById(R.id.roundOff);

        bt_add = (Button) findViewById(R.id.bt_add);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);

        billCash = (Button) findViewById(R.id.billCash);
        billCard = (Button) findViewById(R.id.billCard);
        billUserOnline = (Button) findViewById(R.id.billUserOnline);
        billPaytm = (Button) findViewById(R.id.billPaytm);
        arr = new JSONArray();
        sessionManager = new SessionManager(this);
        strPrefWaitID = sessionManager.getWaiterId();
        strPrefWaitName = sessionManager.getWaiterName();
        strPrifTableNo = AppPrefences.getTable(BillSectionActivity.this);
        pre_table_check = strPrifTableNo;
        splited = new String[20];
        String areases = sessionManager.getKeyUserArea();
        dbdisHelper = new DBDiscountHelper(this, "disisaab_offline.sqlite", null, 1);
        // Gets the data repository in write mode
        if(strPrifTableNo.contains(" "))
        {
            splited = strPrifTableNo.split(" ");
            strPrifTableNo = "T";
            for(int k=0;k<splited.length;k++)
            {

                strPrifTableNo = strPrifTableNo.concat(splited[k]);
                Log.e("tablename" , ""+strPrifTableNo);
            }

        }
        else if(strPrifTableNo.contains("-")){
            splited =   strPrifTableNo.split("-");
            strPrifTableNo = "T";
            for(int k=0;k<splited.length;k++)
            {
                strPrifTableNo = strPrifTableNo.concat(splited[k]);
                Log.e("tablename" , ""+strPrifTableNo);
            }
        }

        try{
            String broken_idea = areases.substring(0,3);
                     //  Cursor discursor = dbdisHelper.getData("SELECT * FROM WAITER_DISCOUNT_ACCOUNT WHERE Waiter_id = "+strPrefWaitID + " AND Waiter_table = "+strPrifTableNo);
                       Cursor discursor = dbdisHelper.getData("SELECT * FROM WAITER_DISCOUNT_ACCOUNT");
                       if(discursor != null && discursor.getCount() > 0)
                       {
                           while (discursor.moveToNext()) {
                               String whichtable = discursor.getString(2);

                               String areadb =  discursor.getString(5);
                               if(whichtable.equals(strPrifTableNo) && areadb.contains(areases))
                               {
                                   Toast.makeText(this, "already discount "+strPrifTableNo, Toast.LENGTH_SHORT).show();
                                   Log.e("string" , "at aklready dis"+whichtable);
                                   alreadyDiscount = discursor.getString(4);
                                   Double truncatedDicount = BigDecimal.valueOf(Double.valueOf(alreadyDiscount))
                                           .setScale(3, RoundingMode.HALF_UP)
                                           .doubleValue();

                                   dicount.setText(String.valueOf(truncatedDicount));

                                   Float y;
                                   float x = (float) Math.floor(bill_amount + bill_tax - discountedAmount);
                                   y = (float) (bill_amount + bill_tax - discountedAmount - x);

                                   roundOff.setText(String.valueOf(y));

                                   tatal_amount.setText(String.valueOf(x));
                                   amountPaid1.setText(String.valueOf(x));
                                   amountPaid.setText(String.valueOf(x));
                               }
                               Log.e("already is ",""+alreadyDiscount);
                           }
                       }
//            if(discursor !=null)
//            {
//             //   discursor.moveToFirst();
//                //to form a table of data as per database.
//                    //more to the first row
//                    discursor.moveToFirst();
//
//                    //iterate over rows
//                    for (int i = 0; i < discursor.getCount(); i++) {
//
//                        //iterate over the columns
//                        for(int j = 0; j < discursor.getColumnNames().length; j++){
//
//                    String whichtable = discursor.getString(2);
//
//                    String areadb =  discursor.getString(5);
//                    if(whichtable.equals(strPrifTableNo) && areadb.contains(areases))
//                    {
//                        Toast.makeText(this, "already discount "+strPrifTableNo, Toast.LENGTH_SHORT).show();
//                        Log.e("string" , "at aklready dis"+whichtable);
//                        alreadyDiscount = discursor.getString(4);
//                        Double truncatedDicount = BigDecimal.valueOf(Double.valueOf(alreadyDiscount))
//                                .setScale(3, RoundingMode.HALF_UP)
//                                .doubleValue();
//
//                        dicount.setText(String.valueOf(truncatedDicount));
//
//                        Float y;
//                        float x = (float) Math.floor(bill_amount + bill_tax - discountedAmount);
//                        y = (float) (bill_amount + bill_tax - discountedAmount - x);
//
//                        roundOff.setText(String.valueOf(y));
//
//                        tatal_amount.setText(String.valueOf(x));
//                        amountPaid1.setText(String.valueOf(x));
//                        amountPaid.setText(String.valueOf(x));
//                    }
//                    Log.e("already is ",""+alreadyDiscount);
//                            //append the column value to the string builder and delimit by a pipe symbol
//                         //   stringBuilder.append(discursor.getString(j) + "|");
//                        }
//                        //add a new line carriage return
//                      //  stringBuilder.append("\n");
//
//                        //move to the next row
//                        discursor.moveToNext();
//                    }
//                    //close the cursor
//                    discursor.close();
//
////                while(discursor.moveToNext())
////                {
////                    alreadyDiscount = discursor.getString(4);
////                    String whichtable = discursor.getString(2);
////                    if(whichtable.equals(strPrifTableNo))
////                    {
////                        Double truncatedDicount = BigDecimal.valueOf(Double.valueOf(alreadyDiscount))
////                                .setScale(3, RoundingMode.HALF_UP)
////                                .doubleValue();
////
////                        dicount.setText(String.valueOf(truncatedDicount));
////
////                        Float y;
////                        float x = (float) Math.floor(bill_amount + bill_tax - discountedAmount);
////                        y = (float) (bill_amount + bill_tax - discountedAmount - x);
////
////                        roundOff.setText(String.valueOf(y));
////
////                        tatal_amount.setText(String.valueOf(x));
////                        amountPaid1.setText(String.valueOf(x));
////                        amountPaid.setText(String.valueOf(x));
////                    }
////                    Log.e("already is ",""+alreadyDiscount);
////                }
//            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


//          try{
//               db = discountDbHelper.getReadableDatabase();
//          //      discountDbHelper.onCreate(db);
//// Define a projection that specifies which columns from the database
//// you will actually use after this query.
//              String[] projection = {
//                      BaseColumns._ID,
//                      DiscountReaderContract.FeedEntry.Waiter_Id,
//                      DiscountReaderContract.FeedEntry.Waiter_Table,
//                      DiscountReaderContract.FeedEntry.Waiter_Discount,
//                      DiscountReaderContract.FeedEntry.Waiter_Name
//              };
//
//// Filter results WHERE "title" = 'My Title'
//              String selection = DiscountReaderContract.FeedEntry.Waiter_Id + " = "+strPrefWaitID+" AND " + DiscountReaderContract.FeedEntry.Waiter_Table +" =  "+ strPrifTableNo;
//              String[] selectionArgs = { strPrefWaitID,strPrifTableNo };
//
//// How you want the results sorted in the resulting Cursor
////              String sortOrder =
////                      DiscountReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
//
//              Cursor cursor = db.query(
//                      DiscountReaderContract.FeedEntry.TABLE_NAME,   // The table to query
//                      projection,             // The array of columns to return (pass null to get all)
//                      selection,              // The columns for the WHERE clause
//                      selectionArgs,          // The values for the WHERE clause
//                      null,                   // don't group the rows
//                      null,null                   // don't filter by row groups
//                                  // The sort order
//              );
//              List Dis = new ArrayList<>();
//              while(cursor.moveToNext()) {
//                  long itemId = cursor.getLong(
//                          cursor.getColumnIndexOrThrow(DiscountReaderContract.FeedEntry._ID));
//                  Dis.add(itemId);
//              }
//              cursor.close();
//          }catch (Exception e)
//          {
//              e.printStackTrace();
//              Toast.makeText(this, "New waiter", Toast.LENGTH_SHORT).show();
//          }

       Log.e("on" , "create called");
//        getDisCursor=  orderDatabseHelper.getDiscountByTable2(strPrefWaitID,strPrifTableNo , strPrefWaitName);
//        if (getDisCursor != null)
//            if (getDisCursor.moveToFirst()) {
////
////                String a= getDisCursor.getString(getDisCursor.getColumnIndex("0"));
////                String a2= getDisCursor.getString(getDisCursor.getColumnIndex("1"));
////                String a3= getDisCursor.getString(getDisCursor.getColumnIndex("3"));
//
//                String a= getDisCursor.getString(0);
//                String a2= getDisCursor.getString(1);
//                String a3= getDisCursor.getString(2);
////                Log.e("at cursor "+a,""+getDisCursor.getString(getDisCursor.getColumnIndex("0")));
////                Log.e("at cursor "+a2,""+getDisCursor.getString(getDisCursor.getColumnIndex("1")));
////                Log.e("at cursor "+a3,""+getDisCursor.getString(getDisCursor.getColumnIndex("3")));
////                details = new ForWaiterDetails(cursor.getString(getDisCursor.getColumnIndex(TABLE_14_COL_0)),
////                        cursor.getString(cursor.getColumnIndex(TABLE_14_COL_1)),
////                        cursor.getString(cursor.getColumnIndex(TABLE_14_COL_3)));
//                alreadyDiscount = a3;
//                getDisCursor.close();
//            }

//        mService = new BluetoothService(BillSectionActivity.this, mHandler);
//        if (mService.isAvailable() == false) {
//            Toast.makeText(BillSectionActivity.this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//        }
//        SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
//        String address = sharedPreferences.getString("device", "");
//        if (address == null || address.equals("")) {
//
//        } else {
//            con_dev = mService.getDevByMac(address);
////            try {
////                createBluetoothSocket(con_dev);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//            mService.connect(con_dev);
//
//        }

        gettingAllOrder();
        getWaiterData();



        Outlet = AppPrefences.getOutlet(BillSectionActivity.this);
        if (billSectionOrderList.size() == 0) {
            txttax.setText("0");
            roundOff.setText("0");
            dicount.setText("0");
            nonChargable.setText("0");
            amount.setText("0");
            tatal_amount.setText("0");
            amountPaid1.setText("0");
            recpNo.setText("0");
        } else {
            for (kitchenOrderItem kitchenOrderItem : billSectionOrderList) {
                float v, v1;
                v = kitchenOrderItem.getRate();
                v1 = kitchenOrderItem.getQuantity();
                f = (v * v1) + f;
                Log.e("TOTAMO", f + "");
            }
            //-----------------------------------------------------------------------------

            final Cursor localCursor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);

            try {
                while (localCursor.moveToNext()) {
//                bill_amount = localCursor.getInt(6);
                    bill_amount = f;
//                    bill_tax = localCursor.getFloat(11);
                    bill_tax = (bill_amount * 5) / 100;
//                    bill_discount = localCursor.getFloat(13);
                    try {
                        if(!alreadyDiscount.isEmpty())
                        {
                            bill_discount = Float.parseFloat(alreadyDiscount);
                        }else{

                        }
                    }catch (Exception e)
                    {
                        bill_discount = Float.parseFloat(strDiscount);
                        e.printStackTrace();
                    }


                    total = (int) (bill_amount + bill_tax);

                    Log.d("billamount", String.valueOf(total));
                }
            } catch (Exception e) {

            }

            Float y;
            float x = (float) Math.floor(bill_amount + bill_tax - bill_discount);
            if (x < 0) {
                x = 0;
                y = (bill_amount + bill_tax - x);
            }
            y = (bill_amount + bill_tax - x);
            Double roundoff = BigDecimal.valueOf(y)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
            String s = String.valueOf(roundoff);
            double val = roundoff;
            String[] arr = String.valueOf(val).split("\\.");
            int[] intArr = new int[2];
            intArr[0] = Integer.parseInt(arr[0]); // 1
            intArr[1] = Integer.parseInt(arr[1]); // 9
            roundOff.setText(String.valueOf("0." + intArr[1]));
            try {
                if (!alreadyDiscount.isEmpty()) {
                    dicount.setText(String.valueOf(alreadyDiscount));
                }
            }catch (Exception e)
            {
                dicount.setText(String.valueOf(0.0));
            }

            Double total_tax = BigDecimal.valueOf(bill_tax)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();

            tatal_amount.setText(String.valueOf(x));
            amount.setText(String.valueOf(bill_amount));
            txttax.setText(String.valueOf(total_tax));
            amountPaid1.setText(String.valueOf(x));
            helper = new DatabaseHelper(this);

            bill_item_list = new ArrayList<>();
            arl = new ArrayList<>();


            billItemAdapter = new BillItemAdapter(BillSectionActivity.this, billSectionOrderList);
            recycler_item_list = (RecyclerView) findViewById(R.id.recycler_item_list);
            recycler_item_list.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BillSectionActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recycler_item_list.setLayoutManager(linearLayoutManager);
            recycler_item_list.setAdapter(billItemAdapter);
        }

        btn_printBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderDatabseHelper db = new OrderDatabseHelper(BillSectionActivity.this);
                db.getWritableDatabase();
                Cursor localCursor = db.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                byte[] format = {27, 33, 0};
                byte[] arrayOfByte1 = {27, 33, 0};
                String Header = "Order:"+String.valueOf(variables.generateReceiptNo(variables.receipt_no))+" TableNo:"+""+pre_table_check+"\n"+
                        "Date:"+currentDateTime.substring(0,16)+" "+currentDateTime.substring(currentDateTime.length()-2)+"\n"+
                        "------------------------\n" +
                        "Items\n" + "(Qt)    (Rate)     (Amt)\n" +
                        "------------------------\n";


                // bill = Header;
                //bill = Header;
//                String amt = null ;
                while (localCursor.moveToNext()) {
                    Toast.makeText(BillSectionActivity.this, "after header", Toast.LENGTH_SHORT).show();
                    String s = localCursor.getString(2);
                    String s1 = localCursor.getString(4);
                    String s2 = localCursor.getString(3);
                    String s3 = localCursor.getString(5);
                    String s4 = localCursor.getString(14);
                    if (s.length() == 1) {
                        s = s.substring(0, 1) + "                      ";
                    }
                    else if (s.length() == 2) {
                        //   for (int j = s.length() - 12; j <= s.length(); j++)
                        s = s.substring(0, 2) + "                     ";
                    }
                    else if (s.length() == 3) {
                        s = s.substring(0, 3) + "                    ";
                    }
                    else if (s.length() == 4) {
                        s = s.substring(0, 4) + "                   ";
                    }
                    else if (s.length() == 5) {
                        s = s.substring(0, 5) + "                  ";
                    }
                    else if (s.length() == 6) {
                        s = s.substring(0, 6) + "                 ";
                    }
                    else if (s.length() == 7) {
                        s = s.substring(0, 7) + "                ";
                    }
                    else if (s.length() == 8) {
                        s = s.substring(0, 8) + "               ";
                    }
                    else if (s.length() == 9) {
                        s = s.substring(0, 9) + "              ";
                    }
                    else if (s.length() == 10) {
                        s = s.substring(0, 10) + "             ";
                    }
                    else if (s.length() == 11) {
                        s = s.substring(0, 11) + "            ";
                    }
                    else if (s.length() == 12) {
                        s = s.substring(0, 12) + "           ";
                    }
                    else if (s.length() == 13) {
                        s = s.substring(0, 13) + "          ";
                    }
                    else if (s.length() == 14) {
                        s = s.substring(0, 14) + "         ";
                    }
                    else if (s.length() == 15) {
                        s = s.substring(0, 15) + "        ";
                    }
                    else if (s.length() == 16) {
                        s = s.substring(0, 16) + "       ";
                    }
                    else if (s.length() == 17) {
                        s = s.substring(0, 17) + "      ";
                    }
                    else if (s.length() == 18) {
                        s = s.substring(0, 18) + "     ";
                    }
                    else if (s.length() == 19) {
                        s = s.substring(0, 19) + "    ";
                    }
                    else if (s.length() == 20) {
                        s = s.substring(0, 20) + "   ";
                    }
                    else if (s.length() == 21) {
                        s = s.substring(0, 21) + "  ";
                    }
                    else if (s.length() == 22) {
                        s = s.substring(0, 22) + " ";
                    }
                    else if (s.length() == 23) {
                        s = s.substring(0, 23) + " ";
                    }
                    else if (s.length() >= 24) {
                        s = s.substring(0, 23) + " ";
                    }
                    i = 0;
                    String items = s + "\n";

                    if (s2.length()== 3)
                    {
                        String items2 = s1 + "         " + s2 + "       " + s3 + "\n";
                  //      bill = bill + items + items2;
                    }
                    else
                        {

                        String items2 = s1 + "          " + s2 + "        " + s3 + "\n";
                      //  bill = bill + items + items2;
                    }

                    i++;
                    System.out.println(bill);
                    Toast.makeText(BillSectionActivity.this, bill, Toast.LENGTH_SHORT).show();
                }
                String tt = tatal_amount.getText().toString();
                String txt = txttax.getText().toString();
                int aldisbill = alreadyDiscount.length();
                Boolean albool = !alreadyDiscount.contains(".");
                Log.e("discount",""+discount_amount.getText().length());
                String dis = discount_amount.getText().toString();
                int txtbilllen = txt.length();
                Toast.makeText(BillSectionActivity.this, ""+discount_amount.getText().toString(), Toast.LENGTH_SHORT).show();
//                String footer = "-----------------------" + " TAX                 " + txt+"\n" + "Total              " + tt +"\n"+ "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
//                bill = bill + footer;
                try{
                    if(!alreadyDiscount.isEmpty())
                    {
                        if( alreadyDiscount.length() ==1)
                        {
                            alreadyDiscount = "00";
//                            Toast.makeText(BillSectionActivity.this, "discount at 3"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();
//
//                            String footer = "-----------------------" + " TAX                  " + txt + "\n" + "DISCOUNT            " + alreadyDiscount.concat(".0") + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
//                            bill = bill + footer;


                        }
                        if( (alreadyDiscount.length() ==2 ) && txt.length() ==3)
                        {

                            Toast.makeText(BillSectionActivity.this, "discount at 3"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();

                            String footer = "-----------------------" + " TAX                  " + txt + "\n" + "DISCOUNT            " + alreadyDiscount.concat(".0") + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                           // bill = bill + footer;


                        }
                        if((alreadyDiscount.length() ==2 )  && txt.length() ==4)
                        {
                            Toast.makeText(BillSectionActivity.this, "discount at 4"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();
                            String footer = "-----------------------" + " TAX                 " + txt + "\n" + "DISCOUNT            " + alreadyDiscount.concat(".0") + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                            bill = bill + footer;
                        }
                        if( (alreadyDiscount.length() ==3) && txt.length() ==3)
                       {
                        Toast.makeText(BillSectionActivity.this, "discount at 5"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();
                        String footer = "-----------------------" + " TAX                 " + txt + "\n" + "DISCOUNT          " + alreadyDiscount.concat(".0") + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                        bill = bill + footer;
                      }
                      if( (alreadyDiscount.length() ==3) && txt.length() ==3)
                       {
                        Toast.makeText(BillSectionActivity.this, "discount at 5"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();
                        String footer = "-----------------------" + " TAX                 " + txt + "\n" + "DISCOUNT          " + alreadyDiscount.concat(".0") + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                        bill = bill + footer;
                      }

                        if( (alreadyDiscount.length() ==3) && txt.length() ==4)
                        {
                            Toast.makeText(BillSectionActivity.this, "discount at 5"+discount_amount.getText().length(), Toast.LENGTH_SHORT).show();
                            String footer = "-----------------------" + " TAX                " + txt + "\n" + "DISCOUNT          " + alreadyDiscount.concat(".0") + "\nTotal            " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                            bill = bill + footer;
                        }
                    }

                }
                catch (Exception e)
                {
                    String footer = "-----------------------" + " TAX                 " + txt + "\n" + "DISCOUNT            " + alreadyDiscount + "\nTotal              " + tt + "\n" + "------------------------" + "THANK YOU! VISIT AGAIN!!" + "------------------------";
                //    bill = bill + footer;
                }


                bill = bill;

                String lang = getString(R.string.bluetooth_strLang);
                if ((lang.compareTo("en")) == 0) {
                    String Addressbill1 = " ";
                    String Addressbill2 = " ";
                    String htmlAsString = getString(R.string.underlineprint);      // used by WebView
                    Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView

                    format[2] = ((byte) (0x06 | arrayOfByte1[2]));
                    format[2] = ((byte) (0x20 | arrayOfByte1[2]));
                    format[2] = ((byte) (0x8 | arrayOfByte1[2]));
              //      mService.write(format);
                    monceService.write(format);
                    //for RESTaurants
                    if(sessionManager.getKeyBillResName().length() ==2)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }

                    //For address 2
                     if(sessionManager.getKeyBillResType2().length() !=0 )
                     {
                         if(sessionManager.getKeyBillResType2().length() ==2)
                         {
                             Addressbill2 = Addressbill2+ "           "+sessionManager.getKeyBillResType2();
                         }
                         if(sessionManager.getKeyBillResType2().length() ==3)
                         {
                             Addressbill2 = Addressbill2+ "        "+sessionManager.getKeyBillResType2();
                         }  if(sessionManager.getKeyBillResType2().length() ==4)
                     {
                         Addressbill2 = Addressbill2+ "        "+sessionManager.getKeyBillResType2();
                     }  if(sessionManager.getKeyBillResType2().length() ==5)
                     {
                         Addressbill2 = Addressbill2+ "       "+sessionManager.getKeyBillResType2();
                     }  if(sessionManager.getKeyBillResType2().length() ==6)
                     {
                         Addressbill2 = Addressbill2+ "       "+sessionManager.getKeyBillResType2();
                     }  if(sessionManager.getKeyBillResType2().length() ==7)
                     {
                         Addressbill2 = Addressbill2+ "       "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==8)
                     {
                         Addressbill2 = Addressbill2+ "      "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==9)
                     {
                         Addressbill2 = Addressbill2+ "     "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==10)
                     {
                         Addressbill2 = Addressbill2+ "     "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==11)
                     {
                         Addressbill2 = Addressbill2+ "     "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==12)
                     {
                         Addressbill2 = Addressbill2+ "    "+sessionManager.getKeyBillResType2();
                     } if(sessionManager.getKeyBillResType2().length() ==13)
                     {
                         Addressbill2 = Addressbill2+ "   "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==14)
                     {
                         Addressbill2 = Addressbill2+ "  "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==15)
                     {
                         Addressbill2 = Addressbill2+ "  "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==16)
                     {
                         Addressbill2 = Addressbill2+ "  "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==17)
                     {
                         Addressbill2 = Addressbill2+ " "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==18)
                     {
                         Addressbill2 = Addressbill2+ " "+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==19)
                     {
                         Addressbill2 = Addressbill2+ ""+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==20)
                     {
                         Addressbill2 = Addressbill2+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==21)
                     {
                         Addressbill2 = Addressbill2+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==22)
                     {
                         Addressbill2 = Addressbill2+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==23)
                     {
                         Addressbill2 = Addressbill2+sessionManager.getKeyBillResType2();
                     }if(sessionManager.getKeyBillResType2().length() ==24)
                     {
                         Addressbill2 =  sessionManager.getKeyBillResType2();
                     }

                         //For address 2
                         if(sessionManager.getKeyBillResType2().length() !=0 )
                         {

                             monceService.sendMessage("          "+sessionManager.getKeyBillResName()+" \n"+Addressbill1+"\n"+Addressbill2+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                         }else {
                             monceService.sendMessage("          "+sessionManager.getKeyBillResName()+" \n"+Addressbill1+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                         }

                    //     monceService.sendMessage("          "+sessionManager.getKeyBillResName()+" \n"+Addressbill1+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                     }
                     else {
                         monceService.sendMessage("          "+sessionManager.getKeyBillResName()+" \n"+Addressbill1+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                     }

                    }
                    else
                        if(sessionManager.getKeyBillResName().length() ==3)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }  if(sessionManager.getKeyBillResType().length() ==4)
                      {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                     }
                     if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }
                        monceService.sendMessage("         "+sessionManager.getKeyBillResName()+" \n" + "    "+sessionManager.getKeyBillResType() + " RESTAURANT"+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                    }
                    else

                        if(sessionManager.getKeyBillResName().length() ==4)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }  if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }
                        monceService.sendMessage("         "+sessionManager.getKeyBillResName()+" \n" + "    "+sessionManager.getKeyBillResType() + " RESTAURANT"+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                    }



                    else

                        if(sessionManager.getKeyBillResName().length() ==5)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }  if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }
                        monceService.sendMessage("         "+sessionManager.getKeyBillResName()+" \n" + "    "+sessionManager.getKeyBillResType() +"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                    }


                    else


                        if(sessionManager.getKeyBillResName().length() ==6)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }
                        monceService.sendMessage("        "+sessionManager.getKeyBillResName()+" \n" + "    "+sessionManager.getKeyBillResType() +"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                    }
                    else


                        if(sessionManager.getKeyBillResName().length() ==7)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }  if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }

                        monceService.sendMessage("        "+sessionManager.getKeyBillResName()+"\n"+sessionManager.getKeyBillResType()+"        "+sessionManager.getKeyBillResType2()+"\n" + htmlAsSpanned + "\n------------------------", "GBK");
                    } else


                        if(sessionManager.getKeyBillResName().length() ==8)
                    {
                        if(sessionManager.getKeyBillResType().length() ==2)
                        {
                            Addressbill1 = Addressbill1+ "           "+sessionManager.getKeyBillResType();
                        }
                        if(sessionManager.getKeyBillResType().length() ==3)
                        {
                            Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                        }  if(sessionManager.getKeyBillResType().length() ==4)
                    {
                        Addressbill1 = Addressbill1+ "        "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==5)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==6)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    }  if(sessionManager.getKeyBillResType().length() ==7)
                    {
                        Addressbill1 = Addressbill1+ "       "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==8)
                    {
                        Addressbill1 = Addressbill1+ "      "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==9)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==10)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==11)
                    {
                        Addressbill1 = Addressbill1+ "     "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==12)
                    {
                        Addressbill1 = Addressbill1+ "    "+sessionManager.getKeyBillResType();
                    } if(sessionManager.getKeyBillResType().length() ==13)
                    {
                        Addressbill1 = Addressbill1+ "   "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==14)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==15)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==16)
                    {
                        Addressbill1 = Addressbill1+ "  "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==17)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==18)
                    {
                        Addressbill1 = Addressbill1+ " "+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==19)
                    {
                        Addressbill1 = Addressbill1+ ""+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==20)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==21)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==22)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }if(sessionManager.getKeyBillResType().length() ==23)
                    {
                        Addressbill1 = Addressbill1+sessionManager.getKeyBillResType();
                    }
                    if(sessionManager.getKeyBillResType().length() ==24)
                    {
                        Addressbill1 =  sessionManager.getKeyBillResType();
                    }
                        monceService.sendMessage("        "+sessionManager.getKeyBillResName()+" \n" + "    "+sessionManager.getKeyBillResType()+"\n        " + htmlAsSpanned + "\n------------------------", "GBK");
                    }


                    //
             //       mService.sendMessage("       SAGAR VIEW\n" + "    5-STAR RESTAURANT\n" + "       " + htmlAsSpanned + "\n------------------------", "GBK");

             //       mService.sendMessage(bill, "GBK");
                //    monceService.sendMessage(bill, "GBK");
                } else if ((lang.compareTo("ch")) == 0) {
                    // Bold
                    format[2] = ((byte) (0x10 | arrayOfByte1[2]));
                    monceService.write(format);
                }

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BillSectionActivity.this, Delay_From_Bill.class);
                startActivity(intent);
                finish();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bill_type.setVisibility(View.VISIBLE);
            }
        });

        btn_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_bill.setVisibility(View.GONE);
                btn_discount.setVisibility(View.GONE);
                layout_discount.setVisibility(View.VISIBLE);
                btn_discount1.setVisibility(View.VISIBLE);
            }
        });

        discount_percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFlag = 1;
                Log.d(TAG, "onClick: editText " + editFlag);
            }
        });

        discount_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFlag = 0;
                Log.d(TAG, "onClick: editText " + editFlag);
            }
        });

        discount_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editFlag == 1) {
                    String per = discount_percentage.getText().toString();
                    if (!per.equals("")) {
                        double percentage = (double) Double.parseDouble(per);
                        discountedAmount = ((bill_amount) * percentage) / 100;
                        discount_amount.setText(String.valueOf(discountedAmount));
                        //discount_amount.setText(String.valueOf(discountedAmount));
                        Log.d(TAG, "onTextChanged: raam");
                        Log.d(TAG, "onTextChanged: editFlag =" + editFlag);
                    } else {
                        discount_amount.setText("0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: hello");


            }
        });


        discount_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editFlag == 0) {
                    String amnt = discount_amount.getText().toString();
                    if (!amnt.equals("")) {
                        discountedAmount = (double) Double.parseDouble(discount_amount.getText().toString());
                        discountPercentage = (discountedAmount / bill_amount) * 100;
                        discount_percentage.setText(String.valueOf(discountPercentage));
                        Log.d(TAG, "onTextChanged: editFlag =" + editFlag);
                    } else {
                        discount_percentage.setText("0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Cursor cursorMenu = null;
            Cursor cursorOrder = null;
            CheckConnection = true;
            changeTextStatus(true);
            if (Bill_no != null) {
                cursorOrder = orderDatabseHelper.getAllOrderDetails();
                if (cursorOrder != null && cursorOrder.getCount() > 0) {

                    while (cursorOrder.moveToNext()) {
                        count++;
//String Customer_name, Waiter_Name, Date_Time, Dinenig_Area, Table_No, Menu_Name, Payment_method;
                        arr = new JSONArray();
                        String Bill_no = cursorOrder.getString(0);
                        String Customer_name = cursorOrder.getString(1);
                        String Dinenig_Area = cursorOrder.getString(2);
                        String Table_No = cursorOrder.getString(3);
                        String Waiter_Name = cursorOrder.getString(4);
                        String Date_Time = cursorOrder.getString(5);
                        double Beofer_tax_amount = Double.parseDouble(cursorOrder.getString(6));
                        double Tax_amount = Double.parseDouble(cursorOrder.getString(7));
                        double SGST = Double.parseDouble(cursorOrder.getString(8));
                        double CGST = Double.parseDouble(cursorOrder.getString(9));
                        double Total_Amount = Double.parseDouble(cursorOrder.getString(10));
                        double Discount_amount = Double.parseDouble(cursorOrder.getString(11));
                        double Round_off_amount = Double.parseDouble(cursorOrder.getString(12));
                        String Payment_method = cursorOrder.getString(13);
                        cursorMenu = orderDatabseHelper.getAllMenuItems(Bill_no);
                        if (cursorMenu != null && cursorMenu.getCount() > 0) {
                            menu_items.clear();
                            Log.e("Filling", "1");
                            while (cursorMenu.moveToNext()) {
                                String Menu_Name = cursorMenu.getString(1);
                                double Qty = Double.parseDouble(cursorMenu.getString(2));
                                double Rate = Double.parseDouble(cursorMenu.getString(3));
                                double Tax = Double.parseDouble(cursorMenu.getString(4));
                                double Amount = Double.parseDouble(cursorMenu.getString(5));
                                menu_items.add(new Menu_Items(Menu_Name, Qty + "", Rate + "", Tax + "", Amount + ""));
                            }
                            for (int i = 0; i < menu_items.size(); i++) {
                                arr.put(menu_items.get(i).getJSONObject());
                                String s = menu_items.get(i).getJSONObject().toString();
                            }
                            int i = orderDatabseHelper.deleteOrderDataByBillNo(Bill_no);
                            if (i == 1) {
                                new PostDataForOffline(Bill_no, Customer_name, Dinenig_Area, Table_No, Waiter_Name, Date_Time, Beofer_tax_amount + "", Tax_amount + "", SGST + ""
                                        , CGST + "", Total_Amount + "", Discount_amount + "", Round_off_amount + "", Payment_method, arr).execute();
                                Log.e("Bill_Number", "1");
                            } else {
                                Toast.makeText(this, "Some Thing Went Wrong To Sent Offline Data to Server", Toast.LENGTH_SHORT).show();
                                Log.e("Bill_Number", "0");
                            }
                        }
                        Log.e("Counting", count + "");

                    }

                }

            } else {
                Log.e("Blank", "1");
            }
            Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
        } else {
            CheckConnection = false;
            Toast.makeText(this, "go", Toast.LENGTH_SHORT).show();
        }
        btn_discount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discount.setVisibility(View.GONE);
                btn_discount1.setVisibility(View.GONE);

                layout_bill.setVisibility(View.VISIBLE);
                btn_discount.setVisibility(View.VISIBLE);
            }
        });

        btn_nonchargeable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        recpNo.setText(String.valueOf(variables.generateReceiptNo(variables.receipt_no)));

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = discountDbHelper.getWritableDatabase();
                String reason = discountReason.getText().toString();
                String disAmo = discount_amount.getText().toString();
//                variables.selected_waiter_data.setDiscount_amo(disAmo);
                boolean b = orderDatabseHelper.Update_Discount(strPrefWaitID, strPrefWaitName, strPrifTableNo, disAmo);
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(DiscountReaderContract.FeedEntry.Waiter_Id, strPrefWaitID);
                values.put(DiscountReaderContract.FeedEntry.Waiter_Table, strPrifTableNo);
                values.put(DiscountReaderContract.FeedEntry.Waiter_Name, strPrefWaitName);
                values.put(DiscountReaderContract.FeedEntry.Waiter_Discount, disAmo);

// Insert the new row, returning the primary key value of the new row
            //    discountDbHelper.Insert_Wait_Discount(strPrefWaitID,strPrifTableNo,strPrefWaitName,disAmo);
               // long newRowId = orderDatabseHelper.insert(DiscountReaderContract.FeedEntry.TABLE_NAME, null, values);
//                Boolean newRowId = discountDatabaseHelper.insertNewWAITER_DISCOUNT(strPrefWaitID, strPrifTableNo, strPrefWaitName , disAmo);

                dbdisHelper.insertDatatoAccount(strPrefWaitID, strPrifTableNo, strPrefWaitName, disAmo , sessionManager.getKeyUserArea());
                if (b) {
                    Log.e("JAI HO", "JAI HO");
                } else {
                    Log.e("JAI HO", "NOTHING");
                }
                if (reason.equals("")) {
                    Toast.makeText(BillSectionActivity.this, "Please add the reason", Toast.LENGTH_SHORT).show();
                } else {
                    Double truncatedDicount = BigDecimal.valueOf(discountedAmount)
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();


                    alreadyDiscount = String.valueOf(Integer.valueOf(String.valueOf(truncatedDicount.intValue())));
//                    dicount.setText(String.valueOf(truncatedDicount));
                    dicount.setText(String.valueOf(alreadyDiscount));

                    layout_discount.setVisibility(View.GONE);
                    btn_discount1.setVisibility(View.GONE);

                    layout_bill.setVisibility(View.VISIBLE);
                    btn_discount.setVisibility(View.VISIBLE);
                    Float y;
                    float x = (float) Math.floor(bill_amount + bill_tax - discountedAmount);
                    y = (float) (bill_amount + bill_tax - discountedAmount - x);

                    roundOff.setText(String.valueOf(y));

                    tatal_amount.setText(String.valueOf(x));
                    amountPaid1.setText(String.valueOf(x));
                    amountPaid.setText(String.valueOf(x));

                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_discount.setVisibility(View.GONE);
                btn_discount1.setVisibility(View.GONE);

                layout_bill.setVisibility(View.VISIBLE);
                btn_discount.setVisibility(View.VISIBLE);
            }
        });


        billCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlag = 1;
                amountPaidTxtview1.setText("Cash");
                strPayment_method = "Cash";
                amountPaid.setText(tatal_amount.getText().toString());
                billCard.setVisibility(View.GONE);
                billPaytm.setVisibility(View.GONE);
                billUserOnline.setVisibility(View.GONE);
            }
        });

        billCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlag = 2;
                strPayment_method = "Card";
                amountPaidTxtview1.setText("Card");
                amountPaid.setText(tatal_amount.getText().toString());
                billCash.setVisibility(View.GONE);
                billPaytm.setVisibility(View.GONE);
                billUserOnline.setVisibility(View.GONE);
            }
        });

        billPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlag = 3;
                strPayment_method = "Patym";
                amountPaidTxtview1.setText("Patym");
                amountPaid.setText(tatal_amount.getText().toString());
                billCash.setVisibility(View.GONE);
                billCard.setVisibility(View.GONE);
                billUserOnline.setVisibility(View.GONE);
            }
        });

        billUserOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFlag = 4;
                strPayment_method = "Other";
                amountPaidTxtview1.setText("Other");
                amountPaid.setText(tatal_amount.getText().toString());
                billCash.setVisibility(View.GONE);
                billPaytm.setVisibility(View.GONE);
                billCard.setVisibility(View.GONE);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = Double.parseDouble(tatal_amount.getText().toString());
                if (saveFlag == 0) {
                    Toast.makeText(BillSectionActivity.this, "Please choose any paying gateway", Toast.LENGTH_SHORT).show();
                } else {
                    if (i > 0) {
                        try {
                           Boolean p= dbdisHelper.Emptythetable(strPrifTableNo , sessionManager.getKeyUserArea());
                           Log.e("dfndsfn", ""+p);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        Cursor localCusor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                        int due = Paper.book().read("total_pending");
                        int net = Paper.book().read("net_sale");
                        localCusor.moveToNext();
                        int amm = localCusor.getInt(6);
                        Paper.book().write("total_pending", due - amm);
                        Paper.book().write("net_sale", amm + net);
                        Cursor paymentCursor = orderDatabseHelper.getPaymentOptions();
                        float ammo = Float.parseFloat(tatal_amount.getText().toString());
                        if (paymentCursor.getCount() == 0) {
                            if (saveFlag == 1) {
                                orderDatabseHelper.insertPaymentOptions(ammo, 0, 0, 0);
                            } else if (saveFlag == 2) {
                                orderDatabseHelper.insertPaymentOptions(0, ammo, 0, 0);
                            } else if (saveFlag == 3) {
                                orderDatabseHelper.insertPaymentOptions(0, 0, ammo, 0);
                            } else if (saveFlag == 4) {
                                orderDatabseHelper.insertPaymentOptions(0, 0, 0, ammo);
                            }
                        } else {
                            paymentCursor.moveToNext();
                            float precash = paymentCursor.getFloat(1);
                            float precard = paymentCursor.getFloat(2);
                            float prepaytm = paymentCursor.getFloat(3);
                            float preother = paymentCursor.getFloat(4);
                            if (saveFlag == 1) {
                                orderDatabseHelper.updatePaymentOptions(ammo + precash, precard, prepaytm, preother);
                            } else if (saveFlag == 2) {
                                orderDatabseHelper.updatePaymentOptions(precash, ammo + precard, prepaytm, preother);
                            } else if (saveFlag == 3) {
                                orderDatabseHelper.updatePaymentOptions(precash, precard, ammo + prepaytm, preother);
                            } else if (saveFlag == 4) {
                                orderDatabseHelper.updatePaymentOptions(precash, precard, prepaytm, ammo + preother);
                            }
                        }
                        orderDatabseHelper.updateOnFinishItem(variables.selecetd_table_data.getT_id(), 0, "Completed", variables.tableNumber);
                        orderDatabseHelper.trancateFreeTableItems(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                        Cursor tableDetailsCursor = helper.getTableDetails(variables.selecetd_table_data.getT_id());
                        if (tableDetailsCursor.getCount() != 0) {
                            tableDetailsCursor.moveToNext();
                            if (!tableDetailsCursor.getString(8).equals("yes")) {
                                helper.updateTableStatus(variables.selecetd_table_data.getT_id(), "free");
                            } else {
                                if (variables.tableNumber.equals("1")) {
                                    orderDatabseHelper.updateOrderDetailsSplitNumber(variables.selecetd_table_data.getT_id(), "1", "2");
                                    orderDatabseHelper.updateATableItemOrderInformationSplitNumber(variables.selecetd_table_data.getT_id(), "1", "2");
                                } else {

                                }
                                helper.updateSplittedStatus(variables.selecetd_table_data.getT_id(), "no", variables.selected_waiter_data.getWaiter_id(), "");
                            }
                        }

                        variables.selecetd_table_data = null;
                        variables.selected_waiter_data.setDiscount_amo("0.0");
                        getDataForServer();
                        startActivity(new Intent(BillSectionActivity.this, DrawerActivity.class));
                        finish();
                    } else {
                        Toast.makeText(BillSectionActivity.this, "Amount Total Is Zero(0)", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
             //    tmp = InsecureBluetooth.createRfcommSocketToServiceRecord(device, "00001101-0000-1000-8000-00805F9B34FB", true);
                 mSocket = con_dev.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                 final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                 device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                 return (BluetoothSocket) m.invoke(device, MY_UUID);
                 }
                 catch (Exception e)
                 {
                     Log.e(TAG, "Could not create Insecure RFComm Connection",e);
                 }
                 } return con_dev.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            }


    private void getWaiterData() {
        Cursor local = null;
        Cursor localWD = null;
        local = orderDatabseHelper.getWaiterWithDiscount();

    }

    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(576);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        //
        sendData = pg.printDraw();
        monceService.write(sendData);   //???byte??????
        Log.d("????????", "" + sendData.length);
    }


    private void gettingAllOrder() {
        Cursor localCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
        int i = 1;
        billSectionOrderList.clear();
        while (localCursor.moveToNext()) {
            if (localCursor.getInt(4) != 0) {
                if (localCursor.getInt(3) == 0) {
                    String item_id = localCursor.getString(1);
                    String item_name = localCursor.getString(2);
                    int item_quantity = localCursor.getInt(4);
                    float item_sgst_tax = localCursor.getFloat(6);
                    float item_cgst_tax = localCursor.getFloat(7);
                    float item_igst_tax = localCursor.getFloat(8);
                    float item_vat_tax = localCursor.getFloat(13);
                    showdialog(item_id, item_name, item_quantity, item_sgst_tax, item_cgst_tax, item_igst_tax, item_vat_tax);
                    break;
                }
                else
                    {
                    totalInt = +localCursor.getInt(3);
                    Log.e("Toatl", totalInt + "");
                    billSectionOrderList.add(new kitchenOrderItem(
                                    localCursor.getString(1),
                                    localCursor.getString(2),
                                    i++,
                                    localCursor.getInt(3),
                                    localCursor.getInt(4),
                                    localCursor.getInt(5),
                                    localCursor.getInt(6),
                                    localCursor.getInt(7),
                                    localCursor.getInt(8),
                                    localCursor.getInt(9),
                                    localCursor.getInt(10),
                                    localCursor.getInt(11),
                                    localCursor.getInt(13),
                                    localCursor.getInt(14)

                            )
                    );
                }
            }
        }
    }

    private void showdialog(final String item_id, final String item_name, final int item_quantity, final float sgstTax, final float cgstTax, final float igstTax, final float vatTax) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etUsername.getText().toString();
                int tt = Integer.valueOf(user) * item_quantity;
                orderDatabseHelper.Updateitem(item_id, String.valueOf(tt), user);
                billItemAdapter.notifyDataSetChanged();
                finish();
                startActivity(getIntent());

                Toast.makeText(getBaseContext(), "price: " + item_name + item_id, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private void updateItemRate(String item_id, int newRate, String item_name, int item_quantity, float sgstTax, float cgstTax, float igstTax, float vatTax) {
        if (newRate != 0) {
            orderDatabseHelper.updateTableItemOrderInformationTableRate(variables.selecetd_table_data.getT_id(),
                    variables.tableNumber,
                    newRate,
                    item_id);

            int price = newRate * item_quantity;

            if (vatTax != 0) {

                float tax = newRate * item_quantity * vatTax / 100;
                orderDatabseHelper.updatTableItemOrderInformation(
                        variables.selecetd_table_data.getT_id(),
                        item_id,
                        item_name,
                        newRate,
                        item_quantity,
                        price,
                        0,
                        0,
                        0,
                        tax,
                        variables.tableNumber
                );

                Cursor localCursor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                if (localCursor.getCount() != 0) {
                    while (localCursor.moveToNext()) {
                        int oldAmount = localCursor.getInt(6);
                        int oldDue = localCursor.getInt(7);
                        float oldTax = localCursor.getFloat(11);
                        orderDatabseHelper.updateOrderListItem(
                                variables.selecetd_table_data.getT_id(),
                                oldAmount + price,
                                oldDue + price,
                                "running",
                                oldTax + tax,
                                variables.tableNumber
                        );
                        Float y;
                        float x = (float) Math.floor(oldAmount + oldTax + price + tax);
                        y = (bill_amount + bill_tax - x);
                        Double roundoff = BigDecimal.valueOf(y)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();

                        roundOff.setText(String.valueOf(roundoff));
                        try {
                            if(!alreadyDiscount.isEmpty())
                            {
                                bill_discount = Float.parseFloat(alreadyDiscount);
                            }else{
                                bill_discount = Float.parseFloat(strDiscount);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        Double total_tax = BigDecimal.valueOf(bill_tax)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();

                        tatal_amount.setText(String.valueOf(x));
                        amount.setText(String.valueOf(oldAmount + price));
                        txttax.setText(String.valueOf(oldTax + tax));

                    }
                }


            } else {
                float tax = newRate * item_quantity * (sgstTax + cgstTax + igstTax) / 100;
                float sgsttax = newRate * item_quantity * (sgstTax) / 100;
                float cgsttax = newRate * item_quantity * (cgstTax) / 100;
                float igsttax = newRate * item_quantity * (igstTax) / 100;
                orderDatabseHelper.updatTableItemOrderInformation(
                        variables.selecetd_table_data.getT_id(),
                        item_id,
                        item_name,
                        newRate,
                        item_quantity,
                        price,
                        sgsttax,
                        cgsttax,
                        igsttax,
                        0,
                        variables.tableNumber
                );

                Cursor localCursor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                if (localCursor.getCount() != 0) {
                    while (localCursor.moveToNext()) {
                        int oldAmount = localCursor.getInt(6);
                        int oldDue = localCursor.getInt(7);
                        float oldTax = localCursor.getFloat(11);
                        orderDatabseHelper.updateOrderListItem(
                                variables.selecetd_table_data.getT_id(),
                                oldAmount + price,
                                oldDue + price,
                                "running",
                                oldTax + tax,
                                variables.tableNumber
                        );

                        Float y;
                        float x = (float) Math.floor(oldAmount + oldTax + price + tax);
                        y = (bill_amount + bill_tax - x);
                        Double roundoff = BigDecimal.valueOf(y)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();

                        roundOff.setText(String.valueOf(roundoff));
                     if(!alreadyDiscount.isEmpty())
                     {
                         dicount.setText(String.valueOf(alreadyDiscount));
                     }else{

                         dicount.setText(String.valueOf(strDiscount));
                     }


                        Double total_tax = BigDecimal.valueOf(bill_tax)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();

                        tatal_amount.setText(String.valueOf(x));
                        amount.setText(String.valueOf(oldAmount + price));
                        txttax.setText(String.valueOf(oldTax + tax));
                    }
                }

            }
            Toast.makeText(this, "Price added", Toast.LENGTH_SHORT).show();
//            gettingAllOrder();
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //??????
                            Toast.makeText(BillSectionActivity.this, "Connect successful", Toast.LENGTH_SHORT).show();

                            break;
                        case BluetoothService.STATE_CONNECTING:  //????????
                            Log.d("????????", "????????.....");
                            Log.e("connecting", "still conn");
//                            if (con_dev.getBondState() == con_dev.BOND_BONDED) {
//
//                                Log.d(TAG, con_dev.getName());
//                                BluetoothSocket mSocket = null;
//                                try {
//                                    mSocket = con_dev.createInsecureRfcommSocketToServiceRecord(MY_UUID);
//                                } catch (IOException e1) {
//                                    // TODO Auto-generated catch block
//                                    Log.d(TAG, "socket not created");
//                                    e1.printStackTrace();
//                                }
//                                try {
//                                    mSocket.connect();
//
//                                } catch (IOException e) {
//                                    try {
//                                        mSocket.close();
//                                        Log.d(TAG, "Cannot connect");
//                                    } catch (IOException e1) {
//                                        Log.d(TAG, "Socket not closed");
//                                        e1.printStackTrace();
//                                    }
//                                }
//                            }
                            break;
                        case BluetoothService.STATE_LISTEN:     //????????????
                        case BluetoothService.STATE_NONE:
                            Log.d("????????", "???????.....");
                            Log.e("connect", "none");
//                            mService.startDiscovery();
//                            if (mService.isBTopen() && (mService.getState() == 1)) {
//                                BluetoothSocket mSocket = null;
//                                try {
//                                    Log.e("connect", "service is open");
//                                    mSocket = con_dev.createInsecureRfcommSocketToServiceRecord(MY_UUID);
//                                } catch (IOException e1) {
//                                    // TODO Auto-generated catch block
//                                    Log.d(TAG, "socket not created");
//                                    e1.printStackTrace();
//                                }
//                                try {
//
//                                    if (mService.isAvailable() == false) {
//                                        Toast.makeText(BillSectionActivity.this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//                                    }
//                                    SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
//                                    String address = sharedPreferences.getString("device", "");
//                                    if (address == null || address.equals("")) {
//
//                                    } else {
//                                        con_dev = mService.getDevByMac(address);
////            try {
////                createBluetoothSocket(con_dev);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//                                        mService.connect(con_dev);
//                                        mSocket.connect();
//
//                                    }
//                                } catch (IOException e) {
//                                    try {
//                                        mSocket.close();
//                                        e.printStackTrace();
//                                        Log.d(TAG, "Cannot connect");
//                                    } catch (IOException e1) {
//                                        Log.d(TAG, "Socket not closed");
//                                        e1.printStackTrace();
//                                    }
//                                }
//                            }


                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //????????????
                    Toast.makeText(BillSectionActivity.this, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();

                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //?????????
                    //   Toast.makeText(NewActivity.this, "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BillSectionActivity.this, Delay_From_Bill.class);
        startActivity(intent);
        finish();
//        IsFinish("Want to close app?");
//        IsFinish("");

    }

    public void IsFinish(String alertmessage) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        /*Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                        Intent intent = new Intent(BillSectionActivity.this, DrawerActivity.class);
                        startActivity(intent);
                        finish();//                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        //finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(BillSectionActivity.this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void getDataForServer() {
        Cursor localCursor = orderDatabseHelper.getCompletedOrderList();
        while (localCursor.moveToNext()) {
            Customer_name = localCursor.getString(5);
            Dinenig_Area = localCursor.getString(3);
            Table_No = localCursor.getString(10);
        }
//        Customer_name = "Hello";
        Waiter_Name = variables.selected_waiter_data.getW_name();
        Date_Time = currentDateTime;
        Beofer_tax_amount = Double.parseDouble(amount.getText().toString());
        Bill_no = recpNo.getText().toString();
        Tax_amount = Double.parseDouble(txttax.getText().toString());
        SGST = 2.5;
        CGST = 2.5;
        Total_Amount = Double.parseDouble(tatal_amount.getText().toString());
        if (discount_amount.getText().toString().equals("")) {
            Discount_amount = 0.0;
        } else {
            Discount_amount = Double.parseDouble(discount_amount.getText().toString());
        }
//        Discount_amount = Double.parseDouble();
        Round_off_amount = Double.parseDouble(roundOff.getText().toString());
        Payment_method = strPayment_method;
        menu_items.clear();
        for (kitchenOrderItem orderItem : billSectionOrderList) {
            stritemName = orderItem.getMname();
            int i = orderItem.getQuantity();
            int i1 = orderItem.getRate();
            stritemQty = String.valueOf(i);
            stritemRate = String.valueOf(i1);
            stritemTax = String.valueOf(orderItem.getTotalVatTax());
            int i2 = i * i1;
            stritemAmount = String.valueOf(i2);
            menu_items.add(new Menu_Items(stritemName, stritemQty, stritemRate, stritemTax, stritemAmount));
            orderDatabseHelper.setMenuItem(Bill_no, stritemName, stritemQty, stritemRate, stritemTax, stritemAmount);
        }
        for (int i = 0; i < menu_items.size(); i++) {
            arr.put(menu_items.get(i).getJSONObject());
            String s = menu_items.get(i).getJSONObject().toString();
        }
        //---------------- Call AsyncTask-----------------
        if (CheckConnection) {
            new PostData(Bill_no, Customer_name, Dinenig_Area, Table_No, Waiter_Name, Date_Time, Beofer_tax_amount + "", Tax_amount + "", SGST + ""
                    , CGST + "", Total_Amount + "", Discount_amount + "", Round_off_amount + "", Payment_method, arr).execute();
        } else {
            Cursor cursorMenu = null;
            orderDatabseHelper.getOrderDetails(Bill_no, Customer_name, Dinenig_Area, Table_No, Waiter_Name, Date_Time, Beofer_tax_amount + "", Tax_amount + "", SGST + ""
                    , CGST + "", Total_Amount + "", Discount_amount + "", Round_off_amount + "", Payment_method);
            sessionManager.setDiscount("0");
        }
    }

    // Method to change the text status
    public void changeTextStatus(boolean isConnected) {

    }
    //---------------------------------------------------------

    public class PostData extends AsyncTask<String, Void, String> {

        private String Customer_name, Waiter_Name, Date_Time, Dinenig_Area, Table_No, Menu_Name, Payment_method;
        private String Bill_no = "";
        private String Discount_amount, Round_off_amount, Qty, Rate, Tax, Amount;
        private String Beofer_tax_amount, Tax_amount, SGST, CGST, Total_Amount;
        private JSONArray arr = new JSONArray();
        private String serial_no, reciept_no;

        public PostData(String bill_no, String customer_name, String dinenig_area, String table_no, String waiter_name, String date_time
                , String beofer_tax_amount, String tax_amount, String sGST, String cGST, String total_Amount, String discount_amount, String round_off_amount, String payment_method, JSONArray arr) {
            Bill_no = bill_no;
            Customer_name = customer_name;
            Dinenig_Area = dinenig_area;
            Table_No = table_no;
            Waiter_Name = waiter_name;
            Date_Time = date_time;
            Beofer_tax_amount = beofer_tax_amount;
            Tax_amount = tax_amount;
            SGST = sGST;
            CGST = cGST;
            Total_Amount = total_Amount;
            Discount_amount = discount_amount;
            Round_off_amount = round_off_amount;
            Payment_method = payment_method;
            this.arr = arr;
        }

        protected void onPreExecute() {
           /* dialog = new ProgressDialog(BillSectionActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(false);
            dialog.show();*/

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://twors.in/POS/Webservices/add_order");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Bill_No", Bill_no);
                postDataParams.put("Customer_name", Customer_name);
                postDataParams.put("Dinenig_Area", Dinenig_Area);
                postDataParams.put("Table_No", Table_No);
                postDataParams.put("Waiter_Name", Waiter_Name);
                postDataParams.put("Date_Time", Date_Time);
                postDataParams.put("Beofer_tax_amount", Beofer_tax_amount);
                postDataParams.put("Tax_amount", Tax_amount);
                postDataParams.put("SGST", SGST);
                postDataParams.put("CGST", CGST);
                postDataParams.put("Total_Amount", Total_Amount);
                postDataParams.put("Discount_amount", Discount_amount);
                postDataParams.put("Round_off_amount", Round_off_amount);
                postDataParams.put("Payment_method", Payment_method);
                postDataParams.put("menulist", arr);
                postDataParams.put("outlet_id", AppPrefences.getOutlet(BillSectionActivity.this));

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("{{")) {
                    sessionManager.setDiscount("0");
                    Intent intent = new Intent(BillSectionActivity.this, DrawerActivity.class);
                    startActivity(intent);
                    Toast.makeText(BillSectionActivity.this, "Oredr Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BillSectionActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    public class PostDataForOffline extends AsyncTask<String, Void, String> {
        int count_PostData;
        private String Customer_name, Waiter_Name, Date_Time, Dinenig_Area, Table_No, Menu_Name, Payment_method;
        private String Bill_no = "";
        private String Discount_amount, Round_off_amount, Qty, Rate, Tax, Amount;
        private String Beofer_tax_amount, Tax_amount, SGST, CGST, Total_Amount;
        private JSONArray arr = new JSONArray();
        private String serial_no, reciept_no;

        public PostDataForOffline(String bill_no, String customer_name, String dinenig_area, String table_no, String waiter_name, String date_time
                , String beofer_tax_amount, String tax_amount, String sGST, String cGST, String total_Amount, String discount_amount, String round_off_amount, String payment_method, JSONArray arr) {
            Bill_no = bill_no;
            Customer_name = customer_name;
            Dinenig_Area = dinenig_area;
            Table_No = table_no;
            Waiter_Name = waiter_name;
            Date_Time = date_time;
            Beofer_tax_amount = beofer_tax_amount;
            Tax_amount = tax_amount;
            SGST = sGST;
            CGST = cGST;
            Total_Amount = total_Amount;
            Discount_amount = discount_amount;
            Round_off_amount = round_off_amount;
            Payment_method = payment_method;
            this.arr = arr;
        }


        protected void onPreExecute() {
            count_PostData++;
           /* dialog = new ProgressDialog(BillSectionActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(false);
            dialog.show();*/
            Log.e("Counting PostData", count_PostData + "");

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://twors.in/POS/Webservices/add_order");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Bill_No", Bill_no);
                postDataParams.put("Customer_name", Customer_name);
                postDataParams.put("Dinenig_Area", Dinenig_Area);
                postDataParams.put("Table_No", Table_No);
                postDataParams.put("Waiter_Name", Waiter_Name);
                postDataParams.put("Date_Time", Date_Time);
                postDataParams.put("Beofer_tax_amount", Beofer_tax_amount);
                postDataParams.put("Tax_amount", Tax_amount);
                postDataParams.put("SGST", SGST);
                postDataParams.put("CGST", CGST);
                postDataParams.put("Total_Amount", Total_Amount);
                postDataParams.put("Discount_amount", Discount_amount);
                postDataParams.put("Round_off_amount", Round_off_amount);
                postDataParams.put("Payment_method", Payment_method);
                postDataParams.put("menulist", arr);
                postDataParams.put("outlet_id", AppPrefences.getOutlet(BillSectionActivity.this));

                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                if (result.equals("{{")) {
//                    Intent intent = new Intent(BillSectionActivity.this, DrawerActivity.class);
//                    startActivity(intent);

                    Toast.makeText(BillSectionActivity.this, "Offline Oredr Saved Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BillSectionActivity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        MyApplication.activityPaused();// On Pause notify the Application
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (mService.isBTopen() == false) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//        }

        MyApplication.activityResumed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mService != null)
//            mService.stop();
//        mService = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:      //?????????
                if (resultCode == Activity.RESULT_OK) {   //?????????
                    Toast.makeText(BillSectionActivity.this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CONNECT_DEVICE:     //????????????????
                if (resultCode == Activity.RESULT_OK) {   //?????????????????????
                    SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
                    String address = sharedPreferences.getString("device", "");  //??????????????mac???
                    con_dev = monceService.getDevByMac(address);

                    monceService.connect(con_dev);
                }
                break;
        }
    }
}
