package com.example.demoeditimage.model;

public class ProductImage {
    private String imageUrl;
    private boolean isSelected;


    public ProductImage(String imageUrl, boolean isSelected) {
        this.imageUrl = imageUrl;
        this.isSelected = isSelected;
    }

    public ProductImage() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
