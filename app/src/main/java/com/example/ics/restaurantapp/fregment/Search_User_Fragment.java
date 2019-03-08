package com.example.ics.restaurantapp.fregment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.CustomerDatabaseHelper;
import com.example.ics.restaurantapp.ModelDB.CustomerItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.adapter.CustomerAdapter;

import java.util.ArrayList;

public class Search_User_Fragment extends DialogFragment {

    private RecyclerView customerRecycler;
    private LinearLayoutManager layoutManager;
    private String wname, AddMob, AddName, AddAdress;
    private EditText m_number, address, shift_table, takename;
    private CustomerDatabaseHelper customerHelper;
    private ArrayList<CustomerItem> list = new ArrayList<>();
    private ImageView icon_add;
    private Button t_submit;
    private SearchView searchView;

    public static Search_User_Fragment newInstance(String param1, String param2) {
        Search_User_Fragment fragment = new Search_User_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            list = getArguments().getParcelableArrayList("List");
            Log.e("Hello", list.size() + "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view=inflater.inflate(R.layout.fragment_search__user, container, false);
        View view = inflater.inflate(R.layout.search_dialog, container, false);
        initView(view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor localCustomerCursor = customerHelper.getCustomerSearchResult(newText);
                list.clear();
                if (localCustomerCursor.getCount() != 0) {
                    Toast.makeText(getActivity(), "Result found" + localCustomerCursor.getCount(), Toast.LENGTH_SHORT).show();
                    while (localCustomerCursor.moveToNext()) {
                        String mob = localCustomerCursor.getString(0);
                        String name = localCustomerCursor.getString(1);
                        String add = localCustomerCursor.getString(2);
                        list.add(new CustomerItem(name, mob, add));
                    }
                    CustomerAdapter cadapter = new CustomerAdapter(getActivity(), list);
                    customerRecycler.setAdapter(cadapter);
                } else {
                    Toast.makeText(getActivity(), "No result found", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        icon_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
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
                            Toast.makeText(getActivity(), "You are already registered!! \n Please try with another mobile number", Toast.LENGTH_SHORT).show();
                        } else {
                            customerHelper.insertNewCustomer(
                                    AddMob,
                                    AddName,
                                    AddAdress
                            );
                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

            }
        });
        return view;
    }

    private void initView(View view) {
        customerRecycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        icon_add = (ImageView) view.findViewById(R.id.icon_add);
        searchView = view.findViewById(R.id.search);
        customerHelper = new CustomerDatabaseHelper(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        customerRecycler.setLayoutManager(layoutManager);
        customerRecycler.setHasFixedSize(true);
//        CustomerAdapter cadapter = new CustomerAdapter(getActivity(), list);
//        customerRecycler.setAdapter(cadapter);
    }

}
