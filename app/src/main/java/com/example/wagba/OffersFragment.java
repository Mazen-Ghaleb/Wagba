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

public class OffersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.offers_fragment,container,false);

        RecyclerView offersRecyclerView = (RecyclerView) rootView.findViewById(R.id.offersRecyclerView);
        offersRecyclerView.setHasFixedSize(true);
        offersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        OfferData[] offerData = new OfferData[]{
                new OfferData("Get 50 EGP off On Selected Items","Valid until 20/12/2022","KFC",R.drawable.kfc),
                new OfferData("Get 20 EGP off On Selected Items","Valid until 25/12/2022","McDonald's",R.drawable.mc_donalds),
                new OfferData("Get 30 EGP off On Selected Items","Valid until 28/12/2022","Papa John's",R.drawable.papa_johns),
                new OfferData("Get 10 EGP off On Selected Items","Valid until 20/12/2022","KFC",R.drawable.kfc),
                new OfferData("Get 20 EGP off On Selected Items","Valid until 20/12/2022","McDonald's",R.drawable.mc_donalds),
        };

        OfferAdapter offerAdapter = new OfferAdapter(offerData,getActivity());
        offersRecyclerView.setAdapter(offerAdapter);

        return rootView;
    }
}
