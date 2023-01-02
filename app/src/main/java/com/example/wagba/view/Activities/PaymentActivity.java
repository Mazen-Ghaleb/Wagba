package com.example.wagba.view.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Constants;
import com.example.wagba.view.AdapterData.OrderData;
import com.example.wagba.view.Adapters.PaymentAdapter;
import com.example.wagba.view.AdapterData.PaymentData;
import com.example.wagba.R;
import com.example.wagba.view.Fragments.CustomMapFragment;
import com.example.wagba.view.Fragments.GateMapFragment;
import com.example.wagba.view.Fragments.HomeFragment;
import com.google.android.gms.maps.MapFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class PaymentActivity extends BaseActivity {

    ImageView restaurantBanner;
    TextView restaurant;
    TextView totalPrice;
    TextView afterTaxPrice;
    TextView finalTotalPrice;
    RecyclerView paymentRecyclerView;
    Spinner gate;
    Spinner deliveries_times;
    Button confirmOrder;
    CustomMapFragment mapFragment;

    JSONObject orderJSON;
    Calendar calendar;
    Integer currentHour;
    Integer currentMin;
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
        confirmOrder = (Button) findViewById(R.id.order_btn);
        switchFragment(R.id.gateMap_container, new GateMapFragment());

        ArrayAdapter<String> gateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gates);
        gate.setAdapter(gateAdapter);


        try {
            orderJSON = new JSONObject(getIntentMessage());

            restaurant.setText(orderJSON.getString("restaurant"));
            Glide.with(PaymentActivity.this).load(orderJSON.getString("restaurantBanner")).into(restaurantBanner);

            if (orderJSON.has("totalPrice")) {
                Double totalPrice_value = orderJSON.getDouble("totalPrice");
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
        currentMin = calendar.get(Calendar.MINUTE);

        if (currentHour >= 13){
            if(currentHour == 13 && currentMin <30) {
                delivery_time[0] = getDateString(calendar) + " "+ delivery_time[0];
                delivery_time[1] = getDateString(calendar) + " "+ delivery_time[1];
            } else {
                calendar.add(Calendar.DATE, 1);
                delivery_time[0] = getDateString(calendar) + " " + delivery_time[0];
                delivery_time[1] = getDateString(calendar) + " " + delivery_time[1];
            }

        } else if (currentHour >= 10){
            if(currentHour == 10 && currentMin <30) {
                delivery_time[0] = getDateString(calendar) + " "+ delivery_time[0];
                delivery_time[1] = getDateString(calendar) + " "+ delivery_time[1];
            } else {
                String temp = delivery_time[0];

                delivery_time[0] = getDateString(calendar) + " "+ delivery_time[1];

                calendar.add(Calendar.DATE,1);
                delivery_time[1] = getDateString(calendar) + " "+ temp;
            }

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
            JSONObject jsonObject = orderJSON.getJSONObject("orderItems");
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

        AlertDialog confirmationAlert = getOrderConfirmationAlert();
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationAlert.show();
            }
        });
    }

    String getDateString(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+
                (calendar.get(Calendar.MONTH)+1) +"/" + // Plus one because Month counts from Zero
                calendar.get(Calendar.YEAR);
    }

    String getDateString(Calendar calendar, boolean hours){
        if (hours) {
            return calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    (calendar.get(Calendar.MONTH) + 1) + "/" + // Plus one because Month counts from Zero
                    calendar.get(Calendar.YEAR) + " "+
                    (calendar.get(Calendar.HOUR_OF_DAY) > 12 ? calendar.get(Calendar.HOUR_OF_DAY)%12 : calendar.get(Calendar.HOUR_OF_DAY))
                    + ":" + calendar.get(Calendar.MINUTE) + " " +
                    (calendar.get(Calendar.AM_PM) == 0 ? "am" : "pm");
        }
        else {
            return calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    (calendar.get(Calendar.MONTH) + 1) + "/" + // Plus one because Month counts from Zero
                    calendar.get(Calendar.YEAR);
        }
    }

    AlertDialog getOrderConfirmationAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to place order?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                try {
                    // Place order
                    dialog.dismiss();
                    switchPage(HomeActivity.class,true);
                    orderJSON.remove("restaurantBanner");
                    orderJSON.put("orderStatus", "Verifying");
                    orderJSON.put("orderTime",deliveries_times.getSelectedItem().toString());
                    orderJSON.put("orderedDate",getDateString(Calendar.getInstance(), true));
                    orderJSON.put("orderGate",gate.getSelectedItem().toString());
                    orderJSON.put("userId",firebaseAuth.getCurrentUser().getUid());
                    orderJSON.put("orderId",firebaseDatabase.getReference("Orders").push().getKey());

                    OrderData order = new OrderData(orderJSON);
                    addOrderToDatabase(order);
                }
                catch (Exception e){
                    Constants.logExceptionError(e);
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        return alert;
    }

    void addOrderToDatabase(OrderData order){
        try {
            firebaseDatabase.getReference("Orders").child(order.getOrderId()).setValue(order);
            firebaseDatabase.getReference("Users").child(order.getUserId()).child("Orders")
                    .child(order.getOrderId()).setValue(order);
        }
        catch (Exception e){
            Constants.logExceptionError(e);
        }
    }
    public String getSelectedGate(){
        return gate.getSelectedItem().toString();
    }
}
