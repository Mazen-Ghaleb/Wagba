package com.example.wagba.view.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.view.Adapters.OrderAdapter;
import com.example.wagba.view.AdapterData.OrderData;
import com.example.wagba.R;

public class OrderHistoryFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_history_fragment,container,false);

        RecyclerView orderRecyclerView = (RecyclerView) rootView.findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        orderRecyclerView.setLayoutManager(linearLayoutManager);


        OrderData[] orderData = new OrderData[]{
                new OrderData("Order #200","Delivered 10 Oct 2022, 7:51 PM","KFC",R.drawable.kfc),
                new OrderData("Order #201","Delivered 11 Oct 2022, 3:20 PM","McDonald's",R.drawable.mc_donalds),
                new OrderData("Order #202","Delivered 12 Oct 2022, 5:51 PM","Papa John's",R.drawable.papa_johns),
                new OrderData("Order #203","Delivered 13 Oct 2022, 2:10 PM","KFC",R.drawable.kfc),
                new OrderData("Order #204","Delivered 14 Oct 2022, 9:21 PM","McDonald's",R.drawable.mc_donalds),
        };

        OrderAdapter orderAdapter = new OrderAdapter(orderData,getActivity());
        orderRecyclerView.setAdapter(orderAdapter);

        return rootView;
    }
}
