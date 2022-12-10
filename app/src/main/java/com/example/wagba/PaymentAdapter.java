package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentAdapter extends RecyclerView.Adapter<com.example.wagba.PaymentAdapter.ViewHolder>{

    PaymentData[] paymentData;
    Context context;

    public PaymentAdapter (PaymentData[] paymentData, FragmentActivity activity) {
        this.paymentData = paymentData;
        this.context = activity;
    }

    @NonNull
    @Override
    public com.example.wagba.PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.payment_item_list,parent,false);
        com.example.wagba.PaymentAdapter.ViewHolder viewHolder = new com.example.wagba.PaymentAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.wagba.PaymentAdapter.ViewHolder holder, int position) {
        final PaymentData paymentDataList = paymentData[position];
        holder.itemName.setText(paymentDataList.getItemName());
        holder.itemPrice.setText(paymentDataList.getItemPrice());
        holder.itemQuantity.setText(paymentDataList.getQuantity());
        holder.itemImage.setImageResource(paymentDataList.getItemImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, paymentDataList.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.payment_item_image);
            itemName = (TextView) itemView.findViewById(R.id.payment_item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.payment_item_price);
            itemQuantity = (TextView) itemView.findViewById(R.id.payment_item_quantity);
        }
    }
}

