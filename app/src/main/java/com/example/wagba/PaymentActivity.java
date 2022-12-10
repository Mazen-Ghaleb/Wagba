package com.example.wagba;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        RecyclerView paymentRecyclerView = (RecyclerView) findViewById(R.id.paymentRecyclerView);
        paymentRecyclerView.setHasFixedSize(true);
        paymentRecyclerView.setLayoutManager((new LinearLayoutManager(this)));

        PaymentData[] paymentData = new PaymentData[]{
                new PaymentData("Rizo","39 EGP","Quantity: 2",R.drawable.kfc_rizo),
                new PaymentData("Shrimp Rizo","39 EGP","Quantity: 2",R.drawable.kfc_shrimp_rizo),
        };

        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentData,this);
        paymentRecyclerView.setAdapter(paymentAdapter);
    }
}
