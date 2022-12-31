package com.example.wagba.view.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);

        helloUser = (TextView) rootView.findViewById(R.id.hello_user);
        ((HomeActivity)getActivity()).setProfileData(helloUser);

        View currentOrder = (View) rootView.findViewById(R.id.current_order_card);
        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TrackingFragment()).commit();
            }
        });

        RecyclerView frequentRecyclerView = (RecyclerView) rootView.findViewById(R.id.frequentRecyclerView);
        frequentRecyclerView.setHasFixedSize(true);
        frequentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        getfrequentRestaurants(new OnGetDataListener() {
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

        RecyclerView recommendedRecyclerView = (RecyclerView) rootView.findViewById(R.id.recommendedRecyclerView);
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

    void getfrequentRestaurants(final OnGetDataListener listener){
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

