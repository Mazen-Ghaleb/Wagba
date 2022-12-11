package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{

    RestaurantData[] restaurantData;
    Context context;

    public RestaurantAdapter(RestaurantData[] restaurantData, FragmentActivity activity) {
        this.restaurantData = restaurantData;
        this.context = activity;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.restaurant_item_list,parent,false);
        RestaurantAdapter.ViewHolder viewHolder = new RestaurantAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        final RestaurantData restaurantDataList = restaurantData[position];
        holder.restaurantImage.setImageResource(restaurantDataList.getRestaurantImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, restaurantDataList.getRestaurant(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MenuActivity.class);
                intent.putExtra("my_restaurant",restaurantDataList.getRestaurant());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView restaurantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.home_restaurant_image);
        }
    }
}
