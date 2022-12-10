package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment,container,false);

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

        RestaurantData[] frequentData = new RestaurantData[]{
                new RestaurantData("KFC",R.drawable.kfc),
                new RestaurantData( "McDonald's",R.drawable.mc_donalds),
                new RestaurantData("Papa John's",R.drawable.papa_johns),
        };

        RestaurantAdapter frequentAdapter = new RestaurantAdapter(frequentData,getActivity());
        frequentRecyclerView.setAdapter(frequentAdapter);

        RecyclerView recommendedRecyclerView = (RecyclerView) rootView.findViewById(R.id.recommendedRecyclerView);
        recommendedRecyclerView.setHasFixedSize(true);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        RestaurantData[] recommendedData = new RestaurantData[]{
                new RestaurantData("KFC",R.drawable.kfc),
                new RestaurantData( "McDonald's",R.drawable.mc_donalds),
                new RestaurantData("Papa John's",R.drawable.papa_johns),
                new RestaurantData("Hardee's",R.drawable.hardees),
                new RestaurantData("Pizza Hut",R.drawable.pizza_hut),
                new RestaurantData("Heart Attack",R.drawable.heart_attack),
                new RestaurantData("City Crepe",R.drawable.city_crepe),
                new RestaurantData("Burger King",R.drawable.burger_king),
                new RestaurantData("Buffalo Burger",R.drawable.buffalo_burger),
                new RestaurantData("Domino's Pizza",R.drawable.domino_pizza),
        };

        RestaurantAdapter recommendedAdapter = new RestaurantAdapter(recommendedData,getActivity());
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        return rootView;
    }
}
