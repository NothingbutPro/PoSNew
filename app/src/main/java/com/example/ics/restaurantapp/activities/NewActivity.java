package com.example.ics.restaurantapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.CustomerDatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.Mid_Delay_Activity;
import com.example.ics.restaurantapp.ModelDB.CustomerItem;
import com.example.ics.restaurantapp.ModelDB.cardOrderItem;
import com.example.ics.restaurantapp.ModelDB.foodDetailsItem;
import com.example.ics.restaurantapp.ModelDB.kitchenOrderItem;
import com.example.ics.restaurantapp.ModelDB.tableItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.SharedPreference.SessionManager;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.adapter.CardAdapter;
import com.example.ics.restaurantapp.adapter.CustomerAdapter;
import com.example.ics.restaurantapp.adapter.KotAdapter;
import com.example.ics.restaurantapp.adapter.MenuAdapter;
import com.example.ics.restaurantapp.adapter.RecyclerAdapter;
import com.example.ics.restaurantapp.fregment.Search_User_Fragment;
import com.example.ics.restaurantapp.handler.HttpHandler;
import com.example.ics.restaurantapp.model.ForWaiterDetails;
import com.example.ics.restaurantapp.model.Kot_Items_Bill_Node;
import com.example.ics.restaurantapp.model.Kot_items;
import com.example.ics.restaurantapp.model.Model_Item;
import com.example.ics.restaurantapp.model.PrintModel;
import com.example.ics.restaurantapp.model.WaiterDiscountModal;
import com.example.ics.restaurantapp.pockdata.PocketPos;
import com.example.ics.restaurantapp.receiver.BlueServiceFree;
import com.example.ics.restaurantapp.receiver.BlueServiceOnce;
import com.example.ics.restaurantapp.util.DateUtil;
import com.example.ics.restaurantapp.util.FontDefine;
import com.example.ics.restaurantapp.util.Printer;
import com.example.ics.restaurantapp.util.StringUtil;
import com.hoin.btsdk.BluetoothService;
import com.hoin.btsdk.PrintPic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.widget.Toolbar;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import io.paperdb.Paper;

import static com.example.ics.restaurantapp.receiver.BlueServiceOnce.monceService;

public class NewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Model_Item> groceryList = new ArrayList<>();
    private RecyclerView re_horizontal;
    private RecyclerView card_recyclerview;
    String prev_item = "None";
    int onetime=1;
    private RecyclerView kot_recyclerview;
    private RecyclerView customerRecycler;
    SessionManager kotManager;
    String kot_before = "";
    private RecyclerAdapter recyclerAdapter;
    private CardAdapter cardAdapter;
    private LinearLayoutManager linearLayoutManager;
    private KotAdapter kotAdapter;
    TextView text, all_list, tet_food, total_rupiee, bt_card, bt_kot, w_name1, btn_all, guestname, itm, totalPrice, table_name;
    ListView list_all, listfood;
    Button btn_split, btn_order, btn_shift, btn_finish, save_and_send_kitchan, shift_Update, shift_Cancel;
    LinearLayout card_section, kot_section, card_kot, card1, kot1;
    GridView grid_food;
    String server_url;
    String Outlet;
    String Category;
    String Btn_all;
    String Group_item;
    String item_kot ;
    String name;
    ImageView add_, icon_add;
    String All = "ALL";
    String Name;
    String wname, AddMob, AddName, AddAdress;
    String Selected_table;
    ArrayAdapter ad_table;
    ArrayList<String> t_list;
    ArrayList<String> AllMenuList;
    ArrayList<String> submenu_list = new ArrayList<String>();
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<HashMap<String, String>> card_list;
    ArrayList<HashMap<String, String>> kot_list;
    private ArrayList<String> arrayList;
    ArrayList<HashMap<String, String>> d_list;
    ArrayList<HashMap<String, String>> k_list;
    ArrayList<HashMap<String, String>> arl;
    private FragmentManager fragmentManager;
    ImageView btn_close;
    EditText m_number, address, shift_table, takename;
    private HashMap<String, String> selectedHashItem = new HashMap<>();
    ArrayList<HashMap<String, String>> selectedHashItemList = new ArrayList<>();
    private String sfloorName = "";
    GridView grid_tables;
    ArrayList<String> table_list;
    Button shift_cancel, shift_update, t_submit;
    Spinner Spin_table;
    String Dine_Area;
    SearchView search;
    private String floor = "";
    public static TextView txtTotal;
    SearchView menu_search;
    ListView listview_search;
    ArrayList<String> searchList;
    ArrayAdapter<String> adapter;
    String SearchValue;
    private String strWaiterID;
    private String strWaiterName;
    private String strTableNo;
    private String strDiscount = "0.0";
    TextView exist_table;
    private Toolbar toolbar_;
    public static Button btn_modify;
    public static LinkedList<Kot_Items_Bill_Node> kot_items_bill_nodes = new LinkedList<>();
    LinkedList<Kot_Items_Bill_Node> kot_items_bill_nodes_clone = new LinkedList<>();
    String msg;
    HashMap<Integer  , com.example.ics.restaurantapp.model.Kot_items>Kot_items = new HashMap<>();
    private ArrayList<kitchenOrderItem> billSectionOrderList = new ArrayList<>();

    //----------------------printer Code-----------------------------------

    Button btnSearch;
    Button btnSendDraw;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    EditText edtPrint;

    private static final int REQUEST_ENABLE_BT = 2;
    static BluetoothService  mbService = null;
    BluetoothDevice con_dev = null;
    private View qrCodeBtnSend;
    private static final int REQUEST_CONNECT_DEVICE = 1;

    //--------------------------------------------------------

    ArrayList<String> local_menu_list;
    DatabaseHelper helpher;
    OrderDatabseHelper orderDatabseHelper;
    CustomerDatabaseHelper customerHelper;
    List<foodDetailsItem> localFoodDetailsList;
    List<cardOrderItem> localAllCardOrderFoodList;
    List<kitchenOrderItem> localAllKitchenOrderFoodlList;
    List<kitchenOrderItem> localAllKitchenOrderFoodlListbill = new ArrayList<>();
    List<foodDetailsItem> localSearchedFoodDetails;
    List<String> available_table_list;
    List<tableItem> available_table_all_data_list;

    Dialog dialog;
    BluetoothAdapter mBluetoothAdapter;
    private static final String TAG = "NewActivity";

    int localCardOrderFlag = 0;
    int localKOTorderFlag = 0;
    private int flagOrderList = 0;

    private int splitflag = 1;
    private int insertFlag = 1;
    private ArrayList<CustomerItem> list = new ArrayList<>();
    private String count = "Stop";
    private String strWaiterId;
    private String strTabNo;
    private String strNewWaitName;
    private String strNewWaiterId;
    private ArrayList<WaiterDiscountModal> discountModals = new ArrayList<>();
    private String bill;
    TextView chn_head_footer;

    @Override
    protected void onStart() {
        super.onStart();

    }


  /*  @Override
    protected void onDestroy() {
        super.onDestroy();

    }*/

    @Override
    protected void onStop() {
        super.onStop();
        orderDatabseHelper.trancateCardOrder();
        //orderDatabseHelper.trancateKotOrder();
        variables.kot_count = 0;


        localAllCardOrderFoodList.clear();
        cardAdapter = new CardAdapter(NewActivity.this, localAllCardOrderFoodList);
        card_recyclerview = (RecyclerView) findViewById(R.id.card_recyclerview);
        card_recyclerview.setHasFixedSize(true);
        LinearLayoutManager cardlinearLayoutManager = new LinearLayoutManager(NewActivity.this);
        cardlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        card_recyclerview.setLayoutManager(cardlinearLayoutManager);
        card_recyclerview.setAdapter(cardAdapter);
        localAllKitchenOrderFoodlListbill = localAllKitchenOrderFoodlList;
        localAllKitchenOrderFoodlList.clear();
        kotAdapter = new KotAdapter(NewActivity.this, localAllKitchenOrderFoodlList);
        kot_recyclerview = (RecyclerView) findViewById(R.id.kot_recyclerview);
        kot_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        kot_recyclerview.setLayoutManager(linearLayoutManager);
        kot_recyclerview.setAdapter(kotAdapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_menu);
        //  startService(new Intent(NewActivity.this , BlueServiceOnce.class));
        toolbar_ = (Toolbar) findViewById(R.id.toolbar_);
        startService(new Intent(this , BlueServiceOnce.class));
        toolbar_.setNavigationIcon(R.drawable.ic_arrow);
        toolbar_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(NewActivity.this, "back clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        kotManager = new SessionManager(this);
        chn_head_footer = findViewById(R.id.chn_head_footer);
        chn_head_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_cahnge = new Intent(NewActivity.this , Header_Footer_Act.class);
                startActivity(to_cahnge);
            }
        });
//        mbService = new BluetoothService(NewActivity.this, mHandler);
//        if (mbService.isAvailable() == false) {
//            Toast.makeText(NewActivity.this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//        }
//        toolbar_.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        // setSupportActionBar(toolbar_);
        //--------------------------------------------------------------------------------
        Paper.init(this);
        //--------------------------------------------------------------------------------


        init();

        //-------------------------------------------

        //Spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
      /*  ad_table = new ArrayAdapter(this, R.layout.custom_spinner, t_list);
        ad_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_table.setAdapter(ad_table);
        Spin_table.setOnItemSelectedListener(this);*/

        floor = AppPrefences.getFloor(NewActivity.this);
        //--------------------------------------------------------

        Intent intent = getIntent();
        String w_name = intent.getStringExtra("waiterlist");

        w_name1.setText(variables.selected_waiter_data.getW_name());

        guestname.setText(AppPrefences.getGuest(NewActivity.this));

        d_list = new ArrayList<HashMap<String, String>>();
        k_list = new ArrayList<HashMap<String, String>>();

        Outlet = AppPrefences.getOutlet(NewActivity.this);
