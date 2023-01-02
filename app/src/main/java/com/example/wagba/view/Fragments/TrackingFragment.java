package com.example.wagba.view.Fragments;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.wagba.Constants;
import com.example.wagba.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class TrackingFragment extends CustomMapFragment {

    TextView trackingPhase;
    TextView trackingETA;

    String orderGate;

    public TrackingFragment(){
        super(R.id.tracking_map, R.layout.tracking_fragment);
    }

    public TrackingFragment(@IdRes int mapId, @LayoutRes int parentFragment){
        super(mapId, parentFragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tracking_fragment, container, false);

        trackingPhase = (TextView) rootView.findViewById(R.id.tracking_phase);
        trackingETA = (TextView) rootView.findViewById(R.id.tracking_ETA);

        trackingPhase.setText(getFragmentMessage().split("\\+")[0]);

        trackingETA.setText("ETA: 60 mins");

        orderGate = getFragmentMessage().split("\\+")[1];
        Log.v("test",orderGate);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        if(orderGate.equals("Gate 3")){
            mMap.addMarker(getMarkerOptions("Gate 3",Constants.Gate3));
        } else if (orderGate.equals("Gate 4")) {
            mMap.addMarker(getMarkerOptions("Gate 4",Constants.Gate4));
        }
    }
}


