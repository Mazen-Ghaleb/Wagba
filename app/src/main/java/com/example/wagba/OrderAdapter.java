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

public class OrderAdapter extends RecyclerView.Adapter<com.example.wagba.OrderAdapter.ViewHolder>{

    OrderData[] orderData;
    Context context;

    public OrderAdapter (OrderData[] orderData, FragmentActivity activity) {
        this.orderData = orderData;
        this.context = activity;
    }

    @NonNull
    @Override
    public com.example.wagba.OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_item_list,parent,false);
        com.example.wagba.OrderAdapter.ViewHolder viewHolder = new com.example.wagba.OrderAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.wagba.OrderAdapter.ViewHolder holder, int position) {
        final OrderData orderDataList = orderData[position];
        holder.orderNumber.setText(orderDataList.getOrderNumber());
        holder.orderDate.setText(orderDataList.getOrderDate());
        holder.orderRestaurant.setText(orderDataList.getRestaurant());
        holder.restaurantImage.setImageResource(orderDataList.getRestaurantImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, orderDataList.getOrderNumber(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView restaurantImage;
        TextView orderNumber;
        TextView orderDate;
        TextView orderRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.order_image);
            orderNumber = (TextView) itemView.findViewById(R.id.order_number);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderRestaurant = (TextView) itemView.findViewById(R.id.order_restaurant);
        }
    }
}

