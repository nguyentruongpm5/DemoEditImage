package com.example.demoeditimage.model;

public class ProductImage {
    private String imageUrl;

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
