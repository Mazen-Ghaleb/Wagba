package com.example.wagba.view.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.example.wagba.view.Activities.HomeActivity;
import com.example.wagba.view.Activities.MenuActivity;
import com.example.wagba.view.AdapterData.MenuItemData;
import com.example.wagba.view.Adapters.MenuItemAdapter;
import com.example.wagba.view.Adapters.RestaurantAdapter;
import com.example.wagba.view.AdapterData.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    TextView helloUser;
    LinearLayout currentOrderContainer;
    View currentOrder;
    TextView currentOrderRestaurant;
    TextView currentOrderStatus;
    TextView currentOrderETA;
    ImageView currentOrderImage;
    String currentOrderGate;

    LinearLayout frequentOrderContainer;
    RecyclerView frequentRecyclerView;
    RecyclerView recommendedRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);

        helloUser = (TextView) rootView.findViewById(R.id.hello_user);
        currentOrderRestaurant = (TextView) rootView.findViewById(R.id.current_restaurant);
        currentOrderStatus = (TextView) rootView.findViewById(R.id.current_status);
        currentOrderETA = (TextView) rootView.findViewById(R.id.current_eta);
        currentOrderImage = (ImageView) rootView.findViewById(R.id.current_restaurant_img);
        currentOrderContainer = (LinearLayout) rootView.findViewById(R.id.current_order_layout);
        frequentOrderContainer = (LinearLayout) rootView.findViewById(R.id.frequently_ordered_layout);
        currentOrder = (View) rootView.findViewById(R.id.current_order_card);
        frequentRecyclerView = (RecyclerView) rootView.findViewById(R.id.frequentRecyclerView);
        recommendedRecyclerView = (RecyclerView) rootView.findViewById(R.id.recommendedRecyclerView);

        currentOrderContainer.setVisibility(View.GONE);
        frequentOrderContainer.setVisibility(View.GONE);

        ((HomeActivity)getActivity()).setProfileData(helloUser);

        firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Orders")) {
                    for (DataSnapshot order : dataSnapshot.child("Orders").getChildren()){
                        if (!order.child("orderStatus").getValue().equals("Delivered") || !order.child("orderStatus").getValue().equals("Denied")){
                            currentOrderRestaurant.setText(order.child("restaurant").getValue().toString());
                            currentOrderStatus.setText(order.child("orderStatus").getValue().toString());
                            currentOrderETA.setText("60 mins");
                            currentOrderGate = order.child("orderGate").getValue().toString();

                            firebaseDatabase.getReference("Restaurants").child(order.child("restaurant").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot restaurant) {
                                    if (getActivity() == null) {
                                        return;
                                    }
                                    Glide.with(getContext()).load(restaurant.child("logo").getValue().toString()).into(currentOrderImage);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            currentOrderContainer.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    currentOrderContainer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(R.id.fragment_container, new TrackingFragment(),
                        currentOrderStatus.getText().toString() +
                                "+"+ currentOrderGate);
            }
        });

        frequentRecyclerView.setHasFixedSize(true);
        frequentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        getFrequentRestaurants(new OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Restaurant> restaurants) {
                RestaurantAdapter frequentAdapter = new RestaurantAdapter(restaurants,getActivity());
                frequentRecyclerView.setAdapter(frequentAdapter);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        recommendedRecyclerView.setHasFixedSize(true);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        getAllRestaurants(new OnGetDataListener() {
          @Override
          public void onSuccess(ArrayList<Restaurant> restaurants) {
              RestaurantAdapter recommendedAdapter = new RestaurantAdapter(restaurants,getActivity());
              recommendedRecyclerView.setAdapter(recommendedAdapter);
          }

          @Override
          public void onStart() {

          }

          @Override
          public void onFailure() {

          }
        });

        return rootView;
    }
    void getAllRestaurants(final OnGetDataListener listener){
        firebaseDatabase.getReference("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot restaurant : dataSnapshot.getChildren()) {
                    restaurants.add(new Restaurant(restaurant.child("name").getValue().toString(),
                            restaurant.child("logo").getValue().toString()));
                }
                listener.onSuccess(restaurants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                listener.onFailure();
            }
        });
    }

    void getFrequentRestaurants(final OnGetDataListener listener){
        firebaseDatabase.getReference("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer i =0;
                for (DataSnapshot restaurant : dataSnapshot.getChildren()) {
                    if (i > 3){
                        break;
                    }
                    restaurants.add(new Restaurant(restaurant.child("name").getValue().toString(),
                            restaurant.child("logo").getValue().toString()));
                    i++;
                }
                listener.onSuccess(restaurants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                listener.onFailure();
            }
        });
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(ArrayList<Restaurant> restaurants);
        void onStart();
        void onFailure();
    }
}

