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


public class MenuItemAdapter extends RecyclerView.Adapter<com.example.wagba.MenuItemAdapter.ViewHolder>{

    MenuItemData[] menuItemData;
    Context context;

    public MenuItemAdapter (MenuItemData[] menuItemData, FragmentActivity activity) {
        this.menuItemData = menuItemData;
        this.context = activity;
    }

    @NonNull
    @Override
    public com.example.wagba.MenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_item_list,parent,false);
        com.example.wagba.MenuItemAdapter.ViewHolder viewHolder = new com.example.wagba.MenuItemAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.wagba.MenuItemAdapter.ViewHolder holder, int position) {
        final MenuItemData menuItemDataList = menuItemData[position];
        holder.itemName.setText(menuItemDataList.getItemName());
        holder.itemPrice.setText(menuItemDataList.getItemPrice());
        holder.itemImage.setImageResource(menuItemDataList.getItemImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, menuItemDataList.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItemData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}

