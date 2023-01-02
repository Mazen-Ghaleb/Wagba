package com.example.wagba.view.Adapters;

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

import com.bumptech.glide.Glide;
import com.example.wagba.view.AdapterData.OrderData;
import com.example.wagba.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    ArrayList<OrderData> orderData;
    Context context;

    public OrderAdapter (ArrayList<OrderData> orderData, FragmentActivity activity) {
        this.orderData = orderData;
        this.context = activity;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_item_list,parent,false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        final OrderData orderDataList = orderData.get(position);
        holder.orderStatus.setText(orderDataList.getOrderStatus());
        holder.orderId.setText(orderDataList.getOrderId());
        holder.orderedDate.setText(orderDataList.getOrderedDate());
        holder.orderRestaurant.setText(orderDataList.getRestaurant());
        holder.orderPrice.setText(orderDataList.getTotalPrice());
        Glide.with(context).load(orderDataList.getRestaurantImage()).into(holder.restaurantImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, orderDataList.getOrderId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView restaurantImage;
        TextView orderStatus;
        TextView orderId;
        TextView orderedDate;
        TextView orderRestaurant;
        TextView orderPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.order_image);
            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            orderId = (TextView) itemView.findViewById(R.id.order_id);
            orderedDate = (TextView) itemView.findViewById(R.id.ordered_date);
            orderRestaurant = (TextView) itemView.findViewById(R.id.order_restaurant);
            orderPrice = (TextView) itemView.findViewById(R.id.order_total_price);
        }
    }
}

