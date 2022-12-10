package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity {

    TextView totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_menu);

        totalPrice = (TextView) findViewById(R.id.total_price);
        totalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView menuRecyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager((new LinearLayoutManager(this)));
        MenuItemData[] menuItemData = getKFCMenu();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String restaurantName = extras.getString("my_restaurant");

                    switch (restaurantName){
                        case "KFC":
                            menuItemData = getKFCMenu();
                            break;
                        case"McDonald's":
                            menuItemData = getMcDonaldMenu();
                            break;
                        case "Papa John's":
                            menuItemData = getPapaJohnMenu();
                            break;
                        case "Hardee's":
                            menuItemData = getHardeeMenu();
                            break;
                        case "Pizza Hut":
                            menuItemData = getPizzaHutMenu();
                            break;
                        case "Heart Attack":
                            menuItemData = getHeartAttackMenu();
                            break;
                        case "City Crepe":
                            menuItemData = getCityCrepeMenu();
                            break;
                        case "Burger King":
                            menuItemData = getBurgerKingMenu();
                            break;
                        case "Buffalo Burger":
                            menuItemData = getBuffaloBurgerMenu();
                            break;
                        case "Domino's Pizza":
                            menuItemData = getDominoPizzaMenu();
                            break;
                    }
        }
        MenuItemAdapter menuAdapter = new MenuItemAdapter(menuItemData,this);
        menuRecyclerView.setAdapter(menuAdapter);
    }
    MenuItemData[] getKFCMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","39 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getMcDonaldMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getPapaJohnMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getHardeeMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getPizzaHutMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getHeartAttackMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getCityCrepeMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getBurgerKingMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getBuffaloBurgerMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }
    MenuItemData[] getDominoPizzaMenu() {
        return new MenuItemData[]{
                new MenuItemData("Rizo","39 EGP" ,R.drawable.kfc_rizo),
                new MenuItemData("Mega Rizo","69 EGP" ,R.drawable.kfc_mega_rizo),
                new MenuItemData("Shrimp Rizo","54 EGP" ,R.drawable.kfc_shrimp_rizo),
                new MenuItemData("Dinner Combo","103 EGP" ,R.drawable.kfc_dinner_combo),
        };
    }

}