//

        helpher = new DatabaseHelper(this);
        orderDatabseHelper = new OrderDatabseHelper(this);
        customerHelper = new CustomerDatabaseHelper(this);
        getAllmenuData();

        //----------------------------------------------------------------
        /**
         * clearing database card and kot table;
         */
        orderDatabseHelper.trancateCardOrder();
        orderDatabseHelper.trancateKotOrder();
        variables.kot_count = 0;


        //---------------------------------------------------------------

        Cursor localCurrentDataCursor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);
        if (localCurrentDataCursor.getCount() == 0) {
            variables.total_price = 0;
        } else {
            localCurrentDataCursor.moveToNext();
            variables.total_price = localCurrentDataCursor.getInt(6);
            NewActivity.txtTotal.setText(String.valueOf(variables.total_price));
        }


        Cursor localCursor = helpher.getAllDataTable4();
        while (localCursor.moveToNext()) {
            local_menu_list.add(localCursor.getString(1));
        }

        setCategoryList(local_menu_list);


        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllmenuData();

            }
        });

        add_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* customerHelper.insertNewCustomer("111", "aaa", "aaa");
                customerHelper.insertNewCustomer("222", "bbb", "bbb");
                customerHelper.insertNewCustomer("333", "ccc", "ccc");
                customerHelper.insertNewCustomer("444", "ddd", "ddd");*/
                Search_User_Fragment signupFragment = new Search_User_Fragment();
                list = customerHelper.getValues();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("List", list);
                signupFragment.setArguments(bundle);
                signupFragment.show(fragmentManager, "Sample Fragment");
                Log.i(TAG, "click on save button");
                Log.e("Hello", "Hello");
                Toast.makeText(NewActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

       /* add_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(NewActivity.this);
                dialog.setContentView(R.layout.search_dialog);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                search = (SearchView) dialog.findViewById(R.id.search);

            *//*    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemsearchList);
                seerchList.setAdapter(adapter);*//*

                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        Cursor localCustomerCursor = customerHelper.getCustomerSearchResult(s);
                      *//*  Cursor localCustomerCursor2 = customerHelper.getAllCustomers();
                        Cursor localCustomer = customerHelper.getAllData();*//*
                        list.clear();
                        if (localCustomerCursor.getCount() != 0) {
                            *//*Toast.makeText(NewActivity.this, "Result found" + localCustomerCursor.getCount(), Toast.LENGTH_SHORT).show();
                            while (localCustomerCursor.moveToNext()) {
                                String mob = localCustomerCursor.getString(0);
                                arrayList.add(name);
                                String name = localCustomerCursor.getString(1);
                                String add = localCustomerCursor.getString(2);
                                list.add(new CustomerItem(name, mob, add));
                            }*//*
                            list=customerHelper.getValues();
                            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view1 = inflater.inflate(R.layout.search_dialog, null);
                            customerRecycler = (RecyclerView) view1.findViewById(R.id.customerRecycler);
                            customerRecycler.setLayoutManager(linearLayoutManager);
                            customerRecycler.setHasFixedSize(true);
                            CustomerAdapter cadapter = new CustomerAdapter(NewActivity.this, list);
                            customerRecycler.setAdapter(cadapter);
                        } else {
                            Toast.makeText(NewActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });


                icon_add = (ImageView) dialog.findViewById(R.id.icon_add);
                btn_close = (ImageView) dialog.findViewById(R.id.btn_close);

                icon_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // custom dialog
                        final Dialog dialog = new Dialog(NewActivity.this);
                        dialog.setContentView(R.layout.dialog_take_away);
                        dialog.setTitle("Title...");

                        // set the custom dialog components - text, image and button
                        m_number = (EditText) dialog.findViewById(R.id.m_number);
                        takename = (EditText) dialog.findViewById(R.id.name);
                        address = (EditText) dialog.findViewById(R.id.address);
                        t_submit = (Button) dialog.findViewById(R.id.t_submit);

                        t_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddMob = m_number.getText().toString();
                                AddName = takename.getText().toString();
                                AddAdress = address.getText().toString();
                                Cursor customerCursor = customerHelper.getCustomer(AddMob);
                                if (customerCursor.getCount() != 0) {
                                    Toast.makeText(NewActivity.this, "You are already registered!! \n Please try with another mobile number", Toast.LENGTH_SHORT).show();
                                } else {
                                    customerHelper.insertNewCustomer(
                                            AddMob,
                                            AddName,
                                            AddAdress
                                    );
                                    Toast.makeText(NewActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                }
                            }
                        });

                        dialog.show();
                    }
                });

                dialog.show();
            }

        });*/

        //-------------------------------------------------

        save_and_send_kitchan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date currentDate = Calendar.getInstance().getTime();
                int hr = currentDate.getHours();
                int min = currentDate.getMinutes();
                int sec = currentDate.getSeconds();
                float total_tax = 0;

                Cursor localCursor = orderDatabseHelper.getAllCardOrderData();

//                Cursor localKOTCursor = rderDatabseHelper.getAllKitchenOrderData();
                if (localCursor.getCount() != 0) {
                    int p=0;
                    Cursor localCurrentDataCursor = orderDatabseHelper.getDataOfSelectedTable(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                    if (localCurrentDataCursor.getCount() == 0) {
                        while (localCursor.moveToNext()) {
                            if (localCursor.getInt(3) != 0) {

                                //    Kot_items[i] =    localCursor.getString(1);
                                //  Kot_items.put(p,new Kot_items(localCursor.getString(1),localCursor.getInt(2)));
                                orderDatabseHelper.insertTableItemOrderInformation(
                                        variables.selecetd_table_data.getT_id(),
                                        localCursor.getString(0),
                                        localCursor.getString(1),
                                        localCursor.getInt(2),
                                        localCursor.getInt(3),
                                        localCursor.getInt(4),
                                        localCursor.getFloat(8),
                                        localCursor.getFloat(9),
                                        localCursor.getFloat(10),
                                        localCursor.getFloat(11),
                                        localCursor.getFloat(12),
                                        localCursor.getFloat(13),
                                        variables.tableNumber,
                                        localCursor.getFloat(14),
                                        localCursor.getFloat(15),
                                        localCursor.getString(16),
                                        localCursor.getString(17),
                                        localCursor.getString(18),
                                        localCursor.getString(19),
                                        localCursor.getString(20),
                                        localCursor.getString(21)
                                );
                                if (localCursor.getFloat(14) == 0) {
                                    total_tax += localCursor.getFloat(11) + localCursor.getFloat(12) + localCursor.getFloat(13);
                                } else {
                                    total_tax += localCursor.getFloat(15);
                                }

                            }
                            p++;
                        }

                        orderDatabseHelper.insertOrderListItem(
                                ++variables.order_no,
                                ++variables.receipt_no,
                                "Dine In",
                                variables.selecetd_table_data.getDin_area(),
                                hr + ":" + min + ":" + sec,
                                "",
                                variables.total_price,
                                variables.total_price,
                                "running",
                                "Datta",
                                variables.selecetd_table_data.getT_id(),
                                total_tax,
                                variables.tableNumber,
                                0,
                                variables.selected_waiter_data.getWaiter_id()
                        );

                        orderDatabseHelper.updatReceiptAndOrderNoTable(
                                String.valueOf(variables.order_no),
                                String.valueOf(variables.receipt_no),
                                String.valueOf(variables.initial_receipt_no));

                        variables.initial_receipt_no = variables.receipt_no;

                        int due = Paper.book().read("total_pending");
                        Paper.book().write("total_pending", due + variables.total_price);
                    } else {
                        localCurrentDataCursor.moveToNext();
                        total_tax = localCurrentDataCursor.getFloat(11);

                        int due = Paper.book().read("total_pending");
                        Paper.book().write("total_pending", due + variables.total_price);


                        Log.d(TAG, "onClick: getting cad item count " + localCursor.getCount());
                        while (localCursor.moveToNext()) {
                            Log.d(TAG, " onClick: card count hellow ");
                            flagOrderList = 0;
                            Cursor localTableItemOrderInformationCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                            while (localTableItemOrderInformationCursor.moveToNext()) {
                                if (localCursor.getString(0).equals(localTableItemOrderInformationCursor.getString(1))) {

                                    orderDatabseHelper.updatTableItemOrderInformation(
                                            variables.selecetd_table_data.getT_id(),
                                            localCursor.getString(0),
                                            localCursor.getString(1),
                                            localCursor.getInt(2),
                                            localCursor.getInt(3) + localTableItemOrderInformationCursor.getInt(4),
                                            localCursor.getInt(4) + localTableItemOrderInformationCursor.getInt(5),
                                            localCursor.getFloat(11) + localTableItemOrderInformationCursor.getFloat(9),
                                            localCursor.getFloat(12) + localTableItemOrderInformationCursor.getFloat(10),
                                            localCursor.getFloat(13) + localTableItemOrderInformationCursor.getFloat(11),
                                            localCursor.getFloat(15) + localTableItemOrderInformationCursor.getFloat(14),
                                            variables.tableNumber
                                    );
                                    flagOrderList = 1;
                                    break;
                                }
                            }



                            if (flagOrderList == 0) {
                                if (localCursor.getInt(3) != 0) {
                                    orderDatabseHelper.insertTableItemOrderInformation(
                                            variables.selecetd_table_data.getT_id(),
                                            localCursor.getString(0),
                                            localCursor.getString(1),
                                            localCursor.getInt(2),
                                            localCursor.getInt(3),
                                            localCursor.getInt(4),
                                            localCursor.getFloat(8),
                                            localCursor.getFloat(9),
                                            localCursor.getFloat(10),
                                            localCursor.getFloat(11),
                                            localCursor.getFloat(12),
                                            localCursor.getFloat(13),
                                            variables.tableNumber,
                                            localCursor.getFloat(14),
                                            localCursor.getFloat(15),
                                            localCursor.getString(16),
                                            localCursor.getString(17),
                                            localCursor.getString(18),
                                            localCursor.getString(19),
                                            localCursor.getString(20),
                                            localCursor.getString(21)
                                    );
                                }

                            }
                            if (localCursor.getFloat(14) == 0) {
                                total_tax += localCursor.getFloat(11) + localCursor.getFloat(12) + localCursor.getFloat(13);
                            } else {
                                total_tax += localCursor.getFloat(15);
                            }

                        }
                        orderDatabseHelper.updateOrderListItem(
                                variables.selecetd_table_data.getT_id(),
                                hr + ":" + min + ":" + sec,
                                variables.total_price,
                                variables.total_price,
                                "running",
                                total_tax,
                                variables.tableNumber
                        );
                    }

                    orderDatabseHelper.trancateCardOrder();

                    localAllCardOrderFoodList.clear();
                    cardAdapter = new CardAdapter(NewActivity.this, localAllCardOrderFoodList);
                    card_recyclerview = (RecyclerView) findViewById(R.id.card_recyclerview);
                    card_recyclerview.setHasFixedSize(true);
                    LinearLayoutManager cardlinearLayoutManager = new LinearLayoutManager(NewActivity.this);
                    cardlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    card_recyclerview.setLayoutManager(cardlinearLayoutManager);
                    card_recyclerview.setAdapter(cardAdapter);

                    //variables.total_price_kot +=variables.total_price;
                    NewActivity.txtTotal.setText(String.valueOf(variables.total_price));

                    localCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                    localAllKitchenOrderFoodlListbill = localAllKitchenOrderFoodlList;
                    localAllKitchenOrderFoodlList.clear();
                    int i = 0;
                    if (localCursor.getCount() != 0) {
                        helpher.updateTableStatus(variables.selecetd_table_data.getT_id(), "allocated");
                    }
                    while (localCursor.moveToNext()) {
                        localAllKitchenOrderFoodlList.add(new kitchenOrderItem(
                                        localCursor.getString(1),
                                        localCursor.getString(2),
                                        ++i,
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
                                        localCursor.getInt(15)
                                )
                        );
                    }

                    Cursor localTableDetailsCurdor = helpher.getTableDetails(variables.selecetd_table_data.getT_id());
                    if (localTableDetailsCurdor.getCount() != 0) {
                        localTableDetailsCurdor.moveToNext();
                        if (!localTableDetailsCurdor.getString(8).equals("yes") & variables.tableNumber.equals("2")) {
                            helpher.updateSplittedStatus(
                                    variables.selecetd_table_data.getT_id(),
                                    "yes",
                                    variables.selected_waiter_data.getWaiter_id(),
                                    variables.selected_waiter_data.getWaiter_id()
                            );
                            splitflag = 1;
                        }
                    }


                    kotAdapter = new KotAdapter(NewActivity.this, localAllKitchenOrderFoodlList);
                    kot_recyclerview = (RecyclerView) findViewById(R.id.kot_recyclerview);
                    kot_recyclerview.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    kot_recyclerview.setLayoutManager(linearLayoutManager);
                    kot_recyclerview.setAdapter(kotAdapter);
//                        for(int px =0; p<localAllKitchenOrderFoodlListbill.size();px++)
//                        {
//                              kot_items_bill_nodes.add(new Kot_Items_Bill_Node(localAllKitchenOrderFoodlList.get(px).getMname() , localAllCardOrderFoodList.get(px).getQuantity()));
//                        }
                    selectedHashItemList.clear();
                    cardAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NewActivity.this, "Atleast One item should be selected", Toast.LENGTH_SHORT).show();
                }

                if(kotManager.getKeyBillToken())
                {
                    kot_before = "          TOKEN       \n";
//                    if(localCursor.getCount() !=0)
//                    {
                    int k=0;
                    for(;k<kot_items_bill_nodes.size();k++)
                    {
                        String s = kot_items_bill_nodes.get(k).getItem_node();
                        Log.e("elements are",""+kot_items_bill_nodes.get(k).getItem_node());
                        kot_before = kot_before + kot_items_bill_nodes.get(k).getItem_node()+" :"+kot_items_bill_nodes.get(k).getitem_qtynode()+"\n";

                    }

//                        for(int k=0;k<kot_items_bill_nodes.size();k++)
//                        {
//                        Log.e("element","Name is"+kot_items_bill_nodes.get(k).getItem_node() +":"+kot_items_bill_nodes.get(k).getitem_qtynode());
//                        if(kot_items_bill_nodes.get(k).getItem_node().equals(kot_items_bill_nodes.get(++k).getItem_node()))
//                        {
//
//                        }
//                    kot_items_bill_nodes.remove(k);
//                            try{
////                                if(!kot_before.isEmpty())
////                                {
//                                   String item1 = kot_items_bill_nodes.get(k).getItem_node();
//                                   String item2 = kot_items_bill_nodes.get(++k).getItem_node();
//                                    Toast.makeText(NewActivity.this, ""+kot_items_bill_nodes.get(k).getItem_node(), Toast.LENGTH_SHORT).show();
//                                    if(item1.equals(item2))
//                                    {
//                                        if(onetime ==1)
//                                        {
//                                            kot_before = kot_before+kot_items_bill_nodes.get(k).getItem_node() +" : "+kot_items_bill_nodes.get(k).getitem_qtynode() +"\n";
//                                            bill = kot_before;
//                                        }
//                                        onetime++;
//                                     //   kot_items_bill_nodes.remove(k);
//                                        Toast.makeText(NewActivity.this, "match found", Toast.LENGTH_SHORT).show();
//                                    } else{
//
//                                        prev_item =  kot_items_bill_nodes.get(k).getItem_node();
//                                       onetime =1;
//                                        kot_before = kot_before+kot_items_bill_nodes.get(k).getItem_node() +" : "+kot_items_bill_nodes.get(k).getitem_qtynode() +"\n";
//                                        bill = kot_before;
//                                      //  kot_items_bill_nodes.remove(k);
//                                    }
//
////                                }
//                            }catch (Exception e)
//                            {
//                                Toast.makeText(NewActivity.this, ""+kot_items_bill_nodes.get(k).getItem_node(), Toast.LENGTH_SHORT).show();
//                                kot_before = kot_items_bill_nodes.get(k).getItem_node() +" : "+kot_items_bill_nodes.get(k).getitem_qtynode() +"\n";
//                                bill = kot_before;
//                              //  kot_items_bill_nodes.remove(k);
//                            }
//                        }
                    // kot_before =null;
//                        kot_items_bill_nodes.clear();
                }
                kot_items_bill_nodes.clear();
//                    String Header = kot_before;
                if(localCursor.getCount() !=0)
                {
                    String lang = getString(R.string.bluetooth_strLang);


                    byte[] cmd = new byte[3];
                    cmd[0] = 0x1b;
                    cmd[1] = 0x21;
//                        kot_before = null;
                    if ((lang.compareTo("en")) == 0) {
                        cmd[2] |= 0x10;
                        monceService.write(cmd);           //??????????
                        //monceService.sendMessage("Restaurant!", "GBK");
                        cmd[2] &= 0xEF;
                        monceService.write(cmd);//??????????????

                        // localCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);

                        monceService.sendMessage(kot_before, "GBK");
                    } else if ((lang.compareTo("ch")) == 0) {
                        cmd[2] |= 0x10;
                        monceService.write(cmd);           //??????????
//                    mService.sendMessage("???????\n", "GBK");
                        cmd[2] &= 0xEF;
                        monceService.write(cmd);           //??????????????
                        monceService.sendMessage(kot_before, "GBK");

                    }
                }

                //sfdgfdgdfgdfg++++++++++++++++++++++
            }

        });


        bt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variables.modifyBtn = 0;
                btn_modify.setVisibility(View.GONE);
                orderDatabseHelper.trancateCancelledTableItems(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                bt_card.setTextColor(NewActivity.this.getResources().getColor(R.color.blue));
                bt_kot.setTextColor(Color.BLACK);
                kot_section.setVisibility(View.GONE);
                card_section.setVisibility(View.VISIBLE);

            }
        });

        bt_kot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variables.modifyBtn = 0;
                btn_modify.setVisibility(View.GONE);
                orderDatabseHelper.trancateCancelledTableItems(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                bt_kot.setTextColor(NewActivity.this.getResources().getColor(R.color.blue));
                bt_card.setTextColor(Color.BLACK);
                localAllKitchenOrderFoodlList.clear();
                int i = 0;
                variables.cancelledTotalAmount = 0;
                variables.cancelledTotalTax = 0;
                Cursor locaCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                if (locaCursor.getCount() != 0) {
                    while (locaCursor.moveToNext()) {
                        if (locaCursor.getInt(4) != 0) {
                            localAllKitchenOrderFoodlList.add(new kitchenOrderItem(
                                    locaCursor.getString(1),
                                    locaCursor.getString(2),
                                    ++i,
                                    locaCursor.getInt(3),
                                    locaCursor.getInt(4),
                                    locaCursor.getInt(5),
                                    locaCursor.getInt(6),
                                    locaCursor.getInt(7),
                                    locaCursor.getInt(8),
                                    locaCursor.getInt(9),
                                    locaCursor.getInt(10),
                                    locaCursor.getInt(11),
                                    locaCursor.getInt(13),
                                    locaCursor.getInt(15)
                            ));
                        }
                    }
                }

                kotAdapter = new KotAdapter(NewActivity.this, localAllKitchenOrderFoodlList);
                kot_recyclerview = (RecyclerView) findViewById(R.id.kot_recyclerview);
                kot_recyclerview.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                kot_recyclerview.setLayoutManager(linearLayoutManager);
                kot_recyclerview.setAdapter(kotAdapter);

                bt_kot.setTextColor(Color.parseColor("#FF314A8B"));
                bt_card.setTextColor(Color.parseColor("#000000"));

                card_section.setVisibility(View.GONE);
                kot_section.setVisibility(View.VISIBLE);

            }
        });

        btn_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // custom dialog
                dialog = new Dialog(NewActivity.this);
                dialog.setContentView(R.layout.dialog_shift);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                exist_table = (TextView) dialog.findViewById(R.id.exist_table);
                exist_table.setText(variables.selecetd_table_data.getT_name());
                exist_table.setEnabled(false);
                Log.e("exist_table", exist_table.toString());
                sfloorName = DrawerActivity.sFloorName;
                Cursor localCursor = helpher.getAllDataTable2(variables.selecetd_table_data.getDin_area());


                while (localCursor.moveToNext()) {
                    if (localCursor.getString(6).equals("free")) {
                        available_table_all_data_list.add(new tableItem(
                                localCursor.getString(0),
                                localCursor.getString(1),
                                localCursor.getString(2),
                                localCursor.getString(3),
                                localCursor.getString(4),
                                localCursor.getString(5),
                                localCursor.getString(6),
                                localCursor.getString(7),
                                localCursor.getString(8)));
                        available_table_list.add(localCursor.getString(1));
                    }
                }
                Spin_table = (Spinner) dialog.findViewById(R.id.spin_table);

                shift_cancel = (Button) dialog.findViewById(R.id.shift_cancel);
                shift_update = (Button) dialog.findViewById(R.id.shift_update);


