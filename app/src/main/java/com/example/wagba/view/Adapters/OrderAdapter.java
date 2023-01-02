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

import com.example.wagba.view.AdapterData.OrderData;
import com.example.wagba.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    OrderData[] orderData;
    Context context;

    public OrderAdapter (OrderData[] orderData, FragmentActivity activity) {
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
        final OrderData orderDataList = orderData[position];
        holder.orderNumber.setText(orderDataList.getOrderId());
        holder.orderDate.setText(orderDataList.getOrderedDate());
        holder.orderRestaurant.setText(orderDataList.getRestaurant());
        holder.restaurantImage.setImageResource(orderDataList.getRestaurantImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, orderDataList.getOrderId(), Toast.LENGTH_SHORT).show();
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

