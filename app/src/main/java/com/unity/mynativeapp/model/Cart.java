package com.unity.mynativeapp.model;

public class Cart {
    String sku, imageUrl, productName;
    Integer productQty, productPrice, totalPrice;

    public Cart(){

    }

    public Cart(String sku, String imageUrl, String productName, Integer productQty, Integer productPrice, Integer totalPrice) {
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
