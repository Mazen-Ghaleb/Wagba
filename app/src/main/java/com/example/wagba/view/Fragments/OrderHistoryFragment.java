package com.example.wagba.view.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.view.Adapters.OrderAdapter;
import com.example.wagba.view.AdapterData.OrderData;
import com.example.wagba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderHistoryFragment extends BaseFragment {

    RecyclerView orderRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_history_fragment,container,false);

        orderRecyclerView = (RecyclerView) rootView.findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        orderRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<OrderData> orderData = new ArrayList<OrderData>();


        firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if (dataSnapshot.hasChild("Orders")) {
                      for (DataSnapshot order : dataSnapshot.child("Orders").getChildren()) {
                          if (order.child("orderStatus").getValue() != "Delivered") {

                              firebaseDatabase.getReference("Restaurants").child(order.child("restaurant").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot restaurant) {
                                      if (getActivity() == null) {
                                          return;
                                      }

                                      firebaseDatabase.getReference("Restaurants").child(order.child("restaurant").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot restaurant) {
                                              if (getActivity() == null) {
                                                  return;
                                              }
                                              orderData.add(new OrderData(
                                                      order.child("userId").getValue().toString(),
                                                      "Order Id: "+ order.child("orderId").getValue().toString(),
                                                      order.child("orderStatus").getValue().toString(),
                                                      order.child("orderTime").getValue().toString(),
                                                      "Ordered at "+ order.child("orderedDate").getValue().toString(),
                                                      order.child("orderGate").getValue().toString(),
                                                      order.child("totalPrice").getValue().toString()+" EGP",
                                                      order.child("restaurant").getValue().toString(),
                                                      restaurant.child("logo").getValue().toString()
                                                      ));

                                              OrderAdapter orderAdapter = new OrderAdapter(orderData,getActivity());
                                              orderRecyclerView.setAdapter(orderAdapter);
                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {

                                          }
                                      });
                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError error) {

                                  }
                              });
                          }
                      }}
              }
              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });

//        []{
//            new OrderData("Order #200","Delivered 10 Oct 2022, 7:51 PM","KFC",R.drawable.kfc),
//            new OrderData("Order #201","Delivered 11 Oct 2022, 3:20 PM","McDonald's",R.drawable.mc_donalds),
//            new OrderData("Order #202","Delivered 12 Oct 2022, 5:51 PM","Papa John's",R.drawable.papa_johns),
//            new OrderData("Order #203","Delivered 13 Oct 2022, 2:10 PM","KFC",R.drawable.kfc),
//            new OrderData("Order #204","Delivered 14 Oct 2022, 9:21 PM","McDonald's",R.drawable.mc_donalds),
//        };


        return rootView;
    }
}
