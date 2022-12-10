package com.example.wagba;

public class PaymentData {
    private String itemName;
    private String itemPrice;
    private String quantity;
    private Integer itemImage;

    public PaymentData(String itemName, String itemPrice, String quantity, Integer itemImage) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemImage() {
        return itemImage;
    }

    public void setItemImage(Integer itemImage) {
        this.itemImage = itemImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
