package com.example.demoeditimage.phung.model.response;

import java.util.List;

public class ShopGallery {
    long shop_id;
    List<String> photos;

    public ShopGallery() {
    }

    public ShopGallery(long shop_id, List<String> photos) {
        this.shop_id = shop_id;
        this.photos = photos;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
