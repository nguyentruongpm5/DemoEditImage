package com.example.demoeditimage.model;

import java.util.List;

public class Product {
    private Long item_id;
    private Long shopid;
    private String status;
    private String item_sku;
    private String name;
    private String[] images;

    public Product() {
    }

    public Product(Long item_id, Long shopid, String status, String item_sku, String name, String[] images) {
        this.item_id = item_id;
        this.shopid = shopid;
        this.status = status;
        this.item_sku = item_sku;
        this.name = name;
        this.images = images;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItem_sku() {
        return item_sku;
    }

    public void setItem_sku(String item_sku) {
        this.item_sku = item_sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
