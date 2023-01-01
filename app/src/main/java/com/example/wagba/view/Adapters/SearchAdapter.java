package com.example.wagba.view.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.wagba.view.Activities.MenuActivity;
import com.example.wagba.R;
import com.example.wagba.view.AdapterData.Restaurant;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ArrayList<Restaurant> searchData;
    Context context;

    public SearchAdapter (ArrayList<Restaurant> searchData, FragmentActivity activity) {
        this.searchData = searchData;
        this.context = activity;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_item_list,parent,false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        final Restaurant searchDataList = searchData.get(position);
        holder.searchRestaurant.setText(searchDataList.getName());
        Glide.with(context).load(searchDataList.getLogo()).into(holder.restaurantImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, searchDataList.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("my_restaurant",searchDataList.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView restaurantImage;
        TextView searchRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.search_image);
            searchRestaurant = (TextView) itemView.findViewById(R.id.search_restaurant);
        }
    }
}

