package com.example.wagba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment,container,false);

        RecyclerView searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RestaurantData[] searchData = new RestaurantData[]{
                new RestaurantData("KFC",R.drawable.kfc),
                new RestaurantData("McDonald's",R.drawable.mc_donalds),
                new RestaurantData("Papa John's",R.drawable.papa_johns),
        };

        SearchAdapter searchAdapter = new SearchAdapter(searchData,getActivity());
        searchRecyclerView.setAdapter(searchAdapter);

        return rootView;
    }
}
