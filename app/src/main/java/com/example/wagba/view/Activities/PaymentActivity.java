package com.example.wagba.view.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Constants;
import com.example.wagba.view.AdapterData.MenuItemData;
import com.example.wagba.view.Adapters.PaymentAdapter;
import com.example.wagba.view.AdapterData.PaymentData;
import com.example.wagba.R;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

public class PaymentActivity extends BaseActivity {

    ImageView restaurantBanner;
    TextView restaurant;
    TextView totalPrice;
    TextView afterTaxPrice;
    TextView finalTotalPrice;

    RecyclerView paymentRecyclerView;

    Spinner gate;
    Spinner deliveries_times;
    JSONObject order;

    Calendar calendar;
    Integer currentHour;

    String[] gates = new String[]{"Gate 3", "Gate 4"};
    String[] delivery_time = new String[]{"12:00 pm", "3:00 pm"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        calendar = Calendar.getInstance();
        totalPrice = (TextView) findViewById(R.id.total_price_val);
        afterTaxPrice = (TextView) findViewById(R.id.taxes_val);
        finalTotalPrice = (TextView) findViewById(R.id.final_cost);
        restaurant = (TextView) findViewById(R.id.payment_restaurant_name);
        restaurantBanner = (ImageView) findViewById(R.id.payment_restaurant_banner);
        gate = (Spinner) findViewById(R.id.gate_list);
        deliveries_times = (Spinner) findViewById(R.id.time_list);
        paymentRecyclerView = (RecyclerView) findViewById(R.id.paymentRecyclerView);


        ArrayAdapter<String> gateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gates);
        gate.setAdapter(gateAdapter);


        try {
            order = new JSONObject(getIntentMessage());

            restaurant.setText(order.getString("restaurant"));
            Glide.with(PaymentActivity.this).load(order.getString("restaurantBanner")).into(restaurantBanner);

            if (order.has("totalPrice")) {
                Double totalPrice_value = order.getDouble("totalPrice");
                totalPrice.setText(Constants.DoublePrecision_toString(totalPrice_value) + " EGP");
                afterTaxPrice.setText(Constants.DoublePrecision_toString(totalPrice_value * 1.14) + " EGP");
                finalTotalPrice.setText(Constants.DoublePrecision_toString((totalPrice_value * 1.14) + 10) + " EGP");
            }
            else {
                super.onBackPressed();
            }
        }
        catch (Exception e){
            Constants.logExceptionError(e);
        }

        currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (currentHour >= 13){
            calendar.add(Calendar.DATE,1);
            delivery_time[0] = getDateString(calendar) + " "+ delivery_time[0];
            delivery_time[1] = getDateString(calendar) + " "+ delivery_time[1];

        } else if (currentHour >= 10){

            String temp = delivery_time[0];

            delivery_time[0] = getDateString(calendar) + " "+ delivery_time[1];

            calendar.add(Calendar.DATE,1);
            delivery_time[1] = getDateString(calendar) + " "+ temp;

        } else {
            delivery_time[0] = getDateString(calendar) + " "+ delivery_time[0];
            delivery_time[1] = getDateString(calendar) + " "+ delivery_time[1];
        }

        ArrayAdapter<String> deliveriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, delivery_time);
        deliveries_times.setAdapter(deliveriesAdapter);

        paymentRecyclerView.setHasFixedSize(true);
        paymentRecyclerView.setLayoutManager((new LinearLayoutManager(this)));

        ArrayList<PaymentData> paymentData = new ArrayList<PaymentData>();

        try {
            JSONObject jsonObject = order.getJSONObject("orderItems");
            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext()) {
                String key = keys.next();

                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject orderItem = (JSONObject) jsonObject.get(key);
                    paymentData.add(new PaymentData(orderItem.getString("name"),
                            orderItem.getString("price"),
                            "Quantity: " + orderItem.getInt("quantity"),
                            orderItem.getString("image")));
                }
            }
        }
        catch (Exception e){
            Constants.logExceptionError(e);
        }

        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentData,this);
        paymentRecyclerView.setAdapter(paymentAdapter);
    }

    String getDateString(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+
                (calendar.get(Calendar.MONTH)+1) +"/" + // Plus one because Month counts from Zero
                calendar.get(Calendar.YEAR);
    }

}
