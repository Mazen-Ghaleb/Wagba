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

import com.example.wagba.view.Activities.MenuActivity;
import com.example.wagba.view.AdapterData.OfferData;
import com.example.wagba.R;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder>{

    OfferData[] offerData;
    Context context;

    public OfferAdapter (OfferData[] offerData, FragmentActivity activity) {
        this.offerData = offerData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.offer_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OfferData offerDataList = offerData[position];
        holder.offerTitle.setText(offerDataList.getTitle());
        holder.offerDate.setText(offerDataList.getValidDate());
        holder.offerRestaurant.setText(offerDataList.getRestaurant());
        holder.restaurantImage.setImageResource(offerDataList.getRestaurantImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, offerDataList.getRestaurant(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("my_restaurant",offerDataList.getRestaurant());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView restaurantImage;
        TextView offerTitle;
        TextView offerDate;
        TextView offerRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = (ImageView) itemView.findViewById(R.id.offer_image);
            offerTitle = (TextView) itemView.findViewById(R.id.offer_title);
            offerDate = (TextView) itemView.findViewById(R.id.offer_date);
            offerRestaurant = (TextView) itemView.findViewById(R.id.offer_restaurant);
        }
    }
}
