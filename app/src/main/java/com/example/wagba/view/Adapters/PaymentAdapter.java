package com.example.wagba.view.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Constants;
import com.example.wagba.view.AdapterData.MenuItemData;
import com.example.wagba.view.AdapterData.PaymentData;
import com.example.wagba.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{

    ArrayList<PaymentData> paymentData;
    Context context;

    public PaymentAdapter (ArrayList<PaymentData> paymentData, FragmentActivity activity) {
        this.paymentData = paymentData;
        this.context = activity;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.payment_item_list,parent,false);
        PaymentAdapter.ViewHolder viewHolder = new PaymentAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {
        final PaymentData paymentDataList = paymentData.get(position);
        holder.itemName.setText(paymentDataList.getItemName());
        holder.itemPrice.setText(paymentDataList.getItemPrice());
        holder.itemQuantity.setText(paymentDataList.getQuantity());
        Glide.with(context).load(paymentDataList.getItemImage()).into(holder.itemImage);

        double item_price = Double.parseDouble(paymentDataList.getItemPrice().split("\\s+")[0]);
        int item_quantity = Integer.parseInt(paymentDataList.getQuantity().split("\\s+")[1]);
        holder.totalItemPrice.setText(Constants.DoublePrecision_toString(item_price*item_quantity) + " EGP");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, paymentDataList.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView totalItemPrice;
        TextView itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.payment_item_image);
            itemName = (TextView) itemView.findViewById(R.id.payment_item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.payment_item_price);
            totalItemPrice = (TextView) itemView.findViewById(R.id.payment_item_total_price);
            itemQuantity = (TextView) itemView.findViewById(R.id.payment_item_quantity);
        }
    }
}

