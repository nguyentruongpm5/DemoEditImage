package com.example.demoeditimage.model.ShopInfo;

public class ShopInfoOverview {
    private String shop_name;
    private String[] shop_images;
    private String shop_status;
    private String shop_site;

    public ShopInfoOverview() {
    }

    public ShopInfoOverview(String shop_name, String[] shop_images, String shop_status, String shop_site) {
        this.shop_name = shop_name;
        this.shop_images = shop_images;
        this.shop_status = shop_status;
        this.shop_site = shop_site;
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

    public String getShop_site() {
        return shop_site;
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

    public void setShop_site(String shop_site) {
        this.shop_site = shop_site;
    }
}
