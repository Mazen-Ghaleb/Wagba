package com.example.wagba;

public class SearchData {
    private String restaurant;
    private Integer restaurantImage;

    public SearchData(String restaurant, Integer restaurantImage) {
        this.restaurant = restaurant;
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(Integer restaurantImage) {
        this.restaurantImage = restaurantImage;
    }
}
