package com.example.wagba.view.AdapterData;

public class OfferData {
    private String title;
    private String validDate;
    private String restaurant;
    private Integer restaurantImage;

    public OfferData(String title, String validDate, String restaurant, Integer restaurantImage) {
        this.title = title;
        this.validDate = validDate;
        this.restaurant = restaurant;
        this.restaurantImage = restaurantImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
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
