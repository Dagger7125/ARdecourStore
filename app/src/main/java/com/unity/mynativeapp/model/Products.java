package com.unity.mynativeapp.model;

public class Products {
    String sku, imageUrl, productName, description, category;
    Integer productQty, productPrice;

    public Products(){

    }

    public Products(String sku, String imageUrl, String productName, String description, String category, Integer productQty, Integer productPrice) {
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.description = description;
        this.category = category;
        this.productQty = productQty;
        this.productPrice = productPrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
