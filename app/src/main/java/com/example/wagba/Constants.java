package com.example.wagba;

import android.util.Log;

public class Constants {

    public static int RC_SIGN_IN = 3331;

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
