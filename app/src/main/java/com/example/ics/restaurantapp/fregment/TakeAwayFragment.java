package com.example.ics.restaurantapp.fregment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.adapter.AllListAdapterNew;
import com.example.ics.restaurantapp.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TakeAwayFragment extends Fragment {
    RecyclerView card_recyclerview;
    Context context;
    ArrayList<HashMap<String,String>> d_list;
    CardAdapter cardAdapter;

    OrderDatabseHelper orderDatabseHelper;
    List<orderListItem> takeawayOrderList;

    RecyclerView recyclerView;

    public TakeAwayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        takeawayOrderList = new ArrayList<>();
        takeawayOrderList.clear();

        orderDatabseHelper = new OrderDatabseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_take_away, container,false);
//
//        Cursor localCursor = orderDatabseHelper.getTakeAwayOrderList();
//        while (localCursor.moveToNext()){
//            takeawayOrderList.add(new orderListItem(
//                    localCursor.getInt(0),
//                    localCursor.getString(1),
//                    localCursor.getString(2),
//                    localCursor.getString(3),
//                    localCursor.getString(4),
//                    localCursor.getString(5),
//                    localCursor.getInt(6),
//                    localCursor.getInt(7),
//                    localCursor.getString(8),
//                    localCursor.getString(9)
//            ));
//        }

        recyclerView = view.findViewById(R.id.recycler_all);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        AllListAdapterNew adapter = new AllListAdapterNew(getActivity(),takeawayOrderList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}
