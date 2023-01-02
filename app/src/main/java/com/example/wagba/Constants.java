package com.example.wagba;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Constants {

    public static int RC_SIGN_IN = 3331;
    public static String MESSAGE_KEY = "inMessage";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int UPDATE_PERIOD_IN_SECONDS = 5;
    public static final LatLng Gate3 = new LatLng(30.06389403476676, 31.277638728755054);
    public static final LatLng Gate4 = new LatLng(30.062823880128082, 31.278094704234224);

    // Converts Double to String with specific precision if needed.
    public static String DoublePrecision_toString(double value) {
        if (value == (long) value)
            return String.format("%d", (long) value);
        else {
            if ((long) (((long) value - value) * 100) != 0)
                return String.format("%.2f", value); // Only 2 decimal units
            else
                return String.format("%.3f", value); // Only 3 decimal units
//            else
//                return String.format("%s",value); // All decimal units
        }
    }
    public static void logExceptionError(Exception e) {
        Log.v("test", "Error : " + e.getMessage());
    }
}
