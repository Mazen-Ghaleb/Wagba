package com.example.wagba.view.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wagba.Constants;
import com.example.wagba.R;
import com.example.wagba.view.Activities.HomeActivity;
import com.example.wagba.view.Activities.PaymentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GateMapFragment extends CustomMapFragment {

    Spinner gate;
    Marker currentGate;

    public GateMapFragment(){
        super(R.id.gate_map, R.layout.gate_map_fragment);
    }

    public GateMapFragment(@IdRes int mapId, @LayoutRes int parentFragment){
        super(mapId, parentFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        gate = (Spinner)((PaymentActivity)getActivity()).findViewById(R.id.gate_list);

        return inflater.inflate(R.layout.gate_map_fragment, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        currentGate = mMap.addMarker(getMarkerOptions("Gate 3",Constants.Gate3));

        gate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        currentGate.remove();
                        currentGate = mMap.addMarker(getMarkerOptions("Gate 3",Constants.Gate3));
                        break;
                    case 1:
                        currentGate.remove();
                        currentGate = mMap.addMarker(getMarkerOptions("Gate 4",Constants.Gate4));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private MarkerOptions getMarkerOptions(String title, LatLng position){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(bitmapDescriptorFromVector(getContext(),R.drawable.ic_gate));
        markerOptions.position(position);
        markerOptions.title(title);
        return  markerOptions;
    }

}