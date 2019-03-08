/*
package com.example.ics.restaurantapp.fregment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ics.restaurantapp.R;

public class Menu_Item_Fragment extends DialogFragment {
    private String mParam1;
    private String mParam2;


    public Menu_Item_Fragment() {
        // Required empty public constructor
    }
    public static Menu_Item_Fragment newInstance(String param1, String param2) {
        Menu_Item_Fragment fragment = new Menu_Item_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1= getArguments().getString("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_menu_item, container, false);
        return view;
    }


}
*/