//               new GetTableAsynctask().execute();

                ad_table = new ArrayAdapter(NewActivity.this, R.layout.custom_spinner, available_table_list);
                ad_table.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spin_table.setAdapter(ad_table);
                Spin_table.setOnItemSelectedListener(NewActivity.this);

                dialog.show();
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (variables.modifyBtn == 1) {

                    Cursor cancelOrderInfoCursor = orderDatabseHelper.getCancelledOrderInfo(
                            variables.selecetd_table_data.getT_id(),
                            variables.tableNumber
                    );
                    Cursor localModifiedDatabase = orderDatabseHelper.getCancelledKotModifiedData(
                            variables.selecetd_table_data.getT_id(),
                            variables.tableNumber
                    );
                    if (cancelOrderInfoCursor.getCount() != 0) {
                        if (localModifiedDatabase.getCount() == 0) {
                            while (cancelOrderInfoCursor.moveToNext()) {
                                orderDatabseHelper.insertCancelledKotModifiedData(
                                        cancelOrderInfoCursor.getString(0),
                                        cancelOrderInfoCursor.getString(1),
                                        cancelOrderInfoCursor.getString(2),
                                        cancelOrderInfoCursor.getInt(3),
                                        cancelOrderInfoCursor.getInt(4),
                                        cancelOrderInfoCursor.getInt(5),
                                        cancelOrderInfoCursor.getFloat(6),
                                        cancelOrderInfoCursor.getFloat(7),
                                        cancelOrderInfoCursor.getFloat(8),
                                        cancelOrderInfoCursor.getFloat(9),
                                        cancelOrderInfoCursor.getFloat(10),
                                        cancelOrderInfoCursor.getFloat(11),
                                        cancelOrderInfoCursor.getString(12),
                                        cancelOrderInfoCursor.getFloat(13),
                                        cancelOrderInfoCursor.getFloat(14),
                                        cancelOrderInfoCursor.getString(15),
                                        cancelOrderInfoCursor.getString(16),
                                        cancelOrderInfoCursor.getString(17),
                                        cancelOrderInfoCursor.getString(18),
                                        cancelOrderInfoCursor.getString(19),
                                        cancelOrderInfoCursor.getString(20)
                                );
                                Log.d(TAG, "onClick: inserting cancelled item");
                            }
                        } else {
                            while (cancelOrderInfoCursor.moveToNext()) {
                                insertFlag = 0;
                                localModifiedDatabase = orderDatabseHelper.getCancelledKotModifiedData(
                                        variables.selecetd_table_data.getT_id(),
                                        variables.tableNumber
                                );
                                while (localModifiedDatabase.moveToNext()) {
                                    if (localModifiedDatabase.getString(1).equals(localModifiedDatabase.getString(1))) {
                                        orderDatabseHelper.updateCancelledKotModifiedData(
                                                variables.selecetd_table_data.getT_id(),
                                                localModifiedDatabase.getString(1),
                                                localModifiedDatabase.getString(2),
                                                localModifiedDatabase.getInt(3),
                                                cancelOrderInfoCursor.getInt(4) + localModifiedDatabase.getInt(4),
                                                cancelOrderInfoCursor.getInt(5) + localModifiedDatabase.getInt(5),
                                                cancelOrderInfoCursor.getInt(9) + localModifiedDatabase.getInt(9),
                                                cancelOrderInfoCursor.getInt(10) + localModifiedDatabase.getInt(10),
                                                cancelOrderInfoCursor.getInt(11) + localModifiedDatabase.getInt(11),
                                                cancelOrderInfoCursor.getInt(14) + localModifiedDatabase.getInt(14),
                                                variables.tableNumber
                                        );
                                        Log.d(TAG, "onClick: updating cancelled items " + localModifiedDatabase.getString(2));
                                        insertFlag = 1;
                                        break;
                                    }

                                    if (insertFlag == 0) {
                                        count = "Coming";
                                        orderDatabseHelper.insertCancelledKotModifiedData(
                                                cancelOrderInfoCursor.getString(0),
                                                cancelOrderInfoCursor.getString(1),
                                                cancelOrderInfoCursor.getString(2),
                                                cancelOrderInfoCursor.getInt(3),
                                                cancelOrderInfoCursor.getInt(4),
                                                cancelOrderInfoCursor.getInt(5),
                                                cancelOrderInfoCursor.getFloat(6),
                                                cancelOrderInfoCursor.getFloat(7),
                                                cancelOrderInfoCursor.getFloat(8),
                                                cancelOrderInfoCursor.getFloat(9),
                                                cancelOrderInfoCursor.getFloat(10),
                                                cancelOrderInfoCursor.getFloat(11),
                                                cancelOrderInfoCursor.getString(12),
                                                cancelOrderInfoCursor.getFloat(13),
                                                cancelOrderInfoCursor.getFloat(14),
                                                cancelOrderInfoCursor.getString(15),
                                                cancelOrderInfoCursor.getString(16),
                                                cancelOrderInfoCursor.getString(17),
                                                cancelOrderInfoCursor.getString(18),
                                                cancelOrderInfoCursor.getString(19),
                                                cancelOrderInfoCursor.getString(20)
                                        );
                                        Log.d(TAG, "onClick: calling inserting function, cancelled items");
                                    }
                                }
                            }
                        }

                        cancelOrderInfoCursor = orderDatabseHelper.getCancelledOrderInfo(
                                variables.selecetd_table_data.getT_id(),
                                variables.tableNumber
                        );


                        while (cancelOrderInfoCursor.moveToNext()) {
                            insertFlag = 0;
                            Cursor tableItemOrderInformation = orderDatabseHelper.getTableItemOrderInformation(
                                    variables.selecetd_table_data.getT_id(),
                                    variables.tableNumber
                            );
                            while (tableItemOrderInformation.moveToNext()) {
                                if (cancelOrderInfoCursor.getString(1).equals(tableItemOrderInformation.getString(1))) {
                                    orderDatabseHelper.updatTableItemOrderInformation(
                                            variables.selecetd_table_data.getT_id(),
                                            cancelOrderInfoCursor.getString(1),
                                            cancelOrderInfoCursor.getString(2),
                                            cancelOrderInfoCursor.getInt(3),
                                            tableItemOrderInformation.getInt(4) - cancelOrderInfoCursor.getInt(4),
                                            tableItemOrderInformation.getInt(5) - cancelOrderInfoCursor.getInt(5),
                                            tableItemOrderInformation.getInt(9) - cancelOrderInfoCursor.getInt(9),
                                            tableItemOrderInformation.getInt(10) - cancelOrderInfoCursor.getInt(10),
                                            tableItemOrderInformation.getInt(11) - cancelOrderInfoCursor.getInt(11),
                                            tableItemOrderInformation.getInt(14) - cancelOrderInfoCursor.getInt(14),
                                            variables.tableNumber
                                    );
                                }
                            }
                        }

                        Cursor tableCursor = orderDatabseHelper.getDataOfSelectedTable(
                                variables.selecetd_table_data.getT_id(),
                                variables.tableNumber
                        );
                        if (tableCursor.getCount() != 0) {
                            tableCursor.moveToNext();
                            orderDatabseHelper.updateOrderListItem(
                                    variables.selecetd_table_data.getT_id(),
                                    tableCursor.getInt(6) - variables.cancelledTotalAmount,
                                    tableCursor.getInt(7) - variables.cancelledTotalAmount,
                                    "running",
                                    tableCursor.getFloat(11) - variables.cancelledTotalTax,
                                    variables.tableNumber
                            );
                            variables.total_price = tableCursor.getInt(6) - variables.cancelledTotalAmount;
                            NewActivity.txtTotal.setText(String.valueOf(variables.total_price));
                            variables.cancelledTotalTax = 0;
                            variables.cancelledTotalAmount = 0;
                        }

                        Toast.makeText(NewActivity.this, "Items Modified!!!", Toast.LENGTH_SHORT).show();
                        orderDatabseHelper.trancateCancelledTableItems(
                                variables.selecetd_table_data.getT_id(),
                                variables.tableNumber
                        );
                        btn_modify.setVisibility(View.GONE);
                        variables.modifyBtn = 0;
                    }
                }
            }
        });

        btn_split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor localTableDetailsCurdor = helpher.getTableDetails(variables.selecetd_table_data.getT_id());
                if (localTableDetailsCurdor.getCount() != 0) {
                    localTableDetailsCurdor.moveToNext();
                    if (!localTableDetailsCurdor.getString(8).equals("yes")) {
//                        helpher.updateSplittedStatus(
//                                variables.selecetd_table_data.getT_id(),
//                                "yes",
//                                variables.selected_waiter_data.getWaiter_id(),
//                                variables.selected_waiter_data.getWaiter_id()
//                        );

                        variables.tableNumber = "2";
                        variables.total_price = 0;
                        NewActivity.txtTotal.setText("0");

                        localAllCardOrderFoodList.clear();
                        cardAdapter = new CardAdapter(NewActivity.this, localAllCardOrderFoodList);
                        card_recyclerview = (RecyclerView) findViewById(R.id.card_recyclerview);
                        card_recyclerview.setHasFixedSize(true);
                        LinearLayoutManager cardlinearLayoutManager = new LinearLayoutManager(NewActivity.this);
                        cardlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        card_recyclerview.setLayoutManager(cardlinearLayoutManager);
                        card_recyclerview.setAdapter(cardAdapter);
                        localAllKitchenOrderFoodlListbill = localAllKitchenOrderFoodlList;
                        localAllKitchenOrderFoodlList.clear();
                        kotAdapter = new KotAdapter(NewActivity.this, localAllKitchenOrderFoodlList);
                        kot_recyclerview = (RecyclerView) findViewById(R.id.kot_recyclerview);
                        kot_recyclerview.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        kot_recyclerview.setLayoutManager(linearLayoutManager);
                        kot_recyclerview.setAdapter(kotAdapter);


                    } else {
                        Toast.makeText(NewActivity.this, "Table is already splitted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        menu_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (searchList.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(NewActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Cursor localSearchCursor = helpher.getAllSearchMenuData();
                // Log.d(TAG, "onQueryTextChange: total data count "+localSearchCursor.getCount());
                //Cursor localCursor =helpher.getSearchMenu(newText);
                ArrayList<String> localSubMenuList = new ArrayList<>();
                Cursor localCursor = helpher.getSearchMenuResult(newText);
                Log.d(TAG, "onQueryTextChange: on query result " + localCursor.getCount());
                localSearchedFoodDetails.clear();
                while (localCursor.moveToNext()) {
                    localSubMenuList.add(localCursor.getString(1));
                    localSearchedFoodDetails.add(new foodDetailsItem(localCursor.getString(0),
                            localCursor.getString(1),
                            localCursor.getString(2),
                            localCursor.getString(3),
                            localCursor.getString(4),
                            localCursor.getString(5),
                            localCursor.getString(6),
                            localCursor.getString(7),
                            localCursor.getString(8),
                            localCursor.getString(9),
                            localCursor.getString(10),
                            localCursor.getString(11),
                            localCursor.getString(12),
                            localCursor.getString(13),
                            localCursor.getString(14),
                            localCursor.getString(15),
                            1,
                            Integer.parseInt(localCursor.getString(4)))
                    );
                }
                MenuAdapter menuAdapter = new MenuAdapter(NewActivity.this, localSubMenuList);
                grid_food.setAdapter(menuAdapter);
                gridFoodSetOnClick(localSearchedFoodDetails);
                Log.d(TAG, "onQueryTextChange: " + localCursor.getCount());
                return true;
            }
        });
        strWaiterID = variables.selected_waiter_data.getW_id();
        strWaiterName = variables.selected_waiter_data.getW_name();
        strTableNo = AppPrefences.getTable(NewActivity.this);
        getWaiterDiscountData();

    }

    private void getWaiterDiscountData() {
        discountModals.clear();
        Cursor local = null;
        strNewWaiterId = new SessionManager(NewActivity.this).getWaiterId();
        Log.e("waited id","is"+strNewWaiterId);
        Toast.makeText(this, ""+strNewWaiterId, Toast.LENGTH_SHORT).show();
        strNewWaitName = new SessionManager(NewActivity.this).getWaiterName();
        local = orderDatabseHelper.getWaiterWithDiscount();
        if (local != null && local.getCount() > 0) {
            ForWaiterDetails waiterDetails = orderDatabseHelper.getDiscountByTableF(strNewWaiterId);
            // ForWaiterDetails waiterDetails = orderDatabseHelper.getDiscountByTableF(strTableNo);
            String strWaiterId = waiterDetails.getWaiterId();
            String strWaiterNam = waiterDetails.getWaiterName();
            String strDiscoun = waiterDetails.getDiscountAmo();
            boolean b = orderDatabseHelper.Update_Discount(strWaiterId, strWaiterNam, strTableNo, strDiscoun);
            if (b) {
                Log.e("YES", "YES");
            } else {
                Log.e("No", "No");
            }
        } else {
            Log.e("WaiterData", strNewWaiterId + "\n" + strNewWaitName + "\n" + strTableNo);
            orderDatabseHelper.setWaiterDiscount(strNewWaiterId, strNewWaitName, strTableNo, strDiscount);
        }

        /*if (local != null && local.getCount() > 0) {
            while (local.moveToNext()) {
                String strWaiterId = local.getString(0).toString();
                String strWaiterNam = local.getString(1).toString();
                String strTableN = local.getString(2).toString();
                String strDiscoun = local.getString(3).toString();
                String id = strNewWaiterId;
                String name = strNewWaitName;
                String tableNo = AppPrefences.getTable(NewActivity.this);
                if (!strWaiterId.equals(id) && !strWaiterNam.equals(name) && !strTableN.equals(tableNo)) {
//                    orderDatabseHelper.Update_Discount(strWaiterID, strWaiterName, strTableNo, strDiscoun);
                    orderDatabseHelper.setWaiterDiscount(strWaiterID, strWaiterName, strTableNo, strDiscount);
                    Log.e("DataTable2", id + "\n" + name + "\n" + tableNo);
                } else if (strWaiterId.equals(id) && strWaiterNam.equals(name) && strTableN.equals(tableNo)) {
                    orderDatabseHelper.Update_Discount(strWaiterID, strWaiterName, strTableNo, strDiscoun);
                    Log.e("DataTable1", strWaiterId + "\n" + strWaiterNam + "\n" + strTableN);
                }
//                orderDatabseHelper.Update_Discount(strWaiterID, strWaiterName, strTableNo, strDiscoun);
            }
        } else {
            Log.e("DataTable0", strWaiterId + "\n" + strWaiterName + "\n" + strTableNo);
            orderDatabseHelper.setWaiterDiscount(strNewWaiterId, strNewWaitName, strTableNo, strDiscount);

            Toast.makeText(NewActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuprinter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.usb:
                return true;
            case R.id.wifi:
                Toast.makeText(NewActivity.this, "Save is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.bluetooth:
                Toast.makeText(NewActivity.this, "Search is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void init() {

        linearLayoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        itm = (TextView) findViewById(R.id.itm);
        grid_food = (GridView) findViewById(R.id.grid_food);
        btn_all = (TextView) findViewById(R.id.btn_all);
        w_name1 = (TextView) findViewById(R.id.w_name1);
        guestname = (TextView) findViewById(R.id.guestname);
        bt_card = (TextView) findViewById(R.id.bt_card);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        bt_kot = (TextView) findViewById(R.id.bt_kot);
        list_all = (ListView) findViewById(R.id.list_all);
        all_list = (TextView) findViewById(R.id.all_list);
        btn_split = (Button) findViewById(R.id.btn_split);
        btn_order = (Button) findViewById(R.id.btn_order);
        btn_shift = (Button) findViewById(R.id.btn_shift);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        save_and_send_kitchan = (Button) findViewById(R.id.save_and_send_kitchan);
        card_kot = (LinearLayout) findViewById(R.id.card_kot);
        card_section = (LinearLayout) findViewById(R.id.card_section);
        kot_section = (LinearLayout) findViewById(R.id.kot_section);
        add_ = (ImageView) findViewById(R.id.add_);
        //  totalPrice=(TextView)findViewById(R.id.total);
        menu_search = (SearchView) findViewById(R.id.menu_search);
        listview_search = (ListView) findViewById(R.id.listview_search);
        table_name = (TextView) findViewById(R.id.table_name);
        table_name.setText(AppPrefences.getTable(NewActivity.this));
        btn_modify = (Button) findViewById(R.id.btn_modify);

        //---------------------------------------------
        t_list = new ArrayList<>();
        card_list = new ArrayList<>();
        kot_list = new ArrayList<>();
        menu_list = new ArrayList<>();
        arl = new ArrayList<>();
        searchList = new ArrayList<>();
        available_table_list = new ArrayList<>();
        available_table_all_data_list = new ArrayList<>();

        //-------------------------------------------

        local_menu_list = new ArrayList<>();
        localFoodDetailsList = new ArrayList<>();
        localAllCardOrderFoodList = new ArrayList<>();
        localAllKitchenOrderFoodlList = new ArrayList<>();
        localSearchedFoodDetails = new ArrayList<>();

        //================================================

        btnSendDraw = (Button) findViewById(R.id.btn_test);
        //btnSendDraw.setOnClickListener(new ClickEvent());
        btnSearch = (Button) findViewById(R.id.btnSearch);
        //   btnSearch.setOnClickListener(new ClickEvent());
        btnSend = (Button) findViewById(R.id.btnSend);
        //   btnSend.setOnClickListener(new ClickEvent());
        qrCodeBtnSend = (Button) findViewById(R.id.qr_code_Send);
        //  qrCodeBtnSend.setOnClickListener(new ClickEvent());
        btnClose = (Button) findViewById(R.id.btnClose);
        //     btnClose.setOnClickListener(new ClickEvent());
        edtContext = (EditText) findViewById(R.id.txt_content);
       /* btnClose.setEnabled(false);
        btnSend.setEnabled(false);
        qrCodeBtnSend.setEnabled(false);
        btnSendDraw.setEnabled(false);*/

//        mbService = new BluetoothService(NewActivity.this, mHandler);
//        //?????????????????
//        if (mbService.isAvailable() == false) {
//            Toast.makeText(NewActivity.this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//        }
//        SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
//        String address = sharedPreferences.getString("device", "");
//        Toast.makeText(NewActivity.this,""+address,Toast.LENGTH_SHORT).show();
//        if (address == null || address.equals("")) {
//
//        } else {
//            con_dev = mbService.getDevByMac(address);
//
//            mbService.connect(con_dev);
//
//        }
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDatabseHelper db = new OrderDatabseHelper(NewActivity.this);
                db.getWritableDatabase();
                String Header =
                        //"  Restaurant! \n"
                        "Items                Qty\n";
                Cursor localCursor = db.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);
                bill = Header;
                int i = 0;
                while (localCursor.moveToNext()) {
                    String s = localCursor.getString(2);
                    String s1 = localCursor.getString(4);
                    if (s.length()== 1) {
                        s = s.substring(0, 1)+"             ";
                    } else if(s.length()==2){
                        //   for (int j = s.length() - 12; j <= s.length(); j++)
                        s = s.substring(0, 2) +"            ";
                    }else if(s.length()==3){
                        s = s.substring(0, 3) +"           ";
                    }else if(s.length()==4){
                        s = s.substring(0, 4) +"          ";
                    }else if(s.length()==5){
                        s = s.substring(0, 5) +"         ";
                    }else if(s.length()==6){
                        s = s.substring(0, 6) +"        ";
                    }else if(s.length()==7){
                        s = s.substring(0, 7) +"       ";
                    }else if(s.length()==8){
                        s = s.substring(0, 8) +"      ";
                    }else if(s.length()==9){
                        s = s.substring(0, 9) +"     ";
                    }else if(s.length()==10){
                        s = s.substring(0, 10) +"    ";
                    }else if(s.length()==11){
                        s = s.substring(0, 11) +"   ";
                    }else if(s.length()==12){
                        s = s.substring(0, 12) +"  ";
                    }else if(s.length()==13){
                        s = s.substring(0, 13) +" ";
                    }else if(s.length()==14){
                        s = s.substring(0, 14) +"";
                    }
                    else if(s.length()>=15){
                        s = s.substring(0, 14) +"";
                    }
//                    else if(s.length()>=16){
//                        s = s.substring(0, 16) +"";
//                    }
                    if (s1.length() <= 5) {
                        for (int j = 0; j <= s1.length() - 5; j++) {
                            s1 = s1+ "";
                        }
                    }
                    String items = s + "        " + s1 + "\n";
                    bill = bill + items;
                    i++;
                    System.out.println(bill);
                    Toast.makeText(NewActivity.this, bill, Toast.LENGTH_SHORT).show();
//                    printBill(bill);
                }


                String lang = getString(R.string.bluetooth_strLang);


                byte[] cmd = new byte[3];
                cmd[0] = 0x1b;
                cmd[1] = 0x21;
                if ((lang.compareTo("en")) == 0) {
                    cmd[2] |= 0x10;
                    monceService.write(cmd);           //??????????
                    monceService.sendMessage("Restaurant!", "GBK");
                    cmd[2] &= 0xEF;
                    monceService.write(cmd);//??????????????

                    // localCursor = orderDatabseHelper.getTableItemOrderInformation(variables.selecetd_table_data.getT_id(), variables.tableNumber);

                    monceService.sendMessage(bill, "GBK");
                } else if ((lang.compareTo("ch")) == 0) {
                    cmd[2] |= 0x10;
                    monceService.write(cmd);           //??????????
//                    mService.sendMessage("???????\n", "GBK");
                    cmd[2] &= 0xEF;
                    monceService.write(cmd);           //??????????????
                    monceService.sendMessage(bill, "GBK");
                }
            }
        });

    }


    //==================================================================================

    private void gridFoodSetOnClick(final List<foodDetailsItem> localFoodList) {

        grid_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bt_card.setTextColor(Color.parseColor("#FF314A8B"));
                bt_kot.setTextColor(Color.parseColor("#000000"));
                kot_section.setVisibility(View.GONE);
                card_section.setVisibility(View.VISIBLE);
                localCardOrderFlag = 0;
//                kot_before = null;
                foodDetailsItem localSelectedFood = localFoodList.get(position);
                Log.d(TAG, "onItemClick: inside grid : selected food item " + localSelectedFood.getM_name());
                variables.total_price = variables.total_price + Integer.parseInt(localSelectedFood.getItem_rate1());
                NewActivity.txtTotal.setText(String.valueOf(variables.total_price));

                Log.d(TAG, "onItemClick: selected food details");

                Cursor localCursor = orderDatabseHelper.getAllCardOrderData();
                if (localCursor.getCount() == 0) {
                    float sgst;
                    float cgst;
                    float igst, vat;
                    if (!localSelectedFood.getRate_Taxsgst().equals("")) {
                        sgst = Float.parseFloat(localSelectedFood.getRate_Taxsgst());
                    } else {
                        sgst = 0;
                    }
                    if (!localSelectedFood.getRate_Taxcgst().equals("")) {
                        cgst = Float.parseFloat(localSelectedFood.getRate_Taxcgst());
                    } else {
                        cgst = 0;
                    }
                    if (!localSelectedFood.getRate_Taxigst().equals("")) {
                        igst = Float.parseFloat(localSelectedFood.getRate_Taxigst());
                    } else {
                        igst = 0;
                    }
                    if (!localSelectedFood.getVat().equals("")) {
                        vat = Float.parseFloat(localSelectedFood.getVat());
                    } else {
                        vat = 0;
                    }
                    Log.d(TAG, "onItemClick: Cursor count is zero adding new item");
                    if (localSelectedFood.getVat().equals("") || localSelectedFood.getVat().equals("0")) {
                        orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                localSelectedFood.getM_name(),
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                1,
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                variables.selected_floor,
                                variables.selecetd_table_data.getT_id(),
                                variables.selecetd_table_data.getT_name(),
                                sgst,
                                cgst,
                                igst,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * igst) / 100,
                                vat,
                                0,
                                localSelectedFood.getBarcode(),
                                localSelectedFood.getAlias(),
                                localSelectedFood.getGroup(),
                                localSelectedFood.getCategory(),
                                localSelectedFood.getServe_unit(),
                                localSelectedFood.getDepartment()

                        );
                        Toast.makeText(NewActivity.this, "if part of first", Toast.LENGTH_SHORT).show();
//                        kot_items_bill_nodes.add(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                        if(kot_items_bill_nodes.size() !=0)
//                        {
//                            for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//                                    //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                    kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                }
//                            }
//                        }else {
//                            kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                        }


                        Log.d(TAG, "onItemClick: card is empty and vat is zero " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100 + " " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100 + " vat" + vat);
                    } else
                    {
                        orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                localSelectedFood.getM_name(),
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                1,
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                variables.selected_floor,
                                variables.selecetd_table_data.getT_id(),
                                variables.selecetd_table_data.getT_name(),
                                Float.parseFloat(localSelectedFood.getRate_Taxsgst()),
                                Float.parseFloat(localSelectedFood.getRate_Taxcgst()),
                                Float.parseFloat(localSelectedFood.getRate_Taxigst()),
                                0,
                                0,
                                0,
                                Float.parseFloat(localSelectedFood.getVat()),
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * Float.parseFloat(localSelectedFood.getVat())) / 100,
                                localSelectedFood.getBarcode(),
                                localSelectedFood.getAlias(),
                                localSelectedFood.getGroup(),
                                localSelectedFood.getCategory(),
                                localSelectedFood.getServe_unit(),
                                localSelectedFood.getDepartment()
                        );

                        Log.d(TAG, "onItemClick: card is empty but vat!=0 " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * Float.parseFloat(localSelectedFood.getVat())) / 100 + " " + sgst + " " + cgst);
                    }
                } else {
                    while (localCursor.moveToNext()) {
                        Log.d(TAG, "onItemClick: existing item in table " + localCursor.getInt(0) + " " + localCursor.getString(1) + " " + localCursor.getString(2) + " " + localCursor.getString(3));
                        if (localCursor.getInt(0) == Integer.parseInt(localSelectedFood.getM_id().trim())) {
                            Log.d(TAG, "onItemClick: Inside while loop " + localCursor.getInt(0) + " " + Integer.parseInt(localSelectedFood.getM_id()));
                            String localId = localCursor.getString(0);
                            String localItemName = localCursor.getString(1);
                            int localRate = localCursor.getInt(2);
                            int localQuantity = localCursor.getInt(3);
                            int localTotalPrice = localCursor.getInt(4);
                            float localSgstTax = localCursor.getFloat(8);
                            float localCgstTax = localCursor.getFloat(9);
                            float localIgstTax = localCursor.getFloat(10);
                            float localTotalSgstTax = localCursor.getFloat(11);
                            float localTotalCgstTax = localCursor.getFloat(12);
                            float localTotalIgstTax = localCursor.getFloat(13);
                            float localVatTax = localCursor.getFloat(14);
                            float localTotalVatTax = localCursor.getFloat(15);


                            int localNewTotalPrice = localTotalPrice + localRate;
                            //Log.d(TAG, "onItemClick: updating existing item "+localItemName+" "+localSelectedFood.getM_name());
                            if (localVatTax == 0) {
                                Log.d(TAG, "onItemClick: update & vat is zero " + localVatTax);
                                orderDatabseHelper.updateQuantityAndTotalPrice(
                                        localId,
                                        localItemName,
                                        localRate,
                                        localQuantity + 1,
                                        localNewTotalPrice,
                                        variables.selected_floor,
                                        variables.table_no,
                                        variables.table_name,
                                        localTotalSgstTax + (localRate * localSgstTax / 100),
                                        localTotalCgstTax + (localRate * localCgstTax / 100),
                                        localTotalIgstTax + (localRate * localIgstTax / 100),
                                        0
                                );
                                Log.d(TAG, "onItemClick: updating item and vat =0 " + localTotalSgstTax + (localRate * localSgstTax / 100) + " " + localTotalCgstTax + (localRate * localCgstTax / 100) + " vat" + localVatTax);
                            } else {
                                Log.d(TAG, "onItemClick: update & vat is not zero " + localVatTax);
                                orderDatabseHelper.updateQuantityAndTotalPrice(
                                        localId,
                                        localItemName,
                                        localRate,
                                        localQuantity + 1,
                                        localNewTotalPrice,
                                        variables.selected_floor,
                                        variables.table_no,
                                        variables.table_name,
                                        0,
                                        0,
                                        0,
                                        localTotalVatTax + (localRate * localVatTax / 100)
                                );
                                Log.d(TAG, "onItemClick: updating item and vat !=0 " + localTotalVatTax + (localRate * localVatTax / 100) + " " + localSgstTax + " " + localCgstTax);
                            }
                            localCardOrderFlag = 1;
                            break;
                        }


                    }
                    if (localCardOrderFlag == 0) {
                        float sgst;
                        float cgst;
                        float igst, vat;
                        if (!localSelectedFood.getRate_Taxsgst().equals("")) {
                            sgst = Float.parseFloat(localSelectedFood.getRate_Taxsgst());
                        } else {
                            sgst = 0;
                        }
                        if (!localSelectedFood.getRate_Taxcgst().equals("")) {
                            cgst = Float.parseFloat(localSelectedFood.getRate_Taxcgst());
                        } else {
                            cgst = 0;
                        }
                        if (!localSelectedFood.getRate_Taxigst().equals("")) {
                            igst = Float.parseFloat(localSelectedFood.getRate_Taxigst());
                        } else {
                            igst = 0;
                        }
                        if (!localSelectedFood.getVat().equals("")) {
                            vat = Float.parseFloat(localSelectedFood.getVat());
                        } else {
                            vat = 0;
                        }
                        Log.d(TAG, "onItemClick: if flag is equal to zero");
                        if (localSelectedFood.getVat().equals("") || localSelectedFood.getVat().equals("0")) {
                            Log.d(TAG, "onItemClick: vat is zero " + localSelectedFood.getVat());
                            orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                    localSelectedFood.getM_name(),
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    1,
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    variables.selected_floor,
                                    variables.selecetd_table_data.getT_id(),
                                    variables.selecetd_table_data.getT_name(),
                                    sgst,
                                    cgst,
                                    igst,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * igst) / 100,
                                    Float.parseFloat(localSelectedFood.getVat()),
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100,
                                    localSelectedFood.getBarcode(),
                                    localSelectedFood.getAlias(),
                                    localSelectedFood.getGroup(),
                                    localSelectedFood.getCategory(),
                                    localSelectedFood.getServe_unit(),
                                    localSelectedFood.getDepartment()

                            );
                            Log.d(TAG, "onItemClick: inserting and vat is zero not in while loop  " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100 + " " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100 + " vat " + vat);

                        } else {
                            Log.d(TAG, "onItemClick: vat is non zero" + localSelectedFood.getVat());
                            orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                    localSelectedFood.getM_name(),
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    1,
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    variables.selected_floor,
                                    variables.selecetd_table_data.getT_id(),
                                    variables.selecetd_table_data.getT_name(),
                                    sgst,
                                    cgst,
                                    igst,
                                    0,
                                    0,
                                    0,
                                    vat,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100,
                                    localSelectedFood.getBarcode(),
                                    localSelectedFood.getAlias(),
                                    localSelectedFood.getGroup(),
                                    localSelectedFood.getCategory(),
                                    localSelectedFood.getServe_unit(),
                                    localSelectedFood.getDepartment()

                            );
                            Log.d(TAG, "onItemClick: inserting and vat is not zero not in while loop  " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100 + " " + sgst + " " + cgst);

                        }

                    }
                }
                localAllCardOrderFoodList.clear();
                localCursor = orderDatabseHelper.getAllCardOrderData();
                while (localCursor.moveToNext()) {
                    localAllCardOrderFoodList.add(new cardOrderItem(localCursor.getString(0),
                            localCursor.getString(1),
                            localCursor.getInt(2),
                            localCursor.getInt(3),
                            localCursor.getInt(4),
                            localCursor.getInt(8),
                            localCursor.getInt(9),
                            localCursor.getInt(10),
                            localCursor.getInt(11),
                            localCursor.getInt(12),
                            localCursor.getInt(13),
                            localCursor.getInt(14),
                            localCursor.getInt(15)
                    ));

                }

                Log.d(TAG, "onItemClick: listCount" + localAllCardOrderFoodList.size());

                cardAdapter = new CardAdapter(NewActivity.this, localAllCardOrderFoodList);
                card_recyclerview = (RecyclerView) findViewById(R.id.card_recyclerview);
                card_recyclerview.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                card_recyclerview.setLayoutManager(linearLayoutManager);
                card_recyclerview.setAdapter(cardAdapter);


            }
        });
    }

    private void getAllmenuData() {
        ArrayList<String> localAllMenuList = new ArrayList<>();

        Cursor localCursor = helpher.getAllMenuDataTable();
        localAllMenuList.clear();
        while (localCursor.moveToNext()) {
            localAllMenuList.add(localCursor.getString(1));
        }
        getValues(localAllMenuList);
    }

    private void setCategoryList(ArrayList list) {
        re_horizontal = findViewById(R.id.re_horizontal);
        re_horizontal.addItemDecoration(new DividerItemDecoration(NewActivity.this, LinearLayoutManager.HORIZONTAL));
        recyclerAdapter = new RecyclerAdapter(NewActivity.this, list);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(NewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        re_horizontal.setLayoutManager(horizontalLayoutManager);
        re_horizontal.setAdapter(recyclerAdapter);
    }

    //----------------------------------------------------------------

    public void getValues(ArrayList<String> category_list) {
        arrayList = category_list;
        //  arrayList = AllMenuList;
        Log.e("adaptList?", category_list.toString());


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_all_item, R.id.all_list, arrayList);
        list_all.setAdapter(adapter);

        String initial_item = arrayList.get(0);

        setUpItemListOfGroup(initial_item);

        list_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                //  String value = adapter.getItem(position);
                //  Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                Group_item = arrayList.get(position);
                Log.e("Group_item", Group_item);

                variables.group_name = Group_item;

                setUpItemListOfGroup(Group_item);

            }
        });

        //---------------------------------------------------------------------------

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewActivity.this, Mid_Delay_Activity.class);
                intent.putExtra("Check", count);
                startActivity(intent);
                finish();

            }
        });

    }


    /**
     * set up this list of item in grid view on onClick on any item eg. MOCKTAILS,SOUTH INDIAN.
     *
     * @param group_item
     */
    private void setUpItemListOfGroup(String group_item) {
        final Cursor localCursor = helpher.getAllGridDataTable(group_item);
        //helpher.trancateSearchMenuTable();
        localFoodDetailsList.clear();
        ArrayList<String> localSubMenuList = new ArrayList<>();
        localSubMenuList.clear();
        while (localCursor.moveToNext()) {
            localSubMenuList.add(localCursor.getString(1));
            localFoodDetailsList.add(new foodDetailsItem(
                    localCursor.getString(0),
                    localCursor.getString(1),
                    localCursor.getString(2),
                    localCursor.getString(3),
                    localCursor.getString(4),
                    localCursor.getString(5),
                    localCursor.getString(6),
                    localCursor.getString(7),
                    localCursor.getString(8),
                    localCursor.getString(9),
                    localCursor.getString(10),
                    localCursor.getString(11),
                    localCursor.getString(12),
                    localCursor.getString(13),
                    localCursor.getString(14),
                    localCursor.getString(15),
                    1,
                    Integer.parseInt(localCursor.getString(4)))
            );

        }

        MenuAdapter menuAdapter = new MenuAdapter(NewActivity.this, localSubMenuList);
        grid_food.setAdapter(menuAdapter);


        grid_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            card_kot.setVisibility(View.GONE);
//                            card1.setVisibility(View.VISIBLE);

                bt_card.setTextColor(Color.parseColor("#FF314A8B"));
                bt_kot.setTextColor(Color.parseColor("#000000"));
                kot_section.setVisibility(View.GONE);
                card_section.setVisibility(View.VISIBLE);
                localCardOrderFlag = 0;
                foodDetailsItem localSelectedFood = localFoodDetailsList.get(position);
                Log.d(TAG, "onItemClick: inside grid : selected food item " + localSelectedFood.getM_name());
                variables.total_price = variables.total_price + Integer.parseInt(localSelectedFood.getItem_rate1());
                NewActivity.txtTotal.setText(String.valueOf(variables.total_price));

                Log.d(TAG, "onItemClick: selected food details");

                Cursor localCursor = orderDatabseHelper.getAllCardOrderData();
                //first step o , and whebn no items
                if (localCursor.getCount() == 0) {
                    float sgst;
                    float cgst;
                    float igst, vat;
                    if (!localSelectedFood.getRate_Taxsgst().equals("")) {
                        sgst = Float.parseFloat(localSelectedFood.getRate_Taxsgst());
                    } else {
                        sgst = 0;
                    }
                    if (!localSelectedFood.getRate_Taxcgst().equals("")) {
                        cgst = Float.parseFloat(localSelectedFood.getRate_Taxcgst());
                    } else {
                        cgst = 0;
                    }
                    if (!localSelectedFood.getRate_Taxigst().equals("")) {
                        igst = Float.parseFloat(localSelectedFood.getRate_Taxigst());
                    } else {
                        igst = 0;
                    }
                    if (!localSelectedFood.getVat().equals("")) {
                        vat = Float.parseFloat(localSelectedFood.getVat());
                    } else {
                        vat = 0;
                    }
                    Log.d(TAG, "onItemClick: Cursor count is zero adding new item");
                    //when no elemts
                    if (localSelectedFood.getVat().equals("") || localSelectedFood.getVat().equals("0")) {

                        orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                localSelectedFood.getM_name(),
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                1,
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                variables.selected_floor,
                                variables.selecetd_table_data.getT_id(),
                                variables.selecetd_table_data.getT_name(),
                                sgst,
                                cgst,
                                igst,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100,
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * igst) / 100,
                                vat,
                                0,
                                localSelectedFood.getBarcode(),
                                localSelectedFood.getAlias(),
                                localSelectedFood.getGroup(),
                                localSelectedFood.getCategory(),
                                localSelectedFood.getServe_unit(),
                                localSelectedFood.getDepartment()

                        );
                        //kot_before = localSelectedFood.getM_name() + ""+ Integer.parseInt(localSelectedFood.getItem_rate1());
                        Toast.makeText(NewActivity.this, "at eslse part of firsy", Toast.LENGTH_SHORT).show();
//                        if(kot_items_bill_nodes.size() !=0)
//                        {
//                            for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//                                    //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                    kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                }
//                            }
//                        }else {
//                            kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                        }




                        Log.d(TAG, "onItemClick: card is empty and vat is zero " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100 + " " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100 + " vat" + vat);
                    } else
                    {
                        orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                localSelectedFood.getM_name(),
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                1,
                                Integer.parseInt(localSelectedFood.getItem_rate1()),
                                variables.selected_floor,
                                variables.selecetd_table_data.getT_id(),
                                variables.selecetd_table_data.getT_name(),
                                Float.parseFloat(localSelectedFood.getRate_Taxsgst()),
                                Float.parseFloat(localSelectedFood.getRate_Taxcgst()),
                                Float.parseFloat(localSelectedFood.getRate_Taxigst()),
                                0,
                                0,
                                0,
                                Float.parseFloat(localSelectedFood.getVat()),
                                (Float.parseFloat(localSelectedFood.getItem_rate1()) * Float.parseFloat(localSelectedFood.getVat())) / 100,
                                localSelectedFood.getBarcode(),
                                localSelectedFood.getAlias(),
                                localSelectedFood.getGroup(),
                                localSelectedFood.getCategory(),
                                localSelectedFood.getServe_unit(),
                                localSelectedFood.getDepartment()

                        );
                        //     kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                            if(kot_items_bill_nodes.size() !=0)
