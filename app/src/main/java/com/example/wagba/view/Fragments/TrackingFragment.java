package com.example.wagba.view.Fragments;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.wagba.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

public class TrackingFragment extends CustomMapFragment {

    TextView trackingPhase;
    TextView trackingETA;

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

        trackingPhase.setText(getFragmentMessage());
        trackingETA.setText("ETA: 60 mins");

        return rootView;
    }


}


