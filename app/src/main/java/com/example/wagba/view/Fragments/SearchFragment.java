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

import com.example.wagba.R;
import com.example.wagba.view.AdapterData.Restaurant;
import com.example.wagba.view.Adapters.SearchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment,container,false);

        RecyclerView searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllRestaurants(new OnGetDataListener() {
            @Override
            public void onSuccess(ArrayList<Restaurant> restaurants) {
                SearchAdapter searchAdapter = new SearchAdapter(restaurants,getActivity());
                searchRecyclerView.setAdapter(searchAdapter);
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
    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(ArrayList<Restaurant> restaurants);
        void onStart();
        void onFailure();
    }
}
