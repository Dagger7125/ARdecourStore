package com.unity.mynativeapp.model;

public class ProductCategory {
    Integer productId;
    String productName;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory(Integer productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