//                            {
//                                for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                    if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//                                        //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                        kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                    }
//                                }
//                            }else {
//                                kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                            }



                        Toast.makeText(NewActivity.this, "we aree vvat localCursor.moveToNext()", Toast.LENGTH_SHORT).show();
                        //   kot_before = localSelectedFood.getM_name() + ""+ localSelectedFood.getItem_rate1();
//                        kot_before = localSelectedFood.getM_name() +":"+localSelectedFood.getQuantity();
//                        bill = kot_before;
                        Log.d(TAG, "onItemClick: card is empty but vat!=0 " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * Float.parseFloat(localSelectedFood.getVat())) / 100 + " " + sgst + " " + cgst);
                    }
                } else {
                    //existing here /second time also
                    while (localCursor.moveToNext()) {
                        Log.d(TAG, "onItemClick: existing item in table " + localCursor.getInt(0) + " " + localCursor.getString(1) + " " + localCursor.getString(2) + " " + localCursor.getString(3));
                        /*not for 2itms*/         if (localCursor.getInt(0) == Integer.parseInt(localSelectedFood.getM_id().trim())) {
                            Log.d(TAG, "onItemClick: Inside while loop " + localCursor.getInt(0) + " " + Integer.parseInt(localSelectedFood.getM_id()));
                            String localId = localCursor.getString(0);
                            String localItemName = localCursor.getString(1);
                            //     kot_before = localSelectedFood.getM_name() + ""+ localSelectedFood.getQuantity();
                            int localRate = localCursor.getInt(2);
//                            kot_before = localItemName +":"+localRate;
//                            bill = kot_before;
                            int localQuantity = localCursor.getInt(3);
                            int localTotalPrice = localCursor.getInt(4);
                            float localSgstTax = localCursor.getFloat(8);
                            float localCgstTax = localCursor.getFloat(9);
                            float localIgstTax = localCursor.getFloat(10);
                            float localTotalSgstTax = localCursor.getFloat(11);
                            float localTotalCgstTax = localCursor.getFloat(12);
                            float localTotalIgstTax = localCursor.getFloat(13);
                            float localVatTax = localCursor.getFloat(14);
                            float localTotalVatTax = localCursor.getFloat(15);

                            int localNewTotalPrice = localTotalPrice + localRate;

//                            if(kot_items_bill_nodes.size() !=0)
//                            {
//                                for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                    if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//                                        //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                        kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                    }
//                                }
//                            }else {
//                                kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                            }


//                            for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//
//                                    //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                    kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localItemName ,localQuantity));
//                                }
//                            }

                            //Log.d(TAG, "onItemClick: updating existing item "+localItemName+" "+localSelectedFood.getM_name());
                            if (localVatTax == 0) {
                                Log.d(TAG, "onItemClick: update & vat is zero " + localVatTax);
                                orderDatabseHelper.updateQuantityAndTotalPrice(
                                        localId,
                                        localItemName,
                                        localRate,
                                        localQuantity + 1,
                                        localNewTotalPrice,
                                        variables.selected_floor,
                                        variables.table_no,
                                        variables.table_name,
                                        localTotalSgstTax + (localRate * localSgstTax / 100),
                                        localTotalCgstTax + (localRate * localCgstTax / 100),
                                        localTotalIgstTax + (localRate * localIgstTax / 100),
                                        0
                                );
                                Log.d(TAG, "onItemClick: updating item and vat =0 " + localTotalSgstTax + (localRate * localSgstTax / 100) + " " + localTotalCgstTax + (localRate * localCgstTax / 100) + " vat" + localVatTax);
                            } else {
                                Log.d(TAG, "onItemClick: update & vat is not zero " + localVatTax);
                                orderDatabseHelper.updateQuantityAndTotalPrice(
                                        localId,
                                        localItemName,
                                        localRate,
                                        localQuantity + 1,
                                        localNewTotalPrice,
                                        variables.selected_floor,
                                        variables.table_no,
                                        variables.table_name,
                                        0,
                                        0,
                                        0,
                                        localTotalVatTax + (localRate * localVatTax / 100)
                                );
                                Log.d(TAG, "onItemClick: updating item and vat !=0 " + localTotalVatTax + (localRate * localVatTax / 100) + " " + localSgstTax + " " + localCgstTax);
                            }
                            localCardOrderFlag = 1;
                            break;
                        }


                    }
                    //runs when 2 itms
                    if (localCardOrderFlag == 0) {
                        float sgst;
                        float cgst;
                        float igst, vat;
                        if (!localSelectedFood.getRate_Taxsgst().equals("")) {
                            sgst = Float.parseFloat(localSelectedFood.getRate_Taxsgst());
                        } else {
                            sgst = 0;
                        }
                        if (!localSelectedFood.getRate_Taxcgst().equals("")) {
                            cgst = Float.parseFloat(localSelectedFood.getRate_Taxcgst());
                        } else {
                            cgst = 0;
                        }
                        if (!localSelectedFood.getRate_Taxigst().equals("")) {
                            igst = Float.parseFloat(localSelectedFood.getRate_Taxigst());
                        } else {
                            igst = 0;
                        }
                        if (!localSelectedFood.getVat().equals("")) {
                            vat = Float.parseFloat(localSelectedFood.getVat());
                        } else {
                            vat = 0;
                        }
                        Log.d(TAG, "onItemClick: if flag is equal to zero");
                        //runs even when have 2 items /one time for 2
                        if (localSelectedFood.getVat().equals("") || localSelectedFood.getVat().equals("0")) {
                            Log.d(TAG, "onItemClick: vat is zero " + localSelectedFood.getVat());
                            //   kot_before = localSelectedFood.getM_name() + ""+ localSelectedFood.getQuantity();
                            orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                    localSelectedFood.getM_name(),
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    1,
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    variables.selected_floor,
                                    variables.selecetd_table_data.getT_id(),
                                    variables.selecetd_table_data.getT_name(),
                                    sgst,
                                    cgst,
                                    igst,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * igst) / 100,
                                    Float.parseFloat(localSelectedFood.getVat()),
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100,
                                    localSelectedFood.getBarcode(),
                                    localSelectedFood.getAlias(),
                                    localSelectedFood.getGroup(),
                                    localSelectedFood.getCategory(),
                                    localSelectedFood.getServe_unit(),
                                    localSelectedFood.getDepartment()

                            );

//                            try{
//
//                                if(!kot_before.isEmpty())
//                                {
//                                    kot_before =kot_before +localSelectedFood.getM_id() +" : "+ localSelectedFood.getQuantity() +"\n";
//                                    bill = kot_before;
//                                }
//                            }catch (Exception e)
//                            {
//                                kot_before = localSelectedFood.getM_id() +" : "+ localSelectedFood.getQuantity() +"\n";
//                                bill = kot_before;
//                            }
                            //       kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                            if(kot_items_bill_nodes.size() !=0)
//                            {
//                                for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                    if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//                                        //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                        kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                    }
//                                }
//                            }else {
//                                kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                            }


//                            for(int p1=0;p1<kot_items_bill_nodes.size();p1++) {
//                                if (!kot_items_bill_nodes.get(p1).getItem_node().equals(localSelectedFood.getM_name())) {
//
//                                    //  kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name(), localSelectedFood.getQuantity()));
//                                    kot_items_bill_nodes.addLast(new Kot_Items_Bill_Node(localSelectedFood.getM_name() , localSelectedFood.getQuantity()));
//                                }
//                            }

                            Log.d(TAG, "onItemClick: inserting and vat is zero not in while loop  " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * sgst) / 100 + " " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * cgst) / 100 + " vat " + vat);

                        } else
                        {
                            Log.d(TAG, "onItemClick: vat is non zero" + localSelectedFood.getVat());
                            orderDatabseHelper.insertCardOrder(localSelectedFood.getM_id(),
                                    localSelectedFood.getM_name(),
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    1,
                                    Integer.parseInt(localSelectedFood.getItem_rate1()),
                                    variables.selected_floor,
                                    variables.selecetd_table_data.getT_id(),
                                    variables.selecetd_table_data.getT_name(),
                                    sgst,
                                    cgst,
                                    igst,
                                    0,
                                    0,
                                    0,
                                    vat,
                                    (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100,
                                    localSelectedFood.getBarcode(),
                                    localSelectedFood.getAlias(),
                                    localSelectedFood.getGroup(),
                                    localSelectedFood.getCategory(),
                                    localSelectedFood.getServe_unit(),
                                    localSelectedFood.getDepartment()

                            );
                            Log.d(TAG, "onItemClick: inserting and vat is not zero not in while loop  " + (Float.parseFloat(localSelectedFood.getItem_rate1()) * vat) / 100 + " " + sgst + " " + cgst);

                        }

                    }
                }
                localAllCardOrderFoodList.clear();
                localCursor = orderDatabseHelper.getAllCardOrderData();
                // runs for all
                while (localCursor.moveToNext() && localCursor.getCount()>0) {
                    localAllCardOrderFoodList.add(new cardOrderItem(localCursor.getString(0),
                            localCursor.getString(1),
                            localCursor.getInt(2),
                            localCursor.getInt(3),
                            localCursor.getInt(4),
                            localCursor.getInt(8),
                            localCursor.getInt(9),
                            localCursor.getInt(10),
                            localCursor.getInt(11),
                            localCursor.getInt(12),
                            localCursor.getInt(13),
                            localCursor.getInt(14),
                            localCursor.getInt(15)

                    ));

//                    try{
//
//                        if(!kot_before.isEmpty())
//                        {
//                            kot_before =kot_before + localCursor.getString(1) +" : "+localCursor.getInt(3) +"\n";
//                            bill = kot_before;
//                        }
//                    }catch (Exception e)
//                    {
//                        kot_before =localCursor.getString(1) +" : "+localCursor.getInt(3) +"\n";
//                        bill = kot_before;
//                    }


//                    kot_before = localCursor.getString(0) +":"+ localCursor.getString(1);
//                    bill = kot_before;


                }

                Log.d(TAG, "onItemClick: listCount" + localAllCardOrderFoodList.size());

                cardAdapter = new CardAdapter(NewActivity.this, localAllCardOrderFoodList);
                card_recyclerview = (RecyclerView) findViewById(R.id.card_recyclerview);
                card_recyclerview.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                card_recyclerview.setLayoutManager(linearLayoutManager);
                card_recyclerview.setAdapter(cardAdapter);


            }
        });
    }

    //------------------------------------------------


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {


        shift_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selected_table = Spin_table.getSelectedItem().toString();
                helpher.updateTableStatus(
                        available_table_all_data_list.get(i).getT_id(),
                        available_table_all_data_list.get(i).getT_name(),
                        available_table_all_data_list.get(i).getDin_area(),
                        available_table_all_data_list.get(i).getAlias(),
                        available_table_all_data_list.get(i).getOutlet_id(),
                        available_table_all_data_list.get(i).getD_id(),
                        "allocated"
                );

                helpher.updateTableStatus(
                        variables.selecetd_table_data.getT_id(),
                        "free"
                );

                orderDatabseHelper.updateOrderListItemTableStatus(variables.selecetd_table_data.getT_id(), available_table_all_data_list.get(i).getT_id());
                orderDatabseHelper.updateTableItemOrderInformationTableStatus(variables.selecetd_table_data.getT_id(), available_table_all_data_list.get(i).getT_id());

                variables.selecetd_table_data = available_table_all_data_list.get(i);

                helpher.updateTableStatus(variables.table_no, "free");
                Log.d(TAG, "onClick: spinner selected item :" + Selected_table + " " + available_table_all_data_list.get(i).getT_name());

                variables.table_name = Selected_table;
                variables.table_no = available_table_all_data_list.get(i).getD_id();
//                NewActivity.this.notifyAll();
                Toast.makeText(NewActivity.this, "Your Table Updated!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        shift_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish();
    }

    //============================================================================

    @Override
    public void onResume() {
        super.onResume();
//       if( mbService.isBTopen() == false){
//         Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//          startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mbService != null)
//            mbService.stop();
//       mbService = null;
    }

    //--------------------------------
   /* private void jj() {
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
                    if ((lang.compareTo("en")) == 0) {
                        cmd[2] |= 0x10;
                        mService.write(cmd);           //??????????
                        mService.sendMessage("Congratulations!\n", "GBK");
                        cmd[2] &= 0xEF;
                        mService.write(cmd);           //??????????????
                        msg = "  You have sucessfully created communications between your device and our bluetooth printer.\n\n"
                                + "  Our company is a high-tech enterprise which specializes" +
                                " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";
                        mService.sendMessage(msg, "GBK");
                    } else if ((lang.compareTo("ch")) == 0) {
                        cmd[2] |= 0x10;
                        mService.write(cmd);           //??????????
                        mService.sendMessage("???????\n", "GBK");
                        cmd[2] &= 0xEF;
                        mService.write(cmd);           //??????????????
                        msg = "  ??????????????????????????????????\n\n"
                                + "  ??????????????????????????????????????????????????????????????????.\n\n";
                        mService.sendMessage(msg, "GBK");
                    }
                    break;
                case R.id.btnSearch:
                    Intent serverIntent = new Intent(NewActivity.this, DeviceListActivity.class);      //???????????????
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                    break;
                case R.id.btnSend:
                    msg = edtContext.getText().toString();
                    if (msg.length() > 0) {
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
                    if (msg.length() > 0) {
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
}*/
    //----------------------------------

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //??????
                            Toast.makeText(NewActivity.this, "Connect successful", Toast.LENGTH_SHORT).show();

                            break;
                        case BluetoothService.STATE_CONNECTING:  //????????
                            Log.d("????????", "????????.....");
                            break;
                        case BluetoothService.STATE_LISTEN:     //????????????
                        case BluetoothService.STATE_NONE:
                            Log.d("????????", "???????.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //????????????
                    Toast.makeText(NewActivity.this, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();



                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //?????????
                    //   Toast.makeText(NewActivity.this, "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    //---------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:      //?????????
                if (resultCode == Activity.RESULT_OK) {   //?????????
                    Toast.makeText(NewActivity.this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CONNECT_DEVICE:     //????????????????
                if (resultCode == Activity.RESULT_OK) {   //?????????????????????
//                    String address = data.getExtras()
//                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //??????????????mac???
//                    con_dev = mbService.getDevByMac(address);
//
//                    mbService.connect(con_dev);
                }
                break;
        }
    }

    //------------------------------

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
        monceService.write(sendData);   //???byte??????
        Log.d("????????",""+sendData.length);
    }
    //============================================================================



}
