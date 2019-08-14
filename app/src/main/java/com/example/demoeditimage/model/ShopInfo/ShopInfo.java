package com.example.demoeditimage.model.ShopInfo;

public class ShopInfo {
    private long shop_id;
    private String shop_name;
    private String[] shop_images;
    private String shop_status;
    private String shop_decription;

    public ShopInfo() {
    }

    public ShopInfo(long shop_id, String shop_name, String[] shop_images, String shop_status, String shop_decription) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_images = shop_images;
        this.shop_status = shop_status;
        this.shop_decription = shop_decription;
    }

    public long getShop_id() {
        return shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String[] getShop_images() {
        return shop_images;
    }

    public String getShop_status() {
        return shop_status;
    }

    public String getShop_decription() {
        return shop_decription;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setShop_images(String[] shop_images) {
        this.shop_images = shop_images;
    }

    public void setShop_status(String shop_status) {
        this.shop_status = shop_status;
    }

    public void setShop_decription(String shop_decription) {
        this.shop_decription = shop_decription;
    }
}
