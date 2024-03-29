package com.example.wagba.view.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Constants;
import com.example.wagba.view.Activities.MenuActivity;
import com.example.wagba.view.AdapterData.MenuItemData;
import com.example.wagba.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder>{

    ArrayList<MenuItemData> menuItemData;
    Context context;
    private QuantityChangeListener quantityChangeListener;

    public MenuItemAdapter (ArrayList<MenuItemData> menuItemData, FragmentActivity activity, QuantityChangeListener quantityChangeListener) {
        this.menuItemData = menuItemData;
        this.context = activity;
        this.quantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public MenuItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_item_list,parent,false);
        MenuItemAdapter.ViewHolder viewHolder = new MenuItemAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemAdapter.ViewHolder holder, int position) {
        final MenuItemData menuItemDataList = menuItemData.get(position);

        holder.itemName.setText(menuItemDataList.getItemName());
        holder.itemPrice.setText(menuItemDataList.getItemPrice());
        holder.itemImageSource = menuItemDataList.getItemImage();
        Glide.with(context).load(holder.itemImageSource).into(holder.itemImage);
//        holder.itemImage.setImageResource(menuItemDataList.());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, menuItemDataList.getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        String itemImageSource;
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
        Button addItem;
        Button removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemQuantity = (TextView) itemView.findViewById(R.id.item_quantity);
            addItem = (Button) itemView.findViewById(R.id.add_item_btn);
            removeItem = (Button) itemView.findViewById(R.id.remove_item_btn);

            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer quantity = Integer.parseInt(itemQuantity.getText().toString());

                    if (quantity < 99) {
                        itemQuantity.setText(Integer.toString(quantity+1));
                        String orderItem =  getOrderItem();

                        quantityChangeListener.onQuantityChange(1,
                                Double.parseDouble(itemPrice.getText().toString().split("\\s+")[0]),
                                orderItem);
                    }
                }
            });

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer quantity = Integer.parseInt(itemQuantity.getText().toString());

                    if (quantity != 0) {
                        itemQuantity.setText(Integer.toString(quantity-1));
                        String orderItem =  getOrderItem();

                        quantityChangeListener.onQuantityChange(-1,
                                Double.parseDouble(itemPrice.getText().toString().split("\\s+")[0]),
                                orderItem);
                    }
                }
            });
        }

        String  getOrderItem(){
            String orderItemString = "";
            try {
                JSONObject orderItem = new JSONObject();
                orderItem.put("name", itemName.getText().toString());
                orderItem.put("image", itemImageSource);
                orderItem.put("price", itemPrice.getText().toString());
                orderItem.put("quantity", Integer.parseInt(itemQuantity.getText().toString()));

                orderItemString = orderItem.toString();
            }
            catch (Exception e){
                Constants.logExceptionError(e);
            }
            return orderItemString;
        }
    }

    public interface QuantityChangeListener {
        void onQuantityChange(Integer quantityDifference, Double itemPrice, String OrderItem);
    }
}

