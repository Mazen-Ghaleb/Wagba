package com.example.wagba;

public class RestaurantData {
    private String restaurant;
    private Integer restaurantImage;

    public RestaurantData(String restaurant, Integer restaurantImage) {
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
