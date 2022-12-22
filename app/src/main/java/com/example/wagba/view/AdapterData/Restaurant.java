package com.example.wagba.view.AdapterData;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String banner;
    private String logo;
    private ArrayList<MenuItemData> menuItems;

    public Restaurant(String name, String logo, String banner, ArrayList<MenuItemData> menuItems) {
        this.name = name;
        this.logo = logo;
        this.banner = banner;
        this.menuItems = menuItems;
    }

    public Restaurant(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public ArrayList<MenuItemData> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItemData> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItemData menuItem) {
        this.menuItems.add(menuItem);
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
