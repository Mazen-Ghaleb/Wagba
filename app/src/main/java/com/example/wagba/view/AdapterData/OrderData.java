package com.example.wagba.view.AdapterData;

import com.example.wagba.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderData {
    private String userId;
    private String orderId;
    private String orderStatus;
    private String orderTime;
    private String orderedDate;
    private String orderGate;
    private String totalPrice;
    private String restaurant;
    private String restaurantImage;
    private Map<String, OrderItem> orderItems;

    public OrderData(JSONObject order) {
        try {
            this.userId = order.getString("userId");
            this.orderId = order.getString("orderId");
            this.orderStatus = order.getString("orderStatus");
            this.orderTime = order.getString("orderTime");
            this.orderedDate = order.getString("orderedDate");
            this.orderGate = order.getString("orderGate");
            this.totalPrice = order.getString("totalPrice");
            this.restaurant = order.getString("restaurant");

            this.orderItems = new HashMap<String, OrderItem>();
            JSONObject jsonObject = order.getJSONObject("orderItems");
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();

                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject orderItem = (JSONObject) jsonObject.get(key);
                    this.orderItems.put(orderItem.getString("name"), new OrderItem(
                            orderItem.getString("name"),
                            orderItem.getString("image"),
                            orderItem.getString("price"),
                            orderItem.getInt("quantity")));
                }
            }
        } catch(Exception e){
                Constants.logExceptionError(e);
        }
    }

    public OrderData(String userId, String orderId, String orderStatus, String orderTime,
                     String orderedDate, String orderGate, String totalPrice,
                     String restaurant, Map<String, OrderItem> orderItems, String restaurantImage) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderedDate = orderedDate;
        this.orderGate = orderGate;
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
        this.orderItems = orderItems;
        this.restaurantImage = restaurantImage;
    }

    public OrderData(String userId, String orderId, String orderStatus, String orderTime,
                     String orderedDate, String orderGate, String totalPrice,
                     String restaurant, String restaurantImage) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderedDate = orderedDate;
        this.orderGate = orderGate;
        this.totalPrice = totalPrice;
        this.restaurant = restaurant;
        this.restaurantImage = restaurantImage;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public Map<String, OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public String getOrderGate() {
        return orderGate;
    }

    public class OrderItem{

        private String name;
        private String image;
        private String price;
        private Integer quantity;

        public OrderItem(String name, String image, String price, Integer quantity) {
            this.name = name;
            this.image = image;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getPrice() {
            return price;
        }

        public Integer getQuantity() {
            return quantity;
        }

    }
}
