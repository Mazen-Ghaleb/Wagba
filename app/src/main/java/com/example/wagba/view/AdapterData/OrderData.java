package com.example.wagba.view.AdapterData;

public class OrderData {
    private String orderNumber;
    private String orderDate;
    private String restaurant;
    private Integer restaurantImage;

    public OrderData(String orderNumber, String orderDate, String restaurant, Integer restaurantImage) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.restaurant = restaurant;
        this.restaurantImage = restaurantImage;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public Integer getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(Integer restaurantImage) {
        this.restaurantImage = restaurantImage;
    }
}
