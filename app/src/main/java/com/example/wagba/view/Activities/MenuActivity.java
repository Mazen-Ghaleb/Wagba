package com.example.wagba.view.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Constants;
import com.example.wagba.view.AdapterData.MenuItemData;
import com.example.wagba.R;
import com.example.wagba.view.AdapterData.Restaurant;
import com.example.wagba.view.Adapters.MenuItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity {

    ImageView restaurantBanner;
    TextView restaurantName;
    TextView totalPrice;

    Double totalPriceValue;

    JSONObject order;
    JSONObject orderItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_menu);

        totalPriceValue = 0.0;
        order= new JSONObject();
        orderItems = new JSONObject();

        restaurantBanner = (ImageView) findViewById(R.id.restaurant_banner);
        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        totalPrice = (TextView) findViewById(R.id.total_price);

        totalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totalPriceValue != 0.0) {
                    try {
                        order.put("restaurant", restaurantName.getText().toString());
                        order.put("orderItems", orderItems);

                        switchPage(PaymentActivity.class, order.toString());
                    } catch (Exception e) {
                        Constants.logExceptionError(e);
                    }
                }
            }
        });

        RecyclerView menuRecyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager((new LinearLayoutManager(this)));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getRestaurant(extras.getString("my_restaurant"), new OnGetDataListener() {
                @Override
                public void onSuccess(Restaurant restaurant) {
                    restaurantName.setText(restaurant.getName());
                    try {
                        order.put("restaurantBanner", restaurant.getBanner());
                    }
                    catch (Exception e){
                        Constants.logExceptionError(e);
                    }
                    Glide.with(MenuActivity.this).load(restaurant.getBanner()).into(restaurantBanner);
                    MenuItemAdapter menuAdapter = new MenuItemAdapter(restaurant.getMenuItems(), MenuActivity.this, new MenuItemAdapter.QuantityChangeListener() {
                        @Override
                        public void onQuantityChange(Integer quantityDifference,  Double itemPrice, String OrderItem) {
//                            totalPriceValue = totalPriceValue + (newQuantity-oldQuantity)*itemPrice;
                            totalPriceValue = totalPriceValue + (quantityDifference)*itemPrice;
                            totalPrice.setText("Total price is "+ Constants.DoublePrecision_toString(totalPriceValue)+" EGP");
                            try {
                                JSONObject jsonObject = new JSONObject(OrderItem);
                                if (totalPriceValue == 0.0){
                                    order.remove("totalPrice");

                                    orderItems.remove(jsonObject.getString("name"));
                                }
                                else {
                                    order.put("totalPrice", totalPriceValue);

                                    orderItems.put(jsonObject.getString("name"),jsonObject);
                                }
                            }
                            catch (Exception e){
                                Constants.logExceptionError(e);
                            }
                        }
                    });
                    menuRecyclerView.setAdapter(menuAdapter);
                    Log.d("firebaseDatabase", "Success");
                }
                @Override
                public void onStart() {
                    //when starting
                    Log.d("firebaseDatabase", "Started");
                }

                @Override
                public void onFailure() {
                    Log.d("firebaseDatabase", "Failed");
                }
            });
        }
    }

    void getRestaurant(String restaurantName, final OnGetDataListener listener){
         firebaseDatabase.getReference("Restaurants").child(restaurantName).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 ArrayList<MenuItemData> menuItems = new ArrayList<MenuItemData>();
                 // This method is called once with the initial value and again
                 // whenever data at this location is updated.
                 String logo = dataSnapshot.child("logo").getValue().toString();
                 String banner = dataSnapshot.child("banner").getValue().toString();
                 for (DataSnapshot menuItem : dataSnapshot.child("MenuItems").getChildren()) {
                     menuItems.add(new MenuItemData(menuItem.child("name").getValue().toString(),
                                     menuItem.child("price").getValue().toString(),
                                     menuItem.child("picture").getValue().toString()));
                 }
                 listener.onSuccess(new Restaurant(restaurantName,logo,banner,menuItems));
             }

             @Override
             public void onCancelled(DatabaseError error) {
                 // Failed to read value
                 listener.onFailure();
             }
         });
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(Restaurant restaurant);
        void onStart();
        void onFailure();
    }
}
