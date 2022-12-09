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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    SearchData[] searchData;
    Context context;

    public SearchAdapter (SearchData[] searchData, FragmentActivity activity) {
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
        final SearchData searchDataList = searchData[position];
        holder.searchRestaurant.setText(searchDataList.getRestaurant());
        holder.restaurantImage.setImageResource(searchDataList.getRestaurantImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, searchDataList.getRestaurant(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchData.length;
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

